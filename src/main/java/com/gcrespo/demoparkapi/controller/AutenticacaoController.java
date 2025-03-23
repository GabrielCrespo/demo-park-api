package com.gcrespo.demoparkapi.controller;

import com.gcrespo.demoparkapi.dto.UsuarioLoginDto;
import com.gcrespo.demoparkapi.exception.ErrorMessage;
import com.gcrespo.demoparkapi.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(JwtUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {

        LOGGER.info("Processo de autenticação pelo login: {}", dto.username());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

            authenticationManager.authenticate(authenticationToken);

            var token = userDetailsService.getTokenAuthenticated(dto.username());

            return ResponseEntity.ok().body(token);
        } catch (AuthenticationException e) {
            LOGGER.warn("Bad crendentials from username: {}", dto.username());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));

    }
}
