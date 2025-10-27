package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.util.List;

public class ListPedidosUseCase {

    private final PedidoGateway gateway;

    public ListPedidosUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosQuery query) {
        List<Pedido> pedidos = gateway.findAll();

        if (pedidos.isEmpty()) {
            throw new NotFoundException("Não há pedidos cadastrados.");
        }

        return pedidos;
    }

}