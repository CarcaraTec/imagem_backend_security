package com.carcara.imagem_backend_security.repository.projection;

public interface ExibicaoItemProjection {

    Integer getIdItem();
    String getDescricao();
    Byte getMandatorio();

    default Boolean isMandatorio() {
        return getMandatorio() != null && getMandatorio() != 0;
    }
}
