package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.DadosAtualizacaoUsuario;
import com.carcara.imagem_backend_security.model.lgpd.Termo;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Tag(name = "Usuario")
public class UserController {

    @Autowired
    UserService service;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/dadosPessoaisUsuarios")
    @Operation(summary = "Recupera dados pessoais do usuario")
    public ResponseEntity<DadosUsuarioProjection> getDadosUsuario() throws ApiException {
        return ResponseEntity.ok(service.getDadosUsuario());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateStatusAceito")
    @Operation(summary = "Aceita usuario")
    public ResponseEntity updateStatusAceito(@RequestParam("id") Integer id) {
        service.updateStatusAceito(id);
        return ResponseEntity.ok().build();
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateStatusRecusado")
    @Operation(summary = "Recusa usuario")
    public ResponseEntity updateStatusRecusado(@RequestParam("id") Integer id) {
        service.updateStatusRecusado(id);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/buscar/{id}")
    @Operation(summary = "Busca usuario pelo id")
    public ResponseEntity buscarPorId(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok().body(service.buscarPeloId(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateUsuario")
    @Operation(summary = "Atualiza dados do usuario")
    @Transactional
    public ResponseEntity updateUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario) throws Exception {
        service.updateUsuario(dadosAtualizacaoUsuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lista todos usuarios")
    public ResponseEntity listarUsuarios(@RequestParam(required = false) StatusRegister status){
        List<DadosUsuarioProjection> users = service.listarUsuarios(status);
        return ResponseEntity.ok().body(users);
    }


}
