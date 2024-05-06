package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

}
