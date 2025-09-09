package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Ingrediente")
public class IngredienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double custoMedida;

    public IngredienteEntity() {
    }

    public IngredienteEntity(Integer id, String nome, Double custoMedida) {
        this.id = id;
        this.nome = nome;
        this.custoMedida = custoMedida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCustoMedida() {
        return custoMedida;
    }

    public void setCustoMedida(Double custoMedida) {
        this.custoMedida = custoMedida;
    }
}
