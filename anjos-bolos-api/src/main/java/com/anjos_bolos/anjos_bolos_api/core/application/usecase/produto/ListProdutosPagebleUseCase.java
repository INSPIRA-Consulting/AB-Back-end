package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.ListProdutosPagebleQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.ListProdutosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import org.springframework.data.domain.Page;

import java.util.List;

public class ListProdutosPagebleUseCase {
    private final ProdutoGateway gateway;

    public ListProdutosPagebleUseCase (ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Page<Produto> execute(ListProdutosPagebleQuery query) {
        Page<Produto> produtos = gateway.findAll(query.pageable());

        if (produtos.isEmpty()) {
            throw new NotFoundException("Não há Produtos cadastrados.");
        }

        return produtos;
    }
}
