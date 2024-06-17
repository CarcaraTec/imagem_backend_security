package com.carcara.imagem_backend_security.service.validador.login;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.exception.SolicitacaoNaoAutorizada;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidadorStatus implements ValidadorLogin{

    @Override
    public void validar(User user) {
        if (user.getStatus() != StatusRegister.ATIVO){
            throw new SolicitacaoNaoAutorizada("Usuario não aprovado");
        }
    }
}
