package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.*;
import com.carcara.imagem_backend_security.infra.config.TokenService;
import com.carcara.imagem_backend_security.model.*;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.key.ChavesAcessoRepository;
import com.carcara.imagem_backend_security.service.TermoService;
import com.carcara.imagem_backend_security.service.UserService;
import com.carcara.imagem_backend_security.service.validador.login.ValidadorLogin;
import com.carcara.imagem_backend_security.util.DTOEncryptor;
import com.carcara.imagem_backend_security.util.EncryptionUtil;
import com.carcara.imagem_backend_security.util.UsuarioAdmUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.management.relation.Role;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private TermoService termoService;

    @Autowired
    private ChavesAcessoRepository chavesAcessoRepository;

    @PostMapping("/login")
    @Operation(summary = "Logar")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) throws Exception {
        String username = userService.encontrarUsuario(data.login());
        if(username == null){
            throw new SolicitacaoNaoAutorizada("Email ou senha incorreto!");
        }
        var usernamePassword = log(username, data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        return userService.logar(user);
    }

    public static UsernamePasswordAuthenticationToken log(String username, String password){
        try{
            return new UsernamePasswordAuthenticationToken(username, password);
        }catch (Exception e){
            throw new SolicitacaoNaoAutorizada("Email ou senha incorreto!");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {
        if (this.userRepository.findByUsername(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        userService.criarUsuario(data);
        return ResponseEntity.ok().build();
    }

}
