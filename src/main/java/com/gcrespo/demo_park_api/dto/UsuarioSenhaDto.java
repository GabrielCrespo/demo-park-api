package com.gcrespo.demo_park_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioSenhaDto {

    @NotBlank(message = "Senha atual não pode ser nula ou vazia")
    @Size(message = "Senha atual deve conter tamanho 6", min = 6, max = 6)
    private String senhaAtual;

    @NotBlank(message = "Nova senha não pode ser nula ou vazia")
    @Size(message = "Nova senha deve conter tamanho 6", min = 6, max = 6)
    private String novaSenha;

    @NotBlank(message = "Confirma senha não pode ser nula ou vazia")
    @Size(message = "Confirma senha deve conter tamanho 6", min = 6, max = 6)
    private String confirmaSenha;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
}
