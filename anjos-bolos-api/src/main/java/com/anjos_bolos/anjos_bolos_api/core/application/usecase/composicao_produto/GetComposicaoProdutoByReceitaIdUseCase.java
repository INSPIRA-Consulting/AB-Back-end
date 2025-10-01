package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.GetComposicaoProdutoByReceitaIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

public class GetComposicaoProdutoByReceitaIdUseCase {

    private final ComposicaoProdutoGateway gateway;

    public GetComposicaoProdutoByReceitaIdUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public ComposicaoProduto execute(GetComposicaoProdutoByReceitaIdQuery query) {
        ComposicaoProduto composicaoProduto = gateway.findByReceitaId(query.receitaId());

        if (composicaoProduto == null) {
            throw new NotFoundException("Não há nenhuma Composição de Produto para a Receita com ID [%d]."
                    .formatted(query.receitaId()));
        }

        return composicaoProduto;
    }

}