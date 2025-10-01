package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double precoFinal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkCategoriaProduto")
    private CategoriaProdutoEntity categoriaProduto;

    public ProdutoEntity() {
    }

    public ProdutoEntity(Integer id, String nome, Double precoFinal, CategoriaProdutoEntity categoriaProdutoId) {
        this.id = id;
        this.nome = nome;
        this.precoFinal = precoFinal;
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

    public Double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(Double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public CategoriaProdutoEntity getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProdutoEntity categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

}