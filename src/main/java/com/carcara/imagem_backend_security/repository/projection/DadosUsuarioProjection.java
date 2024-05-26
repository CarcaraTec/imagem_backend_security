package com.carcara.imagem_backend_security.repository.projection;

public interface DadosUsuarioProjection {

    Integer getUser_id();
    String getUSername();
    String getEmail();
    String getCpf();
    String getNome();
    String getTelefone();

}
