package com.carcara.imagem_backend_security.util;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogado {

    @Autowired
    private UserRepository userRepository;

    public User resgatarUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User usuario = (User) authentication.getPrincipal();

            return userRepository.findById(usuario.getUserId())
                    .orElseThrow(() -> new ApiException("Usuário não encontrado", HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
