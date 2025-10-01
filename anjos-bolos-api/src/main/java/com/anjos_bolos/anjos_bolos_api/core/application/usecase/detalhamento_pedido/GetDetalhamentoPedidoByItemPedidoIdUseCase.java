package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.GetDetalhamentoPedidoByItemPedidoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;

public class GetDetalhamentoPedidoByItemPedidoIdUseCase {

    private final DetalhamentoPedidoGateway gateway;

    public GetDetalhamentoPedidoByItemPedidoIdUseCase(DetalhamentoPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public DetalhamentoPedido execute(GetDetalhamentoPedidoByItemPedidoIdQuery query) {
        DetalhamentoPedido detalhamentoPedido = gateway.findByItemPedidoId(query.itemPedidoId());

        if (detalhamentoPedido == null) {
            throw new RuntimeException("Detalhamento de Pedido contendo Item de Pedido com ID [%d] não encontrado."
                    .formatted(query.itemPedidoId()));
        }

        return detalhamentoPedido;
    }

}