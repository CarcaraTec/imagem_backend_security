package com.carcara.imagem_backend_security.exception;

import org.springframework.http.HttpStatus;

public class ValidacaoException extends RuntimeException{

    private final HttpStatus status;

    public ValidacaoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
