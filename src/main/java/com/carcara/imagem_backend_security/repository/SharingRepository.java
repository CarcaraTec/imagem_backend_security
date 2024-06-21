package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.sharing.Sharing;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingRepository extends JpaRepository<Sharing, Integer> {
    Sharing findBySharingToken(String shareToken);

    @Modifying
    @Transactional
    @Query("DELETE FROM Sharing c WHERE c.createdBy.id = :userId")
    void deleteAllByCreatedBy(@Param("userId") Integer userId);
}
