package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.lgpd.ItensUsuario;
import com.carcara.imagem_backend_security.repository.projection.ItensAceitosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemUsuarioRepository extends JpaRepository<ItensUsuario, Integer> {

    @Query(value ="select i.id_item AS idItem, i.sn_mandatorio AS mandatorio " +
            "from itens_usuario iu " +
            "JOIN item i " +
            "ON iu.id_item = i.id_item " +
            "JOIN termo t " +
            "ON i.id_termo = t.id_termo " +
            "where iu.user_id = :userId " +
            "and t.id_termo = :termoId" , nativeQuery = true)
    List<ItensAceitosProjection> itensAceitosPeloUsuario(@Param("userId") Integer userId, @Param("termoId") Integer termoId);
}
