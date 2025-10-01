package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.DeleteItemPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteItemPedidoUseCase {

    private final ItemPedidoGateway gateway;

    public DeleteItemPedidoUseCase(ItemPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteItemPedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Item de Pedido com ID [%d] não encontrado."
                    .formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}