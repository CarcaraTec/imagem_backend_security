package com.carcara.imagem_backend_security.model;

import com.carcara.imagem_backend_security.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "users")
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACCESS_TYPE")
    @Enumerated(EnumType.STRING)
    private UserRole accessType;

    public User(String username, String password, UserRole accessType, String email, String cpf) {
        this.username = username;
        this.password = password;
        this.accessType = accessType;
        this.email = email;
        this.cpf = cpf;
    }

    public User(RegisterDTO data, String encryptedPassword) {
        this.username = data.login();
        this.email = data.email();
        this.cpf = data.cpf();
        this.accessType = data.role();
        this.password = encryptedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
