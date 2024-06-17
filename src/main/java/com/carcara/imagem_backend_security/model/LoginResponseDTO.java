package com.carcara.imagem_backend_security.model;


import java.time.Instant;

public record LoginResponseDTO(String token, Integer userId, String nome, Instant expiration, String role) {
}
