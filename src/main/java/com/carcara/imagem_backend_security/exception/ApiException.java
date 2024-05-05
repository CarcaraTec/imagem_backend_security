package com.carcara.imagem_backend_security.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
