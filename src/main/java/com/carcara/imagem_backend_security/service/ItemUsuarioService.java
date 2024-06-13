package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import com.carcara.imagem_backend_security.repository.ItemUsuarioRepository;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemUsuarioService {

    private final ItemUsuarioRepository repository;
    private final UsuarioLogado usuarioLogado;
    @Autowired
    public ItemUsuarioService(ItemUsuarioRepository repository,
                              UsuarioLogado usuarioLogado) {
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
    }

    @Transactional
    @Modifying
    public void adicionaItensUsuario(List<Integer> id) {
        for (Integer idItem : id) {
            ItensUsuario itensUsuario = new ItensUsuario();
            itensUsuario.setIdUsuario(usuarioLogado.resgatarUsuario().getUserId());
            itensUsuario.setIdItem(idItem);

            repository.save(itensUsuario);
        }
    }

}
