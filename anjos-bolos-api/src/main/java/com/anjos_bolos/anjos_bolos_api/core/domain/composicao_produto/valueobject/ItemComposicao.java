package com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class ItemComposicao {

    private Receita receita;
    private Double quantidade;
    private String observacao;

    public ItemComposicao() {
    }

    public ItemComposicao(Receita receita, Double quantidade, String observacao) {
        this.receita = receita;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
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