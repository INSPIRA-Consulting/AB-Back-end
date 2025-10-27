package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByDataPedidoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public class ListPedidosByDataPedidoUseCase {

    private final PedidoGateway gateway;

    public ListPedidosByDataPedidoUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosByDataPedidoQuery query) {
        if (query.dataPedido().isAfter(LocalDateTime.now())) {
            throw new InvalidArgumentException("A Data do Pedido não pode ser no futuro.");
        }

        List<Pedido> pedidos = gateway.findByDataPedido(query.dataPedido());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido encontrado para a Data [%s]."
                    .formatted(query.dataPedido()));
        }

        return pedidos;
    }

}