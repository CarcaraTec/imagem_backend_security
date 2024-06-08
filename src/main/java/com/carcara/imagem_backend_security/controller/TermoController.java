package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.model.lgpd.CriarTermo;
import com.carcara.imagem_backend_security.service.TermoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/termo")
@CrossOrigin("*")
public class TermoController {

    @Autowired
    private TermoService service;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/acidionaTermo")
    public ResponseEntity adicionaTermo(@RequestBody @Valid CriarTermo termo) throws Exception {
        service.adicionaTermoCompleto(termo);
        return ResponseEntity.ok().build();
    }

}
