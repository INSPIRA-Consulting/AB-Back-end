package com.anjos_bolos.anjos_bolos_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDto {
    @NotBlank
    @Email
    private String email;
    private boolean autenticado;
    @NotBlank
    private String senha;

    public UsuarioLoginDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
