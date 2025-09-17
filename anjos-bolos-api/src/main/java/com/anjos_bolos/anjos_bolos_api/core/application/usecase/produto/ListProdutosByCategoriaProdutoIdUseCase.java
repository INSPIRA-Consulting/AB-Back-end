package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.ListProdutosByCategoriaProdutoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

import java.util.List;

public class ListProdutosByCategoriaProdutoIdUseCase {
    private final ProdutoGateway gateway;

    public ListProdutosByCategoriaProdutoIdUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Produto> execute(ListProdutosByCategoriaProdutoIdQuery query) {
        List<Produto> produtos = gateway.findByCategoriaProdutoId(query.categoriaProdutoId());

        if (produtos.isEmpty()) {
            throw new NotFoundException("Nenhum produto encontrado para a Categoria com ID [%d]"
                    .formatted(query.categoriaProdutoId()));
        }

        return produtos;
    }
}
