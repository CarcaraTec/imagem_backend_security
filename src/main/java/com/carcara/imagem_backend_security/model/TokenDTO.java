package com.carcara.imagem_backend_security.model;

import java.time.Instant;

public record TokenDTO(String token, Instant expiration) {
}
