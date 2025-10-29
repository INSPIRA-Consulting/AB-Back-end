package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.ListDetalhamentoPedidoByItemPedidoIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;

import java.util.List;

public class ListDetalhamentoPedidoByItemPedidoIdUseCase {

    private final DetalhamentoPedidoGateway gateway;

    public ListDetalhamentoPedidoByItemPedidoIdUseCase(DetalhamentoPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<DetalhamentoPedido> execute(ListDetalhamentoPedidoByItemPedidoIdQuery query) {
        List<DetalhamentoPedido> detalhamentoPedido = gateway.findByItemPedidoId(query.itemPedidoId());

        if (detalhamentoPedido == null) {
            throw new RuntimeException("Detalhamento de Pedido contendo Item de Pedido com ID [%d] não encontrado."
                    .formatted(query.itemPedidoId()));
        }

        return detalhamentoPedido;
    }

}