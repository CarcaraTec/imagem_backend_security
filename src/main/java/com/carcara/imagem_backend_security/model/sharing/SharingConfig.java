package com.carcara.imagem_backend_security.model.sharing;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SHARING_CONFIG")
public class SharingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_SHARING", referencedColumnName = "ID")
    private Sharing sharing;

    @ManyToOne
    @JoinColumn(name = "ID_ACTION", referencedColumnName = "ID")
    private Action action;

    public SharingConfig(Sharing sharing, Action action) {
        this.sharing = sharing;
        this.action = action;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Sharing sharing) {
        this.sharing = sharing;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
