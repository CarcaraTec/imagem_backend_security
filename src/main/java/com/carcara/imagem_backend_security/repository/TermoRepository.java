package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.lgpd.Termo;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoItemProjection;
import com.carcara.imagem_backend_security.repository.projection.ExibicaoTermoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TermoRepository extends JpaRepository<Termo, Integer> {

    @Query(value = "SELECT nm_versao FROM imagem.termo order by id_termo desc limit 1", nativeQuery = true)
    Integer buscarUltimaVersao();

    @Query(value = "SELECT id_termo AS idTermo, ds_termo AS descricao FROM termo order by nm_versao desc limit 1", nativeQuery = true)
    ExibicaoTermoProjection buscarUltimoTermo();

    @Query(value = "SELECT id_item AS idItem, " +
            "ds_item AS descricao, " +
            "sn_mandatorio AS mandatorio " +
            "FROM item " +
            "where id_termo = :id ", nativeQuery = true)
    List<ExibicaoItemProjection> buscarItensTermo(@Param("id") Integer id);
}
