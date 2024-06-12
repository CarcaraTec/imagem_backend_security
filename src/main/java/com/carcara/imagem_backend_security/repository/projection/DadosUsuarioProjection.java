package com.carcara.imagem_backend_security.repository.projection;

import com.carcara.imagem_backend_security.enums.StatusRegister;

public interface DadosUsuarioProjection {

    Integer getUser_id();
    String getUsername();
    String getEmail();
    String getCpf();
    String getNome();
    String getTelefone();
    StatusRegister getStatus();

}
