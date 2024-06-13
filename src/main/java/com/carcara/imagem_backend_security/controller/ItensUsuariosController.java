package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.service.ItemUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensTermo")
@Tag(name = "Aceite do termo")
@CrossOrigin("*")
public class ItensUsuariosController {

    private final ItemUsuarioService service;

    public ItensUsuariosController(ItemUsuarioService service) {
        this.service = service;
    }

    @PostMapping("/aceitar")
    @Operation(summary = "Aceitar termo")
    public ResponseEntity adicionaItensUsuario(@RequestParam Integer userId, @RequestBody List<Integer> idTermo) {
        service.adicionaItensUsuario(idTermo, userId);
        return ResponseEntity.ok().build();
    }

}
