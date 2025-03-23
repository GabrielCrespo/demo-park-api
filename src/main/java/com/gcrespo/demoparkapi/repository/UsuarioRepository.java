package com.gcrespo.demoparkapi.repository;

import com.gcrespo.demoparkapi.entity.Usuario;
import com.gcrespo.demoparkapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query(value = "SELECT u.role FROM usuarios u WHERE u.username LIKE :username")
    Role findRoleByUsername(@Param("username") String username);
}