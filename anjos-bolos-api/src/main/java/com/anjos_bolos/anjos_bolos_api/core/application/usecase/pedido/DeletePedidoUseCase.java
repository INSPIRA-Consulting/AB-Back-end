package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.DeletePedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeletePedidoUseCase {

    private final PedidoGateway gateway;

    public DeletePedidoUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeletePedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}