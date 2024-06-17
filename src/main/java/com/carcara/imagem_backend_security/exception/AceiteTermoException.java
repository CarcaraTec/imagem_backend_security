package com.carcara.imagem_backend_security.exception;

import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AceiteTermoException extends RuntimeException{

    private final HttpStatus status;
    private final RetornarTermo termo;
    private final String mensagem;

    public AceiteTermoException(String mensagem, HttpStatus status, RetornarTermo termo) {
        this.mensagem  = mensagem;
        this.status = status;
        this.termo = termo;
    }
}