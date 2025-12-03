package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByDataRetiradaQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public class ListPedidosByDataRetiradaUseCase{

    private final PedidoGateway gateway;

    public ListPedidosByDataRetiradaUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosByDataRetiradaQuery query) {

        List<Pedido> pedidos = gateway.findByDataRetirada(query.dataRetiradaInicio(), query.dataRetiradaFim());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido encontrado para a Data de Retirada [%s]."
                    .formatted(query.dataRetiradaInicio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }

        return pedidos;
    }

}