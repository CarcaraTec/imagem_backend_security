package com.carcara.imagem_backend_security.service.validador.login;

import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.repository.ItemUsuarioRepository;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
import com.carcara.imagem_backend_security.service.TermoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidadorTermoAceito{

    private static final String MANDATORY = "s";
    private final ItemUsuarioRepository itemUsuarioRepository;
    private final TermoService termoService;

    @Autowired
    public ValidadorTermoAceito(ItemUsuarioRepository itemUsuarioRepository, TermoService termoService) {
        this.itemUsuarioRepository = itemUsuarioRepository;
        this.termoService = termoService;
    }

    public void validar(User user) {
        if (user.getRole().equals(UserRole.ADMIN)) {
            return;
        }

        RetornarTermo termoAtual = termoService.exibirTermo();
        List<ExibicaoItemProjection> itensObrigatorios = termoAtual.itens.stream()
                .filter(item -> MANDATORY.equalsIgnoreCase(item.getMandatorio()))
                .collect(Collectors.toList());

        List<ItensAceitosProjection> itensAceitos =
                itemUsuarioRepository.itensAceitosPeloUsuario(user.getUserId(), termoAtual.termo.getIdTermo());

        if (itensAceitos.isEmpty()) {
            throw new ValidacaoException("Usuario não aceitou o termo", HttpStatus.FORBIDDEN);
        }

        Set<Integer> idsItensAceitos = itensAceitos.stream()
                .map(ItensAceitosProjection::getIdItem)
                .collect(Collectors.toSet());

        boolean todosMandatoriosAceitos = itensObrigatorios.stream()
                .allMatch(item -> idsItensAceitos.contains(item.getIdItem()));

        if (!todosMandatoriosAceitos) {
            throw new ValidacaoException("Usuário precisa aceitar todos os itens mandatórios", HttpStatus.FORBIDDEN);
        }
    }
}
