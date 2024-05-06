package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<DadosUsuarioProjection> getDadosUsuario(@RequestParam("cpf") String cpf) throws ApiException {
        return ResponseEntity.ok(service.getDadosUsuario(cpf));
    }

}
