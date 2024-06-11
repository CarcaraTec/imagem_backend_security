package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.infra.config.TokenService;
import com.carcara.imagem_backend_security.model.AuthenticationDTO;
import com.carcara.imagem_backend_security.model.LoginResponseDTO;
import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) throws ApiException {

        userService.getRole(data.login());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();

        if (user.getStatus() != StatusRegister.ATIVO){
            throw new ApiException("Usuario não aprovado", HttpStatus.FORBIDDEN);
        }

        var token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getUserId(),user.getNome()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {
        if (this.userRepository.findByUsername(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        userService.criarUsuario(data);
        return ResponseEntity.ok().build();
    }

}
