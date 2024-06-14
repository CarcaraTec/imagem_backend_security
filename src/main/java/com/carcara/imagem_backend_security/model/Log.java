package com.carcara.imagem_backend_security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "logs")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer id;

    @Column(name = "log_acao")
    private String acao;

    @Column(name = "log_dh")
    private LocalDateTime dh;

    @Column(name = "usuario_id")
    private Integer usuarioId;
}
