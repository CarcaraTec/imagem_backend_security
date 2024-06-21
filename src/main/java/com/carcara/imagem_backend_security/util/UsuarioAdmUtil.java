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

    public void excluirUsuario(String username){
        List<UserLogin> todosUsuarios = getUsers();
        for(UserLogin userLogin : todosUsuarios){
            if(userLogin.username().equals(username)){
                todosUsuarios.remove(userLogin);
                break;
            }
        }
        this.users = todosUsuarios;;
    }

    @PostConstruct
    public void init() throws Exception {
        this.users = new ArrayList<>(getUsuarios().stream().map(user -> new UserLogin(user)).toList());
    }

    public void carregarUsuario(Integer userId, String username, String password) throws Exception {
        List<UserLogin> todosUsuarios = getUsers();
        todosUsuarios.add(new UserLogin(userId, username, password));
        this.users = todosUsuarios;
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
