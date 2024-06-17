package com.carcara.imagem_backend_security.util;

import com.carcara.imagem_backend_security.model.Log;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ArmazenarLog {

    @Autowired
    private LogRepository logRepository;

    public void armazenarLogItensAceitos(User user, List<Integer> ids, RetornarTermo termo){
        Log log = new Log();
        log.setUsuarioId(user.getUserId());
        log.setDh(LocalDateTime.now());
        log.setAcao("Usuario com id: " + user.getUserId() + " Aceitou os itens: " + ids +" da versão: " + termo.termo.getVersao());
        logRepository.save(log);
    }

    public void armazenarLogItensAtualizados(User user, List<Integer> ids, RetornarTermo termo){
        Log log = new Log();
        log.setUsuarioId(user.getUserId());
        log.setDh(LocalDateTime.now());
        log.setAcao("Usuario com id: " + user.getUserId() + " Atualizou seus itens para: " + ids + " da versão: " + termo.termo.getVersao() );
        logRepository.save(log);
    }
}
