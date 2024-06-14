package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByUsuarioIdOrderByDhDesc(Integer userId);
}
