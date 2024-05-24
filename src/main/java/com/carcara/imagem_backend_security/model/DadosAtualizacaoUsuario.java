package com.carcara.imagem_backend_security.model;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(

        @NotNull
        Integer userId,
        String username,
        String email,
        String cpf,
        String nome,
        String telefone,
        String foto) {
}
