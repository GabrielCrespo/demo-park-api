package com.gcrespo.demoparkapi.jwt;

import com.gcrespo.demoparkapi.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

public class JwtUserDetails extends User {

    private final Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    public Long getId() {
        return usuario.getId();
    }

    public String getRole() {
        return usuario.getRole().name();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof JwtUserDetails that)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usuario);
    }
}
