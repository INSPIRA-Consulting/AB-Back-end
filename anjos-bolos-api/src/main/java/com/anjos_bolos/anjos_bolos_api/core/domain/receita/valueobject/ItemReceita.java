package com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;

public class ItemReceita {
    private IngredienteEntity ingredienteEntity;
    private Double quantidade;
    private UnidadeMedidaEnum unidadeMedida;

    public ItemReceita() {
    }

    public ItemReceita(IngredienteEntity ingredienteEntity, Double quantidade, UnidadeMedidaEnum unidadeMedida) {
        this.ingredienteEntity = ingredienteEntity;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
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

    public UnidadeMedidaEnum getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedidaEnum unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
