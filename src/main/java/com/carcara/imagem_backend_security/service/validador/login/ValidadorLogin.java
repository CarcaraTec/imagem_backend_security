package com.carcara.imagem_backend_security.service.validador.login;

import com.carcara.imagem_backend_security.model.User;

public interface ValidadorLogin {

    void validar(User user);
}
