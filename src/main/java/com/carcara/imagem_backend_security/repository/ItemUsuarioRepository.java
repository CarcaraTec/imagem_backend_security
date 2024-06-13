package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUsuarioRepository extends JpaRepository<ItensUsuario, Integer> {

}
