package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.ListProdutosByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

import java.util.List;

public class ListProdutosByNomeUseCase {
    private final ProdutoGateway gateway;

    public ListProdutosByNomeUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Produto> execute(ListProdutosByNomeQuery query) {
        List<Produto> produtos = gateway.findByNome(query.nome());

        if (produtos.isEmpty()) {
            throw new NotFoundException("Nenhum produto encontrado com o nome: %s"
                    .formatted(query.nome()));
        }

        return produtos;
    }
}
