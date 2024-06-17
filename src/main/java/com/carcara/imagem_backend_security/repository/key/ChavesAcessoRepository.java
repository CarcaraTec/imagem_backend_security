package com.carcara.imagem_backend_security.repository.key;

import com.carcara.imagem_backend_security.model.key.ChavesAcesso;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChavesAcessoRepository extends JpaRepository<ChavesAcesso, Integer> {

    @Modifying
    @Transactional
    @Query(value = " INSERT INTO key_management_master.key_management" +
            " (ID_USER, DS_KEY) VALUES ( :idUsuario, :descKey ) ", nativeQuery = true)
    void salvarEncrypted(@Param("idUsuario") Integer idUsuario,
                         @Param("descKey") String descKey);

    @Query(value = " SELECT DS_KEY FROM key_management_master.key_management" +
            " WHERE ID_USER = :idUsuario", nativeQuery = true)
    String getEncrypted(@Param("idUsuario") Integer idUsuario);

}
