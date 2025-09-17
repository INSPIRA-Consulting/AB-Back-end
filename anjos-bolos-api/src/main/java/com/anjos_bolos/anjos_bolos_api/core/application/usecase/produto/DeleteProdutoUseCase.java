package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.DeleteProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteProdutoUseCase {
    private final ProdutoGateway gateway;

    public DeleteProdutoUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteProdutoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Produto com ID [%s] não encontrado.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }
}
