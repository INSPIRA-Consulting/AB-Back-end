package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.ListCategoriasProdutoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

import java.util.List;

public class ListCategoriasProdutoUseCase {

    private final CategoriaProdutoGateway gateway;

    public ListCategoriasProdutoUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<CategoriaProduto> execute(ListCategoriasProdutoQuery query) {
        List<CategoriaProduto> categoriasProduto = gateway.findAll();

        if (categoriasProduto.isEmpty()) {
            throw new NotFoundException("Não há Categorias de Produto cadastradas.");
        }

        return categoriasProduto;
    }

}