package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer idItem;

    @Column(name = "ID_TERMO")
    private Integer idTermo;

    @Column(name = "DS_ITEM")
    private String dsItem;

    @Column(name = "SN_MANDATORIO")
    private String snMandatorio;

}
