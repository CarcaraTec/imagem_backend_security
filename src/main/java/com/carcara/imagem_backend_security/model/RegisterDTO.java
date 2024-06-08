package com.carcara.imagem_backend_security.model;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.enums.UserRole;

public record RegisterDTO(
        String login,
        String password,
        String email,
        String cpf,
        String nome,
        String telefone,
        String foto) {
}
