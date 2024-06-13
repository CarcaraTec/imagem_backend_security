package com.carcara.imagem_backend_security.model.lgpd;

import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoTermoProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RetornarTermo {

    public ExibicaoTermoProjection termo;
    public List<ExibicaoItemProjection> itens;
}
