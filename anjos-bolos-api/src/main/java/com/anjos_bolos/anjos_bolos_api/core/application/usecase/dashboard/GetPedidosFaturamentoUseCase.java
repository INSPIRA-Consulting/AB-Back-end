package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetDiaSemanaComMaisVendasQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetPedidosFaturamentoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.PedidosFaturamentoDTO;

public class GetPedidosFaturamentoUseCase {

    private final DashboardGateway gateway;

    public GetPedidosFaturamentoUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public PedidosFaturamentoDTO execute(GetPedidosFaturamentoQuery query) {

        if (query.inicio() == null || query.fim() == null || query.inicio().isAfter(query.fim())) {
            throw new InvalidArgumentException("Período inválido.");
        }

        PedidosFaturamentoDTO pedidosFaturamento = gateway.getPedidosFaturamento(query.inicio(), query.fim());

        if (pedidosFaturamento == null) {
            throw new NotFoundException("Nenhum Pedido encontrado.");
        }

        return pedidosFaturamento;
    }

}