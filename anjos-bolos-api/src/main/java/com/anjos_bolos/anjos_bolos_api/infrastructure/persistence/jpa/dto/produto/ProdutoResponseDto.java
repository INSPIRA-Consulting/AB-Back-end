package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto;

public class ProdutoResponseDto {

    private Integer idProduto;
    private String nome;
    private Double valorFinal;

    public ProdutoResponseDto() {}

    public ProdutoResponseDto(Integer idProduto, String nome, Double valorFinal) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.valorFinal = valorFinal;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
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
