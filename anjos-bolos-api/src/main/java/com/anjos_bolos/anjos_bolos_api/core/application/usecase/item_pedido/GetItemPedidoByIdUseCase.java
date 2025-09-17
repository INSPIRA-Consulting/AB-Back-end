package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.GetItemPedidoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;

public class GetItemPedidoByIdUseCase {
    private final ItemPedidoGateway gateway;

    public GetItemPedidoByIdUseCase(ItemPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public ItemPedido execute(GetItemPedidoByIdQuery query) {
        ItemPedido itemPedido = gateway.findById(query.id());

        if (itemPedido == null) {
            throw new RuntimeException("Item de Pedido com ID [%d] não encontrado."
                    .formatted(query.id()));
        }

        return itemPedido;
    }
}