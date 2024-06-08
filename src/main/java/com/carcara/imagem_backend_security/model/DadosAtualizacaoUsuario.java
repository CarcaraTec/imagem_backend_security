package com.carcara.imagem_backend_security.model;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(

        @NotNull
        Integer userId,
        String cpf,
        String nome,
        String telefone,
        String foto) {
}
