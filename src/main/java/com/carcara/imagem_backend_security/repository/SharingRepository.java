package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.sharing.Sharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingRepository extends JpaRepository<Sharing, Long> {
    Sharing findBySharingToken(String shareToken);
}
