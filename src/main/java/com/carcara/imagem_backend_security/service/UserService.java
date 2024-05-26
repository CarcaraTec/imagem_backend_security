package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.DadosAtualizacaoUsuario;
import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioAguardandoProjection;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserService {

    public static final String USUARIO_NAO_ENCONTRADO_NA_BASE = "Usuário não encontrado na base";

    @Autowired
    UserRepository userRepository;

    public DadosUsuarioProjection getDadosUsuario(String cpf) throws ApiException {
        DadosUsuarioProjection dados = userRepository.getDadosUsuario(cpf);

        if (ObjectUtils.isEmpty(dados))
            throw new ApiException(USUARIO_NAO_ENCONTRADO_NA_BASE, HttpStatus.NO_CONTENT);

        return dados;
    }

    public void criarUsuario(RegisterDTO data) {
        RegisterDTO register = new RegisterDTO(
                data.login(),
                data.password(),
                UserRole.USER,
                data.email(),
                data.cpf(),
                StatusRegister.AGUARDANDO,
                data.nome(),
                data.telefone(),
                data.foto()
                );
        String encryptedPassword = new BCryptPasswordEncoder().encode(register.password());
        User newUser = new User(register, encryptedPassword);

        this.userRepository.save(newUser);

    }

    public List<DadosUsuarioAguardandoProjection> getUsuarioAguardando() {
        List<DadosUsuarioAguardandoProjection> status = userRepository.getUsuarioStatusAguardando();
        return status;
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
}
