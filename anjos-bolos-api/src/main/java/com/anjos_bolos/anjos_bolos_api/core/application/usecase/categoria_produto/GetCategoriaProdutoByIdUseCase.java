package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.GetCategoriaProdutoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class GetCategoriaProdutoByIdUseCase {
    private final CategoriaProdutoGateway gateway;

    public GetCategoriaProdutoByIdUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public CategoriaProduto execute(GetCategoriaProdutoByIdQuery query) {
        CategoriaProduto categoriaProduto = gateway.findById(query.id());

        if (categoriaProduto == null) {
            throw new NotFoundException("Categoria de Produto com ID [%d] não encontrada.".formatted(query.id()));
        }

        return categoriaProduto;
    }
}