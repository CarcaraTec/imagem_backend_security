package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ITEM")
@Entity(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "ID_ITEM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @Column(name = "ID_TERMO")
    private Integer idTermo;

    @Column(name = "DS_ITEM")
    private String dsItem;

    @Column(name = "SN_MANDATORIO")
    private Boolean snMandatorio;

}
