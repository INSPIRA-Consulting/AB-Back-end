package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReceitaPrimaryKey implements Serializable {
    private Integer fkProduto;
    private Integer fkIngrediente;

    public ReceitaPrimaryKey() {
    }

    public ReceitaPrimaryKey(Integer fkProduto, Integer fkIngrediente) {
        this.fkProduto = fkProduto;
        this.fkIngrediente = fkIngrediente;
    }

    @Column(name = "fk_produto")
    public Integer getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Integer fkProduto) {
        this.fkProduto = fkProduto;
    }

    @Column(name = "fk_ingrediente")
    public Integer getFkIngrediente() {
        return fkIngrediente;
    }

    public void setFkIngrediente(Integer fkIngrediente) {
        this.fkIngrediente = fkIngrediente;
    }
}
