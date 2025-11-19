package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados.FeriadosResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashboardJpaRepository {

    MargemLucroProdutoResponseDTO getMenorMargemLucroProdutos(Integer limit);

    MargemLucroProdutoResponseDTO getMaiorMargemLucroProdutos(Integer limit);

    List<ProdutoVendidoResponseDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit);

    List<PedidosFaturamentoResponseDTO> getPedidosFaturamento(LocalDate inicio, LocalDate fim);

    String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit);

    List<VendasDiaSemanaResponseDTO> listVendasPorDiaSemana(LocalDate inicio, LocalDate fim);

    List<ProdutoRecomendadoFeriadoResponseDTO>listProdutosRecomendadosFeriados(List<FeriadosResponseDTO> feriados);
}