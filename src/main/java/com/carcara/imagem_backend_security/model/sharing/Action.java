package com.carcara.imagem_backend_security.model.sharing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACTION")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ACTION", length = 100)
    private String action;

    public Action(CreateActionDTO actionDTO) {
        this.action = actionDTO.action();
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
