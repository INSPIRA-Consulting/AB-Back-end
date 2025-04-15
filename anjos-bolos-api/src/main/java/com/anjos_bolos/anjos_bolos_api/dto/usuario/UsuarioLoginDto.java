package com.anjos_bolos.anjos_bolos_api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;

    public UsuarioLoginDto() {
    }

    public UsuarioLoginDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
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
