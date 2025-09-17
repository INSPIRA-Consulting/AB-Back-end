package com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class ComposicaoProduto {
    private Integer id;
    private Produto produto;
    private Receita receita;
    private Integer quantidade;
    private String observacao;

    public ComposicaoProduto() {
    }

    public ComposicaoProduto(Produto produto, Receita receita, Integer quantidade, String observacao) {
        this.produto = produto;
        this.receita = receita;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public ComposicaoProduto(Integer id, Produto produto, Receita receita, Integer quantidade, String observacao) {
        this.id = id;
        this.produto = produto;
        this.receita = receita;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
