package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.MargemLucroProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.PedidosFaturamentoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.ProdutoVendidoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashboardJpaRepository {

    MargemLucroProdutoResponseDTO getMenorMargemLucroProdutos(Integer limit);

    MargemLucroProdutoResponseDTO getMaiorMargemLucroProdutos(Integer limit);

    List<ProdutoVendidoResponseDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit);

    PedidosFaturamentoResponseDTO getPedidosFaturamento(LocalDate inicio, LocalDate fim);

    String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit);

    String getDiaSemanaComMaisVendas(LocalDate inicio, LocalDate fim, Integer limit);
}