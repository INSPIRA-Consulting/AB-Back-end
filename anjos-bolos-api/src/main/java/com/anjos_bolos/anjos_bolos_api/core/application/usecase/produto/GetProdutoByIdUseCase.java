package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.GetProdutoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class GetProdutoByIdUseCase {
    private final ProdutoGateway gateway;

    public GetProdutoByIdUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto execute(GetProdutoByIdQuery query) {
        Produto produto = gateway.findById(query.id());

        if (produto == null) {
            throw new NotFoundException("Produto com ID [%d] não encontrado.".formatted(query.id()));
        }

        return produto;
    }
}