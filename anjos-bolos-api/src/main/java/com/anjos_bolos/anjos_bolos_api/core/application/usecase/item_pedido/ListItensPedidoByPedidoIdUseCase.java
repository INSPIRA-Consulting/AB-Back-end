package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.ListItensPedidoByPedidoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;

import java.util.List;

public class ListItensPedidoByPedidoIdUseCase {

    private final ItemPedidoGateway gateway;

    public ListItensPedidoByPedidoIdUseCase(ItemPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<ItemPedido> execute(ListItensPedidoByPedidoIdQuery query) {
        List<ItemPedido> itensPedido = gateway.findByPedidoId(query.pedidoId());

        if (itensPedido.isEmpty()) {
            throw new NotFoundException("Itens do Pedido com ID [%d] não encontrados."
                    .formatted(query.pedidoId()));
        }

        return itensPedido;
    }

}