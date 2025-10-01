package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

import java.util.List;

public interface ComposicaoProdutoGateway {

    ComposicaoProduto save(ComposicaoProduto composicaoProduto);

    boolean existsById(Integer id);

    boolean existsByProdutoIdAndReceitaId(Integer produtoId, Integer receitaId);

    boolean existsByProdutoIdAndReceitaIdAndIdNot(Integer produtoId, Integer receitaId, Integer id);

    List<ComposicaoProduto> findAll();

    ComposicaoProduto findById(Integer id);

    ComposicaoProduto findByProdutoId(Integer produtoId);

    ComposicaoProduto findByReceitaId(Integer receitaId);

    ComposicaoProduto update(ComposicaoProduto composicaoProduto);

    void delete(Integer id);

}