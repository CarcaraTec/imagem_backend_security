package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.service.ItemUsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensTermo")
@CrossOrigin("*")
public class ItensUsuariosController {

    private final ItemUsuarioService service;

    public ItensUsuariosController(ItemUsuarioService service) {
        this.service = service;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    public ResponseEntity adicionaItensUsuario(@RequestBody List<Integer> idTermo) {
        service.adicionaItensUsuario(idTermo);
        return ResponseEntity.ok().build();
    }

}
