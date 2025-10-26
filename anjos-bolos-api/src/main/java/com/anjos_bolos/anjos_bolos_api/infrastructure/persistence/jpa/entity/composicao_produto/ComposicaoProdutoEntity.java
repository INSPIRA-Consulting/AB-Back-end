package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ProdutoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Composicao_Produto", uniqueConstraints = @UniqueConstraint(columnNames = {"fkProduto", "fkReceita", "fkIngrediente"}))
@IdClass(ComposicaoProdutoEntityId.class)
public class ComposicaoProdutoEntity {

    @Id
    @Column(name = "fkProduto")
    private Integer fkProduto;

    @Id
    @Column(name = "fkReceita")
    private Integer fkReceita;

    @Id
    @Column(name = "fkIngrediente")
    private Integer fkIngrediente;

    @ManyToOne
    @JoinColumn(name = "fkProduto", insertable = false, updatable = false)
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "fkReceita", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "fkIngrediente", referencedColumnName = "fkIngrediente", insertable = false, updatable = false)
    })
    private ReceitaEntity receita;

    private Double quantidade;

    private String observacao;

    public ComposicaoProdutoEntity() {
    }

    public ComposicaoProdutoEntity(Integer fkProduto, Integer fkReceita, Integer fkIngrediente, ProdutoEntity produto, ReceitaEntity receita, Double quantidade, String observacao) {
        this.fkProduto = fkProduto;
        this.fkReceita = fkReceita;
        this.fkIngrediente = fkIngrediente;
        this.produto = produto;
        this.receita = receita;
        this.quantidade = quantidade;
        this.observacao = observacao;
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

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public ReceitaEntity getReceita() {
        return receita;
    }

    public void setReceita(ReceitaEntity receita) {
        this.receita = receita;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}