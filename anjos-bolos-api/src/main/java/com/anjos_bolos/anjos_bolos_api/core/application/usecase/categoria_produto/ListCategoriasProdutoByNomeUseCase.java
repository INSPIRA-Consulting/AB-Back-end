package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.ListCategoriasProdutoByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

import java.util.List;

public class ListCategoriasProdutoByNomeUseCase {
    private final CategoriaProdutoGateway gateway;

    public ListCategoriasProdutoByNomeUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<CategoriaProduto> execute(ListCategoriasProdutoByNomeQuery query) {
        List<CategoriaProduto> categoriasProduto = gateway.findByNome(query.nome());

        if (categoriasProduto.isEmpty()) {
            throw new NotFoundException("Nenhum Ingrediente encontrado com o nome: '%s'."
                    .formatted(query.nome()));
        }

        return categoriasProduto;
    }
}
