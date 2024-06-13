package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.model.lgpd.CriarTermoDTO;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.service.TermoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.OnOpen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/termo")
@CrossOrigin("*")
@Tag(name = "Termo")
public class TermoController {

    @Autowired
    private TermoService service;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/acidionaTermo")
    @Operation(summary = "Adicionar termo")
    public ResponseEntity adicionaTermo(@RequestBody @Valid CriarTermoDTO termo) throws Exception {
        service.adicionaTermoCompleto(termo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ultimo-termo")
    @Operation(summary = "Recuperar termo atual")
    public ResponseEntity ultimoTermo(){
        RetornarTermo termo = service.exibirTermo();
        return ResponseEntity.ok().body(termo);
    }
}
