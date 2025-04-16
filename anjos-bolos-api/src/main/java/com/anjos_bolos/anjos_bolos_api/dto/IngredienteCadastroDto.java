package com.anjos_bolos.anjos_bolos_api.dto;

// IngredienteCadastroDto

public class IngredienteCadastroDto {
    private String nome;
    private String medida;
    private double preco;

    public IngredienteCadastroDto() {
    }

    public IngredienteCadastroDto(String nome, String medida, double preco) {
        this.nome = nome;
        this.medida = medida;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
