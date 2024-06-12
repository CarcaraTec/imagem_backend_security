package com.carcara.imagem_backend_security.model;

import java.time.Instant;
import java.time.LocalDateTime;

public record LoginResponseDTO(String token, Integer userId, String nome, Instant expiration) {
}
