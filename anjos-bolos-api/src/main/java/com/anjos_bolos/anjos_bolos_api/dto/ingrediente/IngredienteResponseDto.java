package com.anjos_bolos.anjos_bolos_api.dto.ingrediente;

public class IngredienteResponseDto {
    private Integer idIngrediente;
    private String nome;
    private String medida;
    private Double preco;

    public IngredienteResponseDto(Integer idIngrediente, String nome, String medida, Double preco) {
        this.idIngrediente = idIngrediente;
        this.nome = nome;
        this.medida = medida;
        this.preco = preco;
    }

    // Getters e Setters
    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}