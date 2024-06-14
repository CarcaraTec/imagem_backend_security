package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.repository.ItemUsuarioRepository;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemUsuarioService {

    @Autowired
    private TermoService termoService;

    private final ItemUsuarioRepository repository;
    @Autowired
    public ItemUsuarioService(ItemUsuarioRepository repository,
                              UsuarioLogado usuarioLogado) {
        this.repository = repository;
    }

    @Transactional
    @Modifying
    public void adicionaItensUsuario(List<Integer> ids, Integer userId) {
        RetornarTermo termoAtual = termoService.exibirTermo();
        List<Integer> itensObrigatorios = termoAtual.itens.stream().map(t -> {
                    if (t.isMandatorio()) {
                        return t.getIdItem();
                    }
                    return null;
                }
            ).toList();
        ItensUsuario itensUsuario = new ItensUsuario();
        for (Integer idItem : ids) {
            itensUsuario.setIdUsuario(userId);
            itensUsuario.setIdItem(idItem);
        }
        if(ids.containsAll(itensObrigatorios)) {
            repository.save(itensUsuario);
        }else {
            throw new ValidacaoException("Itens obrigatórios não selecionados", HttpStatus.BAD_REQUEST);
        }
    }

}