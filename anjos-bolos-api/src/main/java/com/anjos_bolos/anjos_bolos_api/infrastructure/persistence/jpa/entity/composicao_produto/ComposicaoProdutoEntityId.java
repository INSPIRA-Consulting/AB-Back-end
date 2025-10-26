package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntityId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ComposicaoProdutoEntityId implements Serializable {

    private Integer fkProduto;
    private Integer fkReceita;
    private Integer fkIngrediente;

    public ComposicaoProdutoEntityId() {
    }

    public ComposicaoProdutoEntityId(Integer fkProduto, Integer fkReceita, Integer fkIngrediente) {
        this.fkProduto = fkProduto;
        this.fkReceita = fkReceita;
        this.fkIngrediente = fkIngrediente;
    }

    public Integer getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Integer fkProduto) {
        this.fkProduto = fkProduto;
    }

    public Integer getFkReceita() {
        return fkReceita;
    }

    public void setFkReceita(Integer fkReceita) {
        this.fkReceita = fkReceita;
    }

    public Integer getFkIngrediente() {
        return fkIngrediente;
    }

    public void setFkIngrediente(Integer fkIngrediente) {
        this.fkIngrediente = fkIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComposicaoProdutoEntityId that = (ComposicaoProdutoEntityId) o;
        return Objects.equals(fkProduto, that.fkProduto) && Objects.equals(fkReceita, that.fkReceita)
                && Objects.equals(fkIngrediente, that.fkIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkProduto, fkReceita, fkIngrediente);
    }
}