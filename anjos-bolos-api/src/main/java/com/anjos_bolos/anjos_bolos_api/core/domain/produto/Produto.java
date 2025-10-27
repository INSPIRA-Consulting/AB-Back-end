package com.anjos_bolos.anjos_bolos_api.core.domain.produto;

import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class Produto {

    private Integer id;
    private String nome;
    private Double precoFinal;
    private Double custoProducao;
    private CategoriaProduto categoriaProduto;

    public Produto() {
    }

    public Produto(String nome, Double precoFinal, Double custoProducao, CategoriaProduto categoriaProduto) {
        this.nome = nome;
        this.precoFinal = precoFinal;
        this.custoProducao = custoProducao;
        this.categoriaProduto = categoriaProduto;
    }

    public Produto(Integer id, String nome, Double precoFinal, Double custoProducao, CategoriaProduto categoriaProduto) {
        this.id = id;
        this.nome = nome;
        this.precoFinal = precoFinal;
        this.custoProducao = custoProducao;
        this.categoriaProduto = categoriaProduto;
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

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public void setPrecoFinal(Double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public Double getCustoProducao() {
        return custoProducao;
    }

    public void setCustoProducao(Double custoProducao) {
        this.custoProducao = custoProducao;
    }

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

}