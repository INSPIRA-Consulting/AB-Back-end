package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetMaiorMargemLucroQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetMenorMargemLucroQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;

public class GetMenorMargemLucroUseCase {

    private final DashboardGateway gateway;

    public GetMenorMargemLucroUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public MargemLucroProdutoDTO execute(GetMenorMargemLucroQuery query) {
        if (query.limit() == null || query.limit() <= 0) {
            throw new InvalidArgumentException("Valor de LIMIT deve ser >= a 1.");
        }

        MargemLucroProdutoDTO menorMargemLucro = gateway.getMenorMargemLucroProdutos(query.limit());

        if (menorMargemLucro == null) {
            throw new NotFoundException("Nenhum Produto encontrado.");
        }

        return menorMargemLucro;
    }

}