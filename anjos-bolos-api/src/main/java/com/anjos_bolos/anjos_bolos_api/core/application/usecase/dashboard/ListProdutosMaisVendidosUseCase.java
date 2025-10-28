package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.ListProdutosMaisVendidosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.ProdutoVendidoDTO;

import java.util.List;

public class ListProdutosMaisVendidosUseCase {

    private final DashboardGateway gateway;

    public ListProdutosMaisVendidosUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public List<ProdutoVendidoDTO> execute(ListProdutosMaisVendidosQuery query) {
        if (query.limit() == null || query.limit() <= 0) {
            throw new InvalidArgumentException("Valor de LIMIT deve ser >= a 1.");
        }

        if (query.inicio() == null || query.fim() == null || query.inicio().isAfter(query.fim())) {
            throw new InvalidArgumentException("Período inválido.");
        }

        List<ProdutoVendidoDTO> produtos = gateway.listProdutosMaisVendidos(query.inicio(), query.fim(), query.limit());

        if (produtos.isEmpty()) {
            throw new NotFoundException("Nenhum Produto encontrado.");
        }

        return produtos;
    }

}