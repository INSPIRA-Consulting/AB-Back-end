package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.GetPedidoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

public class GetPedidoByIdUseCase {

    private final PedidoGateway gateway;

    public GetPedidoByIdUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public Pedido execute(GetPedidoByIdQuery query) {
        Pedido pedido = gateway.findById(query.id());

        if (pedido == null) {
            throw  new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(query.id()));
        }

        return pedido;
    }

}