package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.DeleteCategoriaProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteCategoriaProdutoUseCase {

    private final CategoriaProdutoGateway gateway;

    public DeleteCategoriaProdutoUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteCategoriaProdutoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Categoria de Produto com ID [%d] não encontrada.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}