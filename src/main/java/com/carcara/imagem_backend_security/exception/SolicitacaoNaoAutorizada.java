package com.carcara.imagem_backend_security.exception;

public class SolicitacaoNaoAutorizada extends RuntimeException{

    private String msg;

    public SolicitacaoNaoAutorizada(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
