package com.carcara.imagem_backend_security.model.lgpd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarTermo {

    private Termo termo;
    private List<Item> itens;

}
