package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.ListItensPedidoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;

import java.util.List;

public class ListItensPedidoUseCase {

    private final ItemPedidoGateway gateway;

    public ListItensPedidoUseCase(ItemPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<ItemPedido> execute(ListItensPedidoQuery query) {
        List<ItemPedido> itensPedido = gateway.findAll();

        if (itensPedido.isEmpty()) {
            throw new NotFoundException("Não há Itens de Pedido cadastrados.");
        }

        return itensPedido;
    }

}