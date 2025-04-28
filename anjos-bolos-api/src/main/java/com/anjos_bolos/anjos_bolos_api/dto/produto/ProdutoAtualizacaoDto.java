package com.anjos_bolos.anjos_bolos_api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoAtualizacaoDto {

    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nome;

    @NotNull(message = "O valor final do produto é obrigatório.")
    private Double valorFinal;

    public ProdutoAtualizacaoDto() {}

    public ProdutoAtualizacaoDto(String nome, Double valorFinal) {
        this.nome = nome;
        this.valorFinal = valorFinal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }
}
