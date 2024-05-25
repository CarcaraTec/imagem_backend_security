package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "TERMO")
@Entity(name = "termo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Termo {

    @Id
    @Column(name = "ID_TERMO")
    private Integer idTermo;

    @Column(name = "NM_VERSAO")
    private Integer nmVersao;

    @Column(name = "DS_TERMO")
    private String dsTermo;

    @Column(name = "DS_CRIADOR")
    private String dsCriador;

    @Column(name = "DH_CRIACAO")
    private LocalDate dhCriacao;

}
