package com.carcara.imagem_backend_security.service.validador.login;

import com.carcara.imagem_backend_security.enums.UserRole;
import com.carcara.imagem_backend_security.exception.AceiteTermoException;
import com.carcara.imagem_backend_security.exception.ApiException;
import com.carcara.imagem_backend_security.exception.ValidacaoException;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.lgpd.RetornarTermo;
import com.carcara.imagem_backend_security.repository.ItemUsuarioRepository;
import com.carcara.imagem_backend_security.repository.UserRepository;
import com.carcara.imagem_backend_security.repository.key.ChavesAcessoRepository;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
import com.carcara.imagem_backend_security.service.TermoService;
import com.carcara.imagem_backend_security.util.DTOEncryptor;
import com.carcara.imagem_backend_security.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidadorTermoAceito implements ValidadorLogin{

    private final ItemUsuarioRepository itemUsuarioRepository;
    private final TermoService termoService;
    private final UserRepository userRepository;

    @Autowired
    private ChavesAcessoRepository chavesAcessoRepository;
    @Autowired
    public ValidadorTermoAceito(ItemUsuarioRepository itemUsuarioRepository, TermoService termoService,
                                UserRepository userRepository) {
        this.itemUsuarioRepository = itemUsuarioRepository;
        this.termoService = termoService;
        this.userRepository = userRepository;
    }

    @Override
    public void validar(User user) {
        if (user.getRole().equals(UserRole.ADMIN)) {
            return;
        }

        RetornarTermo versaoAtual = termoService.exibirTermo();
        List<ItensAceitosProjection> itensAceitos =
                itemUsuarioRepository.itensAceitosPeloUsuarioProjection(user.getUserId(), versaoAtual.termo.getIdTermo());

        if (itensAceitos.isEmpty()) {
            user.setRole(UserRole.ACEITETERMO);


            userRepository.save(user);
            throw new AceiteTermoException("Aguardando aceite do termo", HttpStatus.FORBIDDEN, versaoAtual);
        }
    }
}
