package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.exception.AceiteTermoException;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.exception.ErrorResponseTermoNaoAceito;
import com.carcara.imagem_backend_security.infra.config.TokenService;
import com.carcara.imagem_backend_security.model.*;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.key.ChavesAcessoRepository;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.service.validador.login.ValidadorLogin;
import com.carcara.imagem_backend_security.util.DTOEncryptor;
import com.carcara.imagem_backend_security.util.EncryptionUtil;
import com.carcara.imagem_backend_security.util.UsuarioAdmUtil;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public static final String USUARIO_NAO_ENCONTRADO_NA_BASE = "Usuário não encontrado na base";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioAdmUtil usuarioAdmUtil;
    @Autowired
    private List<ValidadorLogin> validadores;

    @Autowired
    private SharingService sharingService;

    @Autowired
    private UsuarioLogado usuarioLogado;
    private final UserRepository userRepository;
    private final ChavesAcessoRepository chavesAcessoRepository;

    @Autowired
    public UserService(UserRepository userRepository, ChavesAcessoRepository chavesAcessoRepository) {
        this.userRepository = userRepository;
        this.chavesAcessoRepository = chavesAcessoRepository;
    }

    public ResponseEntity logar(User user) throws Exception {
        var token = tokenService.generateToken(user);


        validadores.forEach(v -> v.validar(user));


        System.out.println(chavesAcessoRepository.getEncrypted(user.getUserId()));
        String chave = chavesAcessoRepository.getEncrypted(user.getUserId());
        SecretKey secretKey = EncryptionUtil.convertStringToSecretKey(chave);
        DTOEncryptor.decryptDTO(user, secretKey);
        LoginResponseDTO dadosLogin = new LoginResponseDTO(token.token(), user.getUserId(),user.getNome(), token.expiration(), user.getRole().getRole());
        return ResponseEntity.ok().body(dadosLogin);
    }

    public String encontrarUsuario(String username){
        User user = new User();
        List<UserLogin> usuarios = usuarioAdmUtil.getUsers();
        for(UserLogin userLogin : usuarios){
            if(userLogin.username().equals(username)){
                user = userRepository.findById(userLogin.userId()).orElseThrow();
                break;
            }
        }
        return user.getUsername();
    }


    public DadosUsuarioProjection getDadosUsuario() throws ApiException {
        User userLogado = usuarioLogado.resgatarUsuario();
        DadosUsuarioProjection dados = userRepository.getDadosUsuario(userLogado.getCpf());

        if (ObjectUtils.isEmpty(dados))
            throw new ApiException(USUARIO_NAO_ENCONTRADO_NA_BASE, HttpStatus.NO_CONTENT);

        return dados;
    }

    public User buscarPeloId(Integer id) throws Exception {
        User user = userRepository.findById(id).orElseThrow();
        String chave = chavesAcessoRepository.getEncrypted(user.getUserId());
        SecretKey secretKey = EncryptionUtil.convertStringToSecretKey(chave);
        DTOEncryptor.decryptDTO(user, secretKey);
        return user;
    }

    @Transactional
    public void criarUsuario(RegisterDTO data) throws Exception {
        SecretKey secretKey = EncryptionUtil.generateKey();

        RegisterDTO register = new RegisterDTO(
                data.login(),
                data.password(),
                data.email(),
                data.cpf(),
                data.nome(),
                data.telefone(),
                data.foto()
                );

        DTOEncryptor dtoEncryptor = new DTOEncryptor();

        sharingService.compartilhar(data);
        dtoEncryptor.encryptDTO(register, secretKey);
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(register, encryptedPassword);

        User savedUser = this.userRepository.save(newUser);

        encryptedUser(savedUser, secretKey);
        usuarioAdmUtil.carregarUsuario(savedUser.getUserId(), data.login(), savedUser.getPassword());
    }

    @Transactional
    public void encryptedUser(User savedUser, SecretKey secretKey) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(savedUser);
//        String encryptedString = EncryptionUtil.encrypt(jsonString, secretKey);

        salvarUsuario(savedUser.getUserId(), convertSecretKeyToString(secretKey));
    }

    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }



    @Transactional
    public void salvarUsuario(Integer userId, String encryptedString) {
        chavesAcessoRepository.salvarEncrypted(userId, encryptedString);
    }

    @Modifying
    public void updateStatusAceito(Integer id) {
        userRepository.updateStatusAceito(id);
    }

    @Modifying
    public void updateStatusRecusado(Integer id) {
        userRepository.updateStatusRecusado(id);
    }

    @Modifying
    public void updateUsuario(DadosAtualizacaoUsuario dadosAtualizacaoUsuario) throws Exception {
        var usuario = userRepository.getReferenceById(dadosAtualizacaoUsuario.userId());
        String chave = chavesAcessoRepository.getEncrypted(usuario.getUserId());

        SecretKey secretKey = EncryptionUtil.convertStringToSecretKey(chave);

        DTOEncryptor.encryptDTO(dadosAtualizacaoUsuario, secretKey);
        if (ObjectUtils.isEmpty(usuario)) {
            throw new ApiException("Nenhum usuário encontrado", HttpStatus.NO_CONTENT);
        }
        usuario.atualizarInformacoes(dadosAtualizacaoUsuario);
    }

    public String getRole(String login) throws ApiException {
        UserDetails usuario = userRepository.findByUsername(login);
        String role = userRepository.getRole(login);

        if (ObjectUtils.isEmpty(usuario))
            throw new ApiException("Usuário não encontrado", HttpStatus.NO_CONTENT);
        if (ObjectUtils.isEmpty(role))
            throw new ApiException("Usuário ainda não autorizado", HttpStatus.NO_CONTENT);

        return role;
    }

    public List<User> listarUsuarios(StatusRegister status) throws Exception {
        User user = usuarioLogado.resgatarUsuario();
        descriptografarUsuario(user);
        List<User> usuarios = userRepository.findByStatusNotAndUserIdNot(StatusRegister.RECUSADO, user.getUserId());

        List<User> usuariosDescriptografados = new ArrayList<>();

        for (User user1 : usuarios){
            User userDecript = descriptografarUsuario(user1);
            if(userDecript!= null){
                usuariosDescriptografados.add(userDecript);
            }
        }

        return usuariosDescriptografados;
    }

    public User descriptografarUsuario(User user) throws Exception {
        String chave = chavesAcessoRepository.getEncrypted(user.getUserId());
        if(chave != null){
            SecretKey secretKey = EncryptionUtil.convertStringToSecretKey(chave);
            DTOEncryptor.decryptDTO(user,secretKey);
            return user;
        }
        return null;
    }

    public void deletarUsuarioLogado() {
        User user = usuarioLogado.resgatarUsuario();
        chavesAcessoRepository.deleteKey(user.getUserId());
    }
}
