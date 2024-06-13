package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.lgpd.CriarTermoDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.model.lgpd.Termo;
import com.carcara.imagem_backend_security.repository.TermoRepository;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoTermoProjection;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TermoService {

    private static final String TERMO_NAO_PODE_SER_VAZIO = "Termo não pode ser vazio";
    private final TermoRepository repository;
    private final ItemService itemService;
    private final UsuarioLogado usuarioLogado;

    @Autowired
    public TermoService(TermoRepository repository,
                        ItemService itemService,
                        UsuarioLogado usuarioLogado) {
        this.repository = repository;
        this.itemService = itemService;
        this.usuarioLogado = usuarioLogado;
    }

    @Transactional
    public void adicionaTermoCompleto(CriarTermoDTO termo) throws Exception {
        try {
            Termo termoAdicionado = adicionaTermo(termo);
            itemService.adicionaItens(termo.itens(), termoAdicionado.getIdTermo());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Modifying
    public Termo adicionaTermo(CriarTermoDTO termo) throws ApiException {
        if (ObjectUtils.isEmpty(termo)) {
            throw new ApiException(TERMO_NAO_PODE_SER_VAZIO, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        User user = usuarioLogado.resgatarUsuario();

        Termo novoTermo = new Termo();
        novoTermo.setNmVersao(buscarUltimaVersao() + 1);
        novoTermo.setDsTermo(termo.dsTermo());
        novoTermo.setDsCriador(user.getNome());
        novoTermo.setDhCriacao(LocalDateTime.now());

        return repository.save(novoTermo);

    }


    public RetornarTermo exibirTermo(){
        ExibicaoTermoProjection termo = repository.buscarUltimoTermo();
        if (ObjectUtils.isEmpty(termo)){
            throw new ValidacaoException("Termo não existe", HttpStatus.BAD_REQUEST);
        }
        List<ExibicaoItemProjection> itens = repository.buscarItensTermo(termo.getIdTermo());
        return new RetornarTermo(termo, itens);

    }

    public Integer buscarUltimaVersao(){
        Integer valor = repository.buscarUltimaVersao();
        return valor != null ? valor : 0;
    }


}
