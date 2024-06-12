package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.DadosAtualizacaoUsuario;
import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.key.ChavesAcessoRepository;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.util.EncryptionUtil;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.util.List;

@Service
public class UserService {

    public static final String USUARIO_NAO_ENCONTRADO_NA_BASE = "Usuário não encontrado na base";

    @Autowired
    private UsuarioLogado usuarioLogado;
    private final UserRepository userRepository;
    private final ChavesAcessoRepository chavesAcessoRepository;

    @Autowired
    public UserService(UserRepository userRepository, ChavesAcessoRepository chavesAcessoRepository) {
        this.userRepository = userRepository;
        this.chavesAcessoRepository = chavesAcessoRepository;
    }

    public DadosUsuarioProjection getDadosUsuario(String cpf) throws ApiException {
        DadosUsuarioProjection dados = userRepository.getDadosUsuario(cpf);

        if (ObjectUtils.isEmpty(dados))
            throw new ApiException(USUARIO_NAO_ENCONTRADO_NA_BASE, HttpStatus.NO_CONTENT);

        return dados;
    }

    public DadosUsuarioProjection buscarPeloId(Integer id){
        DadosUsuarioProjection user = userRepository.findByIdProject(id);
        return user;
    }

    @Transactional
    public void criarUsuario(RegisterDTO data) throws Exception {
        RegisterDTO register = new RegisterDTO(
                data.login(),
                data.password(),
                data.email(),
                data.cpf(),
                data.nome(),
                data.telefone(),
                data.foto()
                );
        String encryptedPassword = new BCryptPasswordEncoder().encode(register.password());
        User newUser = new User(register, encryptedPassword);

        User savedUser = this.userRepository.save(newUser);

        encryptedUser(savedUser);

    }

    @Transactional
    public void encryptedUser(User savedUser) throws Exception {
        SecretKey secretKey = EncryptionUtil.generateKey();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(savedUser);
        String encryptedString = EncryptionUtil.encrypt(jsonString, secretKey);

        salvarUsuario(savedUser.getUserId(), encryptedString);
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
    public void updateUsuario(DadosAtualizacaoUsuario dadosAtualizacaoUsuario) throws ApiException {
        var usuario = userRepository.getReferenceById(dadosAtualizacaoUsuario.userId());

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

    public List<DadosUsuarioProjection> listarUsuarios(StatusRegister status) {
        User user = usuarioLogado.resgatarUsuario();
        return userRepository.findAllUsers(status != null ? status.getStatus().toUpperCase() : null, user.getUserId());
    }
}
