package com.anjos_bolos.anjos_bolos_api.dto;

import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class UsuarioResponseDto {
    @NotBlank
    private String nome;
    @NotBlank
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Funcao funcao;


    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(String nome, Funcao funcao) {
        this.nome = nome;
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }
}
