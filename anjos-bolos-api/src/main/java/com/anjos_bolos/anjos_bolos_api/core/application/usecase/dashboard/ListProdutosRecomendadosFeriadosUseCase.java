package com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.ListProdutosRecomendadosFeriadosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.ProdutoRecomendadoFeriadoDTO;

import java.util.List;

public class ListProdutosRecomendadosFeriadosUseCase {

    private final DashboardGateway gateway;

    public ListProdutosRecomendadosFeriadosUseCase(DashboardGateway gateway) {
        this.gateway = gateway;
    }

    public List<ProdutoRecomendadoFeriadoDTO> execute(ListProdutosRecomendadosFeriadosQuery query) {
       if (query.feriados().isEmpty()) {
           throw new NotFoundException("Nenhum Feriado encontrardo.");
       }

       List<ProdutoRecomendadoFeriadoDTO> produtos = gateway.listProdutosRecomendadosFeriados(query.feriados());

       if (produtos.isEmpty()) {
              throw new NotFoundException("Nenhum Produto recomendado encontrado para os feriados informados.");
       }

       return produtos;
    }

}
