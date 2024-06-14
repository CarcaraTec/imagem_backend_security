package com.carcara.imagem_backend_security.exception;

import com.carcara.imagem_backend_security.model.LoginResponseDTO;

public record ErrorResponseTermoNaoAceito(LoginResponseDTO dadosLogin, String mensagem, Object termo) {
}
