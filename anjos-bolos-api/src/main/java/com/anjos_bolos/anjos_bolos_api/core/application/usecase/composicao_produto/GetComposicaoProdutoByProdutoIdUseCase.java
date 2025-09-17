package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.GetComposicaoProdutoByProdutoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

public class GetComposicaoProdutoByProdutoIdUseCase {
    private final ComposicaoProdutoGateway gateway;

    public GetComposicaoProdutoByProdutoIdUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ComposicaoProduto execute(GetComposicaoProdutoByProdutoIdQuery query) {
        ComposicaoProduto composicaoProduto = gateway.findByProdutoId(query.produtoId());

        if (composicaoProduto == null) {
            throw new NotFoundException("Não há nenhuma Composição de Produto para o Produto com ID [%d]."
                    .formatted(query.produtoId()));
        }

        return composicaoProduto;
    }
}