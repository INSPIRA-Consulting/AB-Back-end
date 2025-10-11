package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReceitaEntityId implements Serializable {

    private Integer id;
    private Integer fkIngrediente;

    public ReceitaEntityId() {
    }

    public ReceitaEntityId(Integer id, Integer fkIngrediente) {
        this.id = id;
        this.fkIngrediente = fkIngrediente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        ReceitaEntityId that = (ReceitaEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(fkIngrediente, that.fkIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkIngrediente);
    }
}