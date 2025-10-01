package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.GetDetalhamentoPedidoByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;

public class GetDetalhamentoPedidoByIdUseCase {

    private final DetalhamentoPedidoGateway gateway;

    public GetDetalhamentoPedidoByIdUseCase(DetalhamentoPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public DetalhamentoPedido execute(GetDetalhamentoPedidoByIdQuery query) {
        DetalhamentoPedido detalhamentoPedido = gateway.findById(query.id());

        if (detalhamentoPedido == null) {
            throw new NotFoundException("Detalhamento de Pedido com ID [%d] não encontrado."
                    .formatted(query.id()));
        }

        return detalhamentoPedido;
    }

}