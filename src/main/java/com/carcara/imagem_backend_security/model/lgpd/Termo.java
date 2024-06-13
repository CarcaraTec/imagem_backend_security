package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "TERMO")
@Entity(name = "termo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Termo {

    @Id
    @Column(name = "ID_TERMO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTermo;

    @Column(name = "NM_VERSAO")
    private Integer nmVersao;

    @Column(name = "DS_TERMO")
    private String dsTermo;

    @Column(name = "DS_CRIADOR")
    private String dsCriador;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;

}
