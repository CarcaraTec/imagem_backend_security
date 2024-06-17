package com.carcara.imagem_backend_security.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaErro {
    private String mensagem;
    private Integer codigoStatus;

    public RespostaErro(String mensagem, Integer codigoStatus) {
        this.mensagem = mensagem;
        this.codigoStatus = codigoStatus;
    }
}
