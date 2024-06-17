package com.carcara.imagem_backend_security.model.lgpd;

import java.util.List;

public record CriarTermoDTO(String dsTermo, List<CriarItemDTO> itens) {
}
