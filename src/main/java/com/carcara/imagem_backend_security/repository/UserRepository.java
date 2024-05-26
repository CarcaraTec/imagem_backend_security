package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioAguardandoProjection;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            " cpf AS cpf, " +
            " nome AS nome, " +
            " telefone AS telefone " +
            " FROM users " +
            " WHERE cpf = :cpf ", nativeQuery = true)
    DadosUsuarioProjection getDadosUsuario(@Param("cpf") String cpf);

    @Query(value = " SELECT " +
            " user_id AS userId, " +
            " nome AS nome, " +
            " telefone AS telefone, " +
            " email AS email" +
            " FROM users " +
            " WHERE status = 'AGUARDANDO' ", nativeQuery = true)
    List<DadosUsuarioAguardandoProjection> getUsuarioStatusAguardando();

    @Transactional
    @Modifying
    @Query(value = " UPDATE users " +
            " SET status = 'ATIVO' " +
            " WHERE user_id = :id ", nativeQuery = true)
    void updateStatusAceito(@Param("id") Integer id);


    @Transactional
    @Modifying
    @Query(value = " UPDATE users " +
            " SET status = 'RECUSADO' " +
            " WHERE user_id = :id ", nativeQuery = true)
    void updateStatusRecusado(@Param("id") Integer id);

}
