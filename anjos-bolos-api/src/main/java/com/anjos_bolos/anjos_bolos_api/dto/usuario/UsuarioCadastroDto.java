package com.anjos_bolos.anjos_bolos_api.dto.usuario;

import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioCadastroDto {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    public UsuarioCadastroDto(String nome, String email, String senha, Funcao funcao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }
}