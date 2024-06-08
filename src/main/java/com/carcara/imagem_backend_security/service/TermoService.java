package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.lgpd.CriarTermo;
import com.carcara.imagem_backend_security.model.lgpd.Termo;
import com.carcara.imagem_backend_security.repository.TermoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

@Service
public class TermoService {

    private static final String TERMO_NAO_PODE_SER_VAZIO = "Termo não pode ser vazio";
    private final TermoRepository repository;
    private final ItemService itemService;

    @Autowired
    public TermoService(TermoRepository repository,
                        ItemService itemService) {
        this.repository = repository;
        this.itemService = itemService;
    }

    @Transactional
    public void adicionaTermoCompleto(CriarTermo termo) throws Exception {
        try {
            adicionaTermo(termo.getTermo());
            itemService.adicionaItens(termo.getItens(), termo.getTermo().getNmVersao());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void adicionaTermo(Termo termo) throws ApiException {
        if (ObjectUtils.isEmpty(termo)) {
            throw new ApiException(TERMO_NAO_PODE_SER_VAZIO, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        Termo novoTermo = new Termo();
        novoTermo.setIdTermo(termo.getIdTermo());
        novoTermo.setNmVersao(termo.getNmVersao());
        novoTermo.setDsTermo(termo.getDsTermo());
        novoTermo.setDsCriador(termo.getDsCriador());
        novoTermo.setDhCriacao(LocalDate.now());

        repository.save(novoTermo);
    }
}
