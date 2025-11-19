package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.feriados.FeriadosDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.ProdutoRecomendadoFeriadoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.VendasDiaSemanaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados.FeriadosResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashboardGateway {

    MargemLucroProdutoDTO getMenorMargemLucroProdutos(Integer limit);

    MargemLucroProdutoDTO getMaiorMargemLucroProdutos(Integer limit);

    List<ProdutoVendidoDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit);

    List<PedidosFaturamentoDTO> getPedidosFaturamento(LocalDate inicio, LocalDate fim);

    String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit);

    List<VendasDiaSemanaDTO> listVendasPorDiaSemana(LocalDate inicio, LocalDate fim);

    List<ProdutoRecomendadoFeriadoDTO>listProdutosRecomendadosFeriados(List<FeriadosDTO> feriados);


}