package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.ListVendasPorDiaSemanaQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.VendasDiaSemanaDTO;

import java.util.List;

public class ListVendasPorDiaSemanaUseCase {

    private final DashboardGateway gateway;

    public ListVendasPorDiaSemanaUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public List<VendasDiaSemanaDTO> execute(ListVendasPorDiaSemanaQuery query) {
        if (query.inicio() == null || query.fim() == null || query.inicio().isAfter(query.fim())) {
            throw new InvalidArgumentException("Período inválido.");
        }

        List<VendasDiaSemanaDTO> vendas = gateway.listVendasPorDiaSemana(query.inicio(), query.fim());

        if (vendas.isEmpty()) {
            throw new NotFoundException("Nenhum Pedido encontrado.");
        }

        return vendas;
    }

}