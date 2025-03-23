package com.gcrespo.demoparkapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioLoginDto(

        @Email(message = "Email deve ter um formato válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$")
        @NotBlank(message = "Email não pode ser nulo ou vazio")
        String username,

        @NotBlank(message = "Senha não pode ser nula ou vazia")
        @Size(message = "Senha deve conter tamanho 6", min = 6, max = 6)
        String password) {
}
