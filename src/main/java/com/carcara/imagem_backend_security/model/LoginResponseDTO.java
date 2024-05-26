package com.carcara.imagem_backend_security.model;

public record LoginResponseDTO(String token, Integer userId, String nome) {
}
