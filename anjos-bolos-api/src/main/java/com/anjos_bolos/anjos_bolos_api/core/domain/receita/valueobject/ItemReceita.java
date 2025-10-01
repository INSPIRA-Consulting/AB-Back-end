package com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

public class ItemReceita {

    private Ingrediente ingrediente;
    private Double quantidade;
    private UnidadeMedidaEnum unidadeMedida;

    public ItemReceita() {
    }

    public ItemReceita(Ingrediente ingrediente, Double quantidade, UnidadeMedidaEnum unidadeMedida) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
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

    public UnidadeMedidaEnum getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedidaEnum unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

}