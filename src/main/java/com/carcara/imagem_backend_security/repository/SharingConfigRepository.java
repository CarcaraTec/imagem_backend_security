package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.sharing.SharingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingConfigRepository extends JpaRepository<SharingConfig, Long> {
}
