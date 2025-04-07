package com.anjos_bolos.anjos_bolos_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDto {
    @NotBlank
    @Email
    private String email;
    private boolean autenticado;

    public boolean isLoginValido(String email, String senha) {
        autenticado = this.email.equalsIgnoreCase(email) && this.senha.equalsIgnoreCase(senha);
        return autenticado;
    }

    @NotBlank
    private String senha;

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
