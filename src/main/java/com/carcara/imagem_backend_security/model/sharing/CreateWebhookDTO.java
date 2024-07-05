package com.carcara.imagem_backend_security.model.sharing;

import java.util.List;

public record CreateWebhookDTO(
        String url,
        List<Long> actions

) {
}
