package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.DadosAtualizacaoUsuario;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioAguardandoProjection;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import com.carcara.imagem_backend_security.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/dadosPessoaisUsuarios")
    public ResponseEntity<DadosUsuarioProjection> getDadosUsuario(@RequestParam("cpf") String cpf) throws ApiException {
        return ResponseEntity.ok(service.getDadosUsuario(cpf));
    }

    @GetMapping("/usuariosStatusAguardando")
    public ResponseEntity<List<DadosUsuarioAguardandoProjection>> getUsuarioStatusAguardando() throws ApiException {
        return ResponseEntity.ok(service.getUsuarioAguardando());
    }

    @PutMapping("/updateStatusAceito")
    public ResponseEntity updateStatusAceito(@RequestParam("id") Integer id) {
        service.updateStatusAceito(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateStatusRecusado")
    public ResponseEntity updateStatusRecusado(@RequestParam("id") Integer id) {
        service.updateStatusRecusado(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateUsuario")
    @Transactional
    public ResponseEntity updateUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario) throws ApiException {
        service.updateUsuario(dadosAtualizacaoUsuario);
        return ResponseEntity.ok().build();
    }

}
