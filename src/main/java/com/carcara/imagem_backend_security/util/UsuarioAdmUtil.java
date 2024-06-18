package com.carcara.imagem_backend_security.util;


import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.UserLogin;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.key.ChavesAcessoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;


@Component
public class UsuarioAdmUtil {

    public List<UserLogin> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChavesAcessoRepository chavesAcessoRepository;

    public List<UserLogin> getUsers(){
        return users;
    }

    @PostConstruct
    public void init() throws Exception {
        this.users = getUsuarios().stream().map(user -> new UserLogin(user)).toList();
    }

    public void carregarUsuario(Integer userId, String username, String password) throws Exception {
        this.users = List.of(new UserLogin(userId, username, password));
    }

    public List<User> getUsuarios() throws Exception {
        List<User> usuario = userRepository.findAll();

        for (User user : usuario) {
            String chave = chavesAcessoRepository.getEncrypted(user.getUserId());
            if(chave != null){
                SecretKey secretKey = EncryptionUtil.convertStringToSecretKey(chave);
                DTOEncryptor.decryptDTO(user,secretKey);
            }
        }

        return usuario;
    }
}
