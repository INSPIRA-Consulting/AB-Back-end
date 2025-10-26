package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.ListComposicoesProdutoByProdutoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;


public class ListComposicoesProdutoByProdutoIdUseCase {

    private final ComposicaoProdutoGateway gateway;

    public ListComposicoesProdutoByProdutoIdUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ComposicaoProduto execute(ListComposicoesProdutoByProdutoIdQuery query) {
        if (!gateway.existsByProdutoId(query.produtoId())) {
            throw new NotFoundException("Composições para o Produto com ID [%d] não encontradas."
                    .formatted(query.produtoId()));
        }

        ComposicaoProduto composicaoProduto = gateway.findAllByProdutoId(query.produtoId());

        if (composicaoProduto == null) {
            throw new NotFoundException("Composições para o Produto com ID [%d] não encontradas."
                    .formatted(query.produtoId()));
        }

        return composicaoProduto;
    }

}