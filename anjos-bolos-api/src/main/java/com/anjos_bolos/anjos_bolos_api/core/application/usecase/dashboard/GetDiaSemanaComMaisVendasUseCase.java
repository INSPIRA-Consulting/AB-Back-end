package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetDiaSemanaComMaisVendasQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetMaiorMargemLucroQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;

public class GetDiaSemanaComMaisVendasUseCase {

    private final DashboardGateway gateway;

    public GetDiaSemanaComMaisVendasUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public String execute(GetDiaSemanaComMaisVendasQuery query) {
        if (query.limit() == null || query.limit() <= 0) {
            throw new InvalidArgumentException("Valor de LIMIT deve ser >= a 1.");
        }

        if (query.inicio() == null || query.fim() == null || query.inicio().isAfter(query.fim())) {
            throw new InvalidArgumentException("Período inválido.");
        }

        String diaSemana = gateway.getDiaSemanaComMaisVendas(query.inicio(), query.fim(), query.limit());

        if (diaSemana == null) {
            throw new NotFoundException("Nenhum Produto encontrado.");
        }

        return diaSemana;
    }

}