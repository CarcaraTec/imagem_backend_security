package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioAguardandoProjection;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByUsername(String username);

    @Query(value = " SELECT " +
            " username AS username, " +
            " email AS email, " +
            " cpf AS cpf " +
            " FROM users " +
            " WHERE cpf = :cpf ", nativeQuery = true)
    DadosUsuarioProjection getDadosUsuario(@Param("cpf") String cpf);

    @Query(value = " SELECT " +
            " nome AS nome, " +
            " telefone AS telefone, " +
            " email AS email" +
            " FROM users " +
            " WHERE status = 'AGUARDANDO' ", nativeQuery = true)
    List<DadosUsuarioAguardandoProjection> getUsuarioStatusAguardando();

}
