package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.model.Log;
import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity adicionaItensUsuario(@RequestBody List<Integer> id) {
        return service.adicionaItensUsuario(id);
    }

    @PostMapping("/atualizar-itens")
    @Operation(summary = "Atualizar itens aceitos")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity atualizaItensUsuario(@RequestBody List<Integer> id) {
        service.atualizarItensUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/itens-aceitos")
    @Operation(summary = "Buscar itens aceitos")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ItensAceitosProjection>> buscaItensUsuario() {
        return ResponseEntity.ok().body(service.buscarItensAceitos());
    }

    @GetMapping("/logs")
    @Operation(summary = "Verificar logs")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Log>> logs() {
        return ResponseEntity.ok().body(service.logs());
    }

}
