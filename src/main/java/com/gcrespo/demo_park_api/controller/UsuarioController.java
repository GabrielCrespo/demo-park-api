package com.gcrespo.demo_park_api.controller;

import com.gcrespo.demo_park_api.entity.Usuario;
import com.gcrespo.demo_park_api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@RequestBody Usuario usuario, @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.editarSenha(id, usuario.getPassword()));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }
}
