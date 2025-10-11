package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.TipoReceitaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Receita", uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "fkIngrediente"}))
@IdClass(ReceitaEntityId.class)
public class ReceitaEntity {

    @Id
    private Integer id;

    @Id
    @Column(name = "fkIngrediente")
    private Integer fkIngrediente;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "fkIngrediente", insertable = false, updatable = false)
    private IngredienteEntity ingrediente;

    private Double quantidade;

    private String unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "fkTipoReceita")
    private TipoReceitaEntity tipoReceita;

    public ReceitaEntity() {
    }

    public ReceitaEntity(Integer id, Integer fkIngrediente, String nome, IngredienteEntity ingrediente, Double quantidade, String unidadeMedida, TipoReceitaEntity tipoReceita) {
        this.id = id;
        this.fkIngrediente = fkIngrediente;
        this.nome = nome;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.tipoReceita = tipoReceita;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public IngredienteEntity getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteEntity ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public TipoReceitaEntity getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceitaEntity tipoReceita) {
        this.tipoReceita = tipoReceita;
    }
}