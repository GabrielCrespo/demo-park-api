package com.gcrespo.demoparkapi.repository;

import com.gcrespo.demoparkapi.entity.Usuario;
import com.gcrespo.demoparkapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query(value = "select u.role from usuario u where u.username like :username")
    Role findRoleByUsername(String username);
}