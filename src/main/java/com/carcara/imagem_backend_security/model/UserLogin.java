package com.carcara.imagem_backend_security.model;

public record UserLogin(Integer userId, String username, String password) {
    public UserLogin(User user) {
        this(user.getUserId(),
                user.getUsername(),
                user.getPassword());
    }
}
