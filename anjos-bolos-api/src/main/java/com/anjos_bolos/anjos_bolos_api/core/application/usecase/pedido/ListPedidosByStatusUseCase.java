package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByStatusQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;

import java.util.List;

public class ListPedidosByStatusUseCase {

    private final PedidoGateway gateway;

    public ListPedidosByStatusUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosByStatusQuery query) {
        try {
            StatusPedidoEnum.valueOf(query.status());
        } catch (IllegalArgumentException ex) {
            throw new InvalidArgumentException("Status inválido: " + query.status());
        }

        List<Pedido> pedidos = gateway.findByStatus(query.status());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido encontrado com o Status: " + query.status());
        }
        
        return pedidos;
    }

}