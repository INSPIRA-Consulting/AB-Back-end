package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.DeleteDetalhamentoPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteDetalhamentoPedidoUseCase {

    private final DetalhamentoPedidoGateway gateway;

    public DeleteDetalhamentoPedidoUseCase(DetalhamentoPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteDetalhamentoPedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Detalhamento de Pedido com ID [%d] não encontrado."
                    .formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}