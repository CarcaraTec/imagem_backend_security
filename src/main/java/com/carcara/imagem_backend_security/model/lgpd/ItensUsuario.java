package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ITENS_USUARIO")
@Entity(name = "itensUsuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItensUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM_USUARIO")
    private Integer idItemUsuario;

    @Column(name = "ID_ITEM")
    private Integer idItem;

    @Column(name = "USER_ID")
    private Integer idUsuario;

}
