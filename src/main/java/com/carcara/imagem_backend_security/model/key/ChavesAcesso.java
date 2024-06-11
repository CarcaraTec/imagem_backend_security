package com.carcara.imagem_backend_security.model.key;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "KEY_MANAGEMENT")
@Entity(name = "chavesAcesso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChavesAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_KEY")
    private Integer id;

    @Column(name = "ID_USER")
    private Integer idUsuario;

    @Column(name = "DS_KEY")
    private String descKey;

}
