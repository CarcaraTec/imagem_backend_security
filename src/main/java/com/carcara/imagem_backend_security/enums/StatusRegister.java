package com.carcara.imagem_backend_security.enums;

public enum StatusRegister {

    AGUARDANDO("aguardando"),
    ATIVO("ativo"),
    RECUSADO("recusado");

    private String status;

    StatusRegister(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
