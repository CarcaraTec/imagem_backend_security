package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.Log;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.repository.ItemUsuarioRepository;
import com.carcara.imagem_backend_security.repository.LogRepository;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
import com.carcara.imagem_backend_security.util.ArmazenarLog;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemUsuarioService {

    private final TermoService termoService;
    private final ItemUsuarioRepository repository;
    private final UsuarioLogado usuarioLogado;
    private final UserRepository userRepository;
    private final ItemUsuarioRepository itemUsuarioRepository;
    private final ArmazenarLog armazenarLog;
    private final LogRepository logRepository;

    @Autowired
    public ItemUsuarioService(ItemUsuarioRepository repository,
                              UsuarioLogado usuarioLogado,
                              TermoService termoService,
                              UserRepository userRepository, ItemUsuarioRepository itemUsuarioRepository,
                              ArmazenarLog armazenarLog,
                              LogRepository logRepository) {
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.termoService = termoService;
        this.userRepository = userRepository;
        this.itemUsuarioRepository = itemUsuarioRepository;
        this.armazenarLog = armazenarLog;
        this.logRepository = logRepository;
    }

    @Transactional
    @Modifying
    public void adicionaItensUsuario(List<Integer> ids) {
        User user = usuarioLogado.resgatarUsuario();
        RetornarTermo termoAtual = termoService.exibirTermo();

        List<Integer> itensObrigatorios = buscarItensObrigatorios(termoAtual);
        validarItensObrigatorios(ids, itensObrigatorios);

        List<ItensUsuario> itensUsuario = new ArrayList<>();
        for (Integer idItem : ids) {
            ItensUsuario itemUsuario = new ItensUsuario();
            itemUsuario.setIdUsuario(user.getUserId());
            itemUsuario.setIdItem(idItem);
            itensUsuario.add(itemUsuario);
        }
        user.setRole(UserRole.USER);
        userRepository.save(user);
        repository.saveAll(itensUsuario);
        armazenarLog.armazenarLogItensAceitos(user, ids, termoAtual);
    }

    @Transactional
    @Modifying
    public void atualizarItensUsuario(List<Integer> ids) {
        User user = usuarioLogado.resgatarUsuario();

        RetornarTermo termoAtual = termoService.exibirTermo();

        List<Integer> itensObrigatorios = buscarItensObrigatorios(termoAtual);

        validarItensObrigatorios(ids, itensObrigatorios);

        List<ItensUsuario> itensAceitosAnteriormente = repository.itensAceitosPeloUsuarioEntidade(user.getUserId(), termoAtual.termo.getIdTermo());
        repository.deleteAll(itensAceitosAnteriormente);

        List<ItensUsuario> itensAceitos = new ArrayList<>();

        for (Integer id : ids){
            ItensUsuario itemUsuario   = new ItensUsuario();
            itemUsuario.setIdUsuario(user.getUserId());
            itemUsuario.setIdItem(id);
            itensAceitos.add(itemUsuario);
        }

        repository.saveAll(itensAceitos);

        armazenarLog.armazenarLogItensAtualizados(user, ids, termoAtual);
    }





    public List<ItensAceitosProjection> buscarItensAceitos(){
        User user = usuarioLogado.resgatarUsuario();
        RetornarTermo termoAtual = termoService.exibirTermo();
        List<ItensAceitosProjection> itensAceitos = itemUsuarioRepository.itensAceitosPeloUsuarioProjection(user.getUserId(), termoAtual.termo.getIdTermo());
        return itensAceitos;
    }


    private List<Integer> buscarItensObrigatorios(RetornarTermo termoAtual) {
        List<Integer> itensObrigatorios = termoAtual.itens.stream()
                .filter(t -> t.isMandatorio())
                .map(t -> t.getIdItem())
                .collect(Collectors.toList());
        return itensObrigatorios;
    }

    private void validarItensObrigatorios(List<Integer> ids, List<Integer> itensObrigatorios) {
        if (!ids.containsAll(itensObrigatorios)) {
            throw new ValidacaoException("Itens obrigatórios não selecionados", HttpStatus.BAD_REQUEST);
        }
    }

    public List<Log> logs(){
        User user = usuarioLogado.resgatarUsuario();
        return logRepository.findAllByUsuarioIdOrderByDhDesc(user.getUserId());
    }
}