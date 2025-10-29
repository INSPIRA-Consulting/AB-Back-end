package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.PedidosFaturamentoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.ProdutoVendidoDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashboardGateway {

    MargemLucroProdutoDTO getMenorMargemLucroProdutos(Integer limit);

    MargemLucroProdutoDTO getMaiorMargemLucroProdutos(Integer limit);

    List<ProdutoVendidoDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit);

    PedidosFaturamentoDTO getPedidosFaturamento(LocalDate inicio, LocalDate fim);

    String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit);

    String getDiaSemanaComMaisVendas(LocalDate inicio, LocalDate fim, Integer limit);

}