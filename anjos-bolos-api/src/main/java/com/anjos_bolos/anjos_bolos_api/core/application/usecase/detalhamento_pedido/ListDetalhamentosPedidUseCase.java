package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.ListDetalhamentosPedidoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;

import java.util.List;

public class ListDetalhamentosPedidUseCase {

    private final DetalhamentoPedidoGateway gateway;

    public ListDetalhamentosPedidUseCase(DetalhamentoPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<DetalhamentoPedido> execute(ListDetalhamentosPedidoQuery query) {
        List<DetalhamentoPedido> detalhamentosPedido = gateway.findAll();

        if (detalhamentosPedido.isEmpty()) {
            throw new NotFoundException("Não há Detalhamentos de Pedidos cadastrados.");
        }

        return detalhamentosPedido;
    }

}