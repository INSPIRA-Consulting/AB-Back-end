package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.GetCategoriaProdutoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.GetCategoriaProdutoByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class GetCategoriaProdutoByNomeUseCase {
    private final CategoriaProdutoGateway gateway;

    public GetCategoriaProdutoByNomeUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public CategoriaProduto execute(GetCategoriaProdutoByNomeQuery query) {
        CategoriaProduto categoriaProduto = gateway.findByNome(query.nome());

        if (categoriaProduto == null) {
            throw new NotFoundException("Nenhum Ingrediente encontrado com o nome: '%s'." + query.nome());
        }

        return categoriaProduto;
    }
}
