package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.model.lgpd.CriarItemDTO;
import com.carcara.imagem_backend_security.model.lgpd.Item;
import com.carcara.imagem_backend_security.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ItemService {

    public static final String TERMO_NAO_PODE_SER_CRIADO_SEM_ITEM = "Termo não pode ser criado sem item";

    private final ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void adicionaItens(List<CriarItemDTO> itens, Integer numeroVersaoTermo) throws ApiException {
        if (ObjectUtils.isEmpty(itens)) {
            throw new ApiException(TERMO_NAO_PODE_SER_CRIADO_SEM_ITEM, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        for(CriarItemDTO item : itens) {
            Item adicionaItem = new Item();
            adicionaItem.setIdTermo(numeroVersaoTermo);
            adicionaItem.setDsItem(item.descricao());
            adicionaItem.setSnMandatorio(item.mandatorio());

            repository.save(adicionaItem);
        }
    }

}
