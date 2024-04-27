package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
