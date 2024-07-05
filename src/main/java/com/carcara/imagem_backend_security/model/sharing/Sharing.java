package com.carcara.imagem_backend_security.model.sharing;

import com.carcara.imagem_backend_security.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SHARING")
public class Sharing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "URL", length = 1000)
    private String url;



    @ManyToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusSharing status;

    @Column(name = "SHARING_KEY", length = 1000)
    private String sharingKey;

    @Column(name = "SHARING_TOKEN", length = 1000)
    private String sharingToken;


    public Sharing(User user, String url, String key) {
        this.createdBy = user;
        this.url = url;
        this.status = StatusSharing.ATIVO;
        this.sharingKey = key;
        this.sharingToken = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public StatusSharing getStatus() {
        return status;
    }

    public void setStatus(StatusSharing status) {
        this.status = status;
    }

    public String getKey() {
        return sharingKey;
    }

    public void setKey(String key) {
        this.sharingKey = key;
    }

    public String getSharingKey() {
        return sharingKey;
    }

    public void setSharingKey(String sharingKey) {
        this.sharingKey = sharingKey;
    }

    public String getSharingToken() {
        return sharingToken;
    }

    public void setSharingToken(String sharingToken) {
        this.sharingToken = sharingToken;
    }
}
