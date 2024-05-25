package com.carcara.imagem_backend_security.model.lgpd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "USUARIO")
@Entity(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "DS_NOME")
    private String dsNome;

    @Column(name = "DS_EMAIL")
    private String dsEmail;

    @Column(name = "DS_CPF")
    private String dsCpf;

    @Column(name = "DS_STATUS")
    private String dsStatus;

}
