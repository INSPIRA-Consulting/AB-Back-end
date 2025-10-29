package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetDiaSemanaComMaisVendasQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.GetProdutoMaisVendidoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class GetProdutoMaisVendidoUseCase {

    private final DashboardGateway gateway;

    public GetProdutoMaisVendidoUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public String execute(GetProdutoMaisVendidoQuery query) {
        if (query.limit() == null || query.limit() <= 0) {
            throw new InvalidArgumentException("Valor de LIMIT deve ser >= a 1.");
        }

        if (query.inicio() == null || query.fim() == null || query.inicio().isAfter(query.fim())) {
            throw new InvalidArgumentException("Período inválido.");
        }

        String produtoMaisVendido = gateway.getProdutoMaisVendido(query.inicio(), query.fim(), query.limit());

        if (produtoMaisVendido == null) {
            throw new NotFoundException("Nenhum Produto encontrado.");
        }

        return produtoMaisVendido;
    }

}