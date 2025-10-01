package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.GetComposicaoProdutoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

public class GetComposicaoProdutoByIdUseCase {

    private final ComposicaoProdutoGateway gateway;

    public GetComposicaoProdutoByIdUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ComposicaoProduto execute(GetComposicaoProdutoByIdQuery query) {
        ComposicaoProduto composicaoProduto = gateway.findById(query.id());

        if (composicaoProduto == null) {
            throw new NotFoundException("Composição de Produto com ID [%d] não encontrada."
                    .formatted(query.id()));
        }

        return composicaoProduto;
    }

}