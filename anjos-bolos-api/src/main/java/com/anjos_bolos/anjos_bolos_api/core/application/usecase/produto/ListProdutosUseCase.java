package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.ListProdutosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

import java.util.List;

public class ListProdutosUseCase {
    private final ProdutoGateway gateway;

    public ListProdutosUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Produto> execute(ListProdutosQuery query) {
        List<Produto> produtos = gateway.findAll();

        if (produtos.isEmpty()) {
            throw new NotFoundException("Não há Produtos cadastrados.");
        }

        return produtos;
    }
}
