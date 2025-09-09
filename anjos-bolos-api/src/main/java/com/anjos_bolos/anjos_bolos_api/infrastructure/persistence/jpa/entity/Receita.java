package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
public class Receita {
    @EmbeddedId
    private ReceitaPrimaryKey idReceita;

    @ManyToOne @MapsId("fkProduto")
    private Produto produto;

    @ManyToOne @MapsId("fkIngrediente")
    private IngredienteEntity ingredienteEntity;

    private Double quantidade;

    public Receita() {
    }

    public Receita(ReceitaPrimaryKey idReceita, Produto produto, IngredienteEntity ingredienteEntity, Double quantidade) {
        this.idReceita = idReceita;
        this.produto = produto;
        this.ingredienteEntity = ingredienteEntity;
        this.quantidade = quantidade;
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
