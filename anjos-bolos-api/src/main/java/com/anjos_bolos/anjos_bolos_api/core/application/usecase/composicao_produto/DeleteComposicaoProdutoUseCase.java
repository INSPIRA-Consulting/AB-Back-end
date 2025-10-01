package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.DeleteComposicaoProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteComposicaoProdutoUseCase {

    private final ComposicaoProdutoGateway gateway;

    public DeleteComposicaoProdutoUseCase(ComposicaoProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteComposicaoProdutoCommand command) {
        if (gateway.existsById(command.id())) {
            throw new NotFoundException("Composição de Produto com ID [%d] não encontrada.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}