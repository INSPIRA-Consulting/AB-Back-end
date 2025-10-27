package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByClienteIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.util.List;

public class ListPedidosByClienteIdUseCase {

    private final PedidoGateway gateway;
    private final ClienteGateway clienteGateway;

    public ListPedidosByClienteIdUseCase(PedidoGateway gateway, ClienteGateway clienteGateway) {
        this.gateway = gateway;
        this.clienteGateway = clienteGateway;
    }

    public List<Pedido> execute(ListPedidosByClienteIdQuery query) {
        if (!clienteGateway.existsById(query.clienteId())) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado.".formatted(query.clienteId()));
        }

        List<Pedido> pedidos = gateway.findByClienteId(query.clienteId());

        if (pedidos.isEmpty()) {
            throw new NotFoundException("Nenhum pedido encontrado para o Cliente com ID [%d].".formatted(query.clienteId()));
        }

        return pedidos;
    }

}