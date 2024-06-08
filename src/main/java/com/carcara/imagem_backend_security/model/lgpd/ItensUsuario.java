package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ITEM_USUARIO")
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

    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

}
