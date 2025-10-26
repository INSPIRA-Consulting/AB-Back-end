package com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.valueobject.ItemComposicao;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public class ComposicaoProduto {

    private Produto produto;
    private List<ItemComposicao> receitas;

    public ComposicaoProduto() {
    }

    public ComposicaoProduto(Produto produto, List<ItemComposicao> receitas) {
        this.produto = produto;
        this.receitas = receitas;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<ItemComposicao> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<ItemComposicao> receitas) {
        this.receitas = receitas;
    }

}