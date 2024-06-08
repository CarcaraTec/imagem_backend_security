package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.DadosAtualizacaoUsuario;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioAguardandoProjection;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService service;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/dadosPessoaisUsuarios")
    public ResponseEntity<DadosUsuarioProjection> getDadosUsuario(@RequestParam("cpf") String cpf) throws ApiException {
        return ResponseEntity.ok(service.getDadosUsuario(cpf));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/usuariosStatusAguardando")
    public ResponseEntity<List<DadosUsuarioAguardandoProjection>> getUsuarioStatusAguardando() {
        return ResponseEntity.ok(service.getUsuarioAguardando());
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateStatusAceito")
    public ResponseEntity updateStatusAceito(@RequestParam("id") Integer id) {
        service.updateStatusAceito(id);
        return ResponseEntity.ok().build();
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateStatusRecusado")
    public ResponseEntity updateStatusRecusado(@RequestParam("id") Integer id) {
        service.updateStatusRecusado(id);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(service.buscarPeloId(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateUsuario")
    @Transactional
    public ResponseEntity updateUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario) throws ApiException {
        service.updateUsuario(dadosAtualizacaoUsuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity listarUsuarios(){
        List<DadosUsuarioProjection> users = service.listarUsuarios();
        return ResponseEntity.ok().body(users);
    }

}
