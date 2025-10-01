package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.UpdateCategoriaProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class UpdateCategoriaProdutoUseCase {

    private final CategoriaProdutoGateway gateway;

    public UpdateCategoriaProdutoUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public CategoriaProduto execute(UpdateCategoriaProdutoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Categoria de Produto com ID [%d] não encontrada.".formatted(command.id()));
        }

        CategoriaProduto categoriaProduto = gateway.findById(command.id());
        categoriaProduto.setNome(command.nome());
        categoriaProduto.setDescricao(command.descricao());

        return gateway.update(categoriaProduto);
    }

}