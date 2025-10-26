package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

import java.util.List;

public interface ComposicaoProdutoGateway {

    ComposicaoProduto save(ComposicaoProduto composicaoProduto);

    boolean existsByProdutoId(Integer produtoId);

    boolean existsByProdutoIdAndReceitaId(Integer produtoId, Integer receitaId);

    ComposicaoProduto findAllByProdutoId(Integer produtoId);

    ComposicaoProduto update(ComposicaoProduto composicaoProduto);

    void delete(Integer id);

}