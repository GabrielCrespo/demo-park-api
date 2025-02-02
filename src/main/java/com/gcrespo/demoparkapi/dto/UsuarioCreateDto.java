package com.gcrespo.demoparkapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {

    @Email(message = "Email deve ter um formato válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$")
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    private String username;

    @NotBlank(message = "Senha não pode ser nula ou vazia")
    @Size(message = "Senha deve conter tamanho 6", min = 6, max = 6)
    private String password;

    public UsuarioCreateDto() {
    }

    public UsuarioCreateDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
