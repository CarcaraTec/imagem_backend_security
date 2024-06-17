package com.carcara.imagem_backend_security.exception;

import com.carcara.imagem_backend_security.model.LoginResponseDTO;

import java.time.Instant;

public record ErrorResponseTermoNaoAceito(String token, Integer userId, String nome, Instant expiration, String role) {
    public ErrorResponseTermoNaoAceito(LoginResponseDTO dadosLogin) {
        this(dadosLogin.token(),
                dadosLogin.userId(),
                dadosLogin.nome(),
                dadosLogin.expiration(),
                dadosLogin.role());
    }
}