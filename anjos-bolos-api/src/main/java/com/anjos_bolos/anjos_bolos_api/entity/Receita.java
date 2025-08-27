package com.anjos_bolos.anjos_bolos_api.entity;

import jakarta.persistence.*;

@Entity
public class Receita {
    @EmbeddedId
    @Column(name = "idReceita")
    private ReceitaPrimaryKey idReceita;

    @ManyToOne @MapsId("fkProduto")
    private Produto produto;

    @ManyToOne @MapsId("fkIngrediente")
    private Ingrediente ingrediente;

    private Double quantidade;

    public Receita() {
    }

    public Receita(ReceitaPrimaryKey idReceita, Produto produto, Ingrediente ingrediente, Double quantidade) {
        this.idReceita = idReceita;
        this.produto = produto;
        this.ingrediente = ingrediente;
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

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
