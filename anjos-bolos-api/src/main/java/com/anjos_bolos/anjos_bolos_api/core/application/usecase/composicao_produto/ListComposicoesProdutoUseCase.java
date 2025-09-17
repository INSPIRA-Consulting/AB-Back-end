package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.ListComposicoesProdutoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;

import java.util.List;

public class ListComposicoesProdutoUseCase {
    private final ComposicaoProdutoGateway gateway;

    public ListComposicoesProdutoUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<ComposicaoProduto> execute(ListComposicoesProdutoQuery query) {
        List<ComposicaoProduto> composicoesProduto = gateway.findAll();

        if (composicoesProduto.isEmpty()) {
            throw new NotFoundException("Não há Composições de Produtos cadastradas.");
        }

        return composicoesProduto;
    }
}
