package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetMaiorMargemLucroQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;

public class GetMaiorMargemLucroUseCase {

    private final DashboardGateway gateway;

    public GetMaiorMargemLucroUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public MargemLucroProdutoDTO execute(GetMaiorMargemLucroQuery query) {
        if (query.limit() == null || query.limit() <= 0) {
            throw new InvalidArgumentException("Valor de LIMIT deve ser >= a 1.");
        }

        MargemLucroProdutoDTO maiorMargemLucro = gateway.getMaiorMargemLucroProdutos(query.limit());

        if (maiorMargemLucro == null) {
            throw new NotFoundException("Nenhum Produto encontrado.");
        }

        return maiorMargemLucro;
    }

}