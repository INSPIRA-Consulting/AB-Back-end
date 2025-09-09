package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ReceitaPrimaryKey;

public class ReceitaResponseDto {
    private ReceitaPrimaryKey idReceita;

    private Produto produto;

    private IngredienteEntity ingredienteEntity;

    private Double quantidade;

    public ReceitaResponseDto(ReceitaPrimaryKey idReceita, Produto produto, IngredienteEntity ingredienteEntity, Double quantidade) {
        this.idReceita = idReceita;
        this.produto = produto;
        this.ingredienteEntity = ingredienteEntity;
        this.quantidade = quantidade;
    }

    public ReceitaResponseDto() {

    }

    public ReceitaPrimaryKey getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(ReceitaPrimaryKey idReceita) {
        this.idReceita = idReceita;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public IngredienteEntity getIngrediente() {
        return ingredienteEntity;
    }

    public void setIngrediente(IngredienteEntity ingredienteEntity) {
        this.ingredienteEntity = ingredienteEntity;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
