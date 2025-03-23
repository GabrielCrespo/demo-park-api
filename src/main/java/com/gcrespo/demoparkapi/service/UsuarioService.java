package com.gcrespo.demoparkapi.service;

import com.gcrespo.demoparkapi.entity.Usuario;
import com.gcrespo.demoparkapi.enums.Role;
import com.gcrespo.demoparkapi.exception.EntityNotFoundException;
import com.gcrespo.demoparkapi.exception.PasswordInvalidException;
import com.gcrespo.demoparkapi.exception.UserUniqueViolationException;
import com.gcrespo.demoparkapi.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new UserUniqueViolationException(String.format("Username %s já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id=%d não encontrado", id)));
    }

    @Transactional
    public void editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {

        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmacao");
        }

        Usuario usuario = buscarPorId(id);

        if (!usuario.getPassword().equals(senhaAtual)) {
            throw new PasswordInvalidException("Senha atual não confere!");
        }

        usuario.setPassword(novaSenha);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário %s não encontrado", username)));
    }

    @Transactional
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
