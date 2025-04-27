package com.anjos_bolos.anjos_bolos_api.dto.receita;

import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

public class ReceitaAtualizacaoDto {

    private Produto produto;
    private Ingrediente ingrediente;
    private Double quantidade;

    public ReceitaAtualizacaoDto(Produto produto, Ingrediente ingrediente, Double quantidade) {
        this.produto = produto;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
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
