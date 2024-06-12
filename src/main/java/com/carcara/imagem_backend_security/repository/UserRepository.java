package com.carcara.imagem_backend_security.repository;

import com.carcara.imagem_backend_security.enums.StatusRegister;
import com.carcara.imagem_backend_security.model.User;
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
            "user_id, " +
            " username AS username, " +
            " email AS email, " +
            " cpf AS cpf, " +
            " nome AS nome, " +
            " telefone AS telefone " +
            " FROM users " +
            " WHERE cpf = :cpf ", nativeQuery = true)
    DadosUsuarioProjection getDadosUsuario(@Param("cpf") String cpf);

    @Transactional
    @Modifying
    @Query(value = " UPDATE users " +
            " SET status = 'ATIVO', " +
            " role = 'USER' " +
            " WHERE user_id = :id ", nativeQuery = true)
    void updateStatusAceito(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = " UPDATE users " +
            " SET status = 'RECUSADO' " +
            " WHERE user_id = :id ", nativeQuery = true)
    void updateStatusRecusado(@Param("id") Integer id);

    @Query(value = "SELECT * FROM users WHERE user_id = :id", nativeQuery = true)
    DadosUsuarioProjection findByIdProject(@Param("id") Integer id);

    @Query(value = " SELECT ROLE FROM USERS WHERE USERNAME = :login ", nativeQuery = true)
    String getRole(String login);

    @Query(value = " SELECT " +
            "user_id, " +
            " username AS username, " +
            " email AS email, " +
            " cpf AS cpf, " +
            " nome AS nome, " +
            " telefone AS telefone, " +
            "status AS status " +
            " FROM users " +
            " WHERE status != 'RECUSADO' " +
            "AND status = :status " +
            "AND user_id != :id", nativeQuery = true)
    List<DadosUsuarioProjection> findAllUsers(@Param("status")String status, @Param("id") Integer id);
}
