package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.feriados.FeriadosDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados.FeriadosResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class DashboardMapper {

    public static GetMenorMargemLucroQuery toGetMenorMargemLucroQuery(Integer limit) {
        return new GetMenorMargemLucroQuery(limit);
    }

    public static GetMaiorMargemLucroQuery toGetMaiorMargemLucroQuery(Integer limit) {
        return new GetMaiorMargemLucroQuery(limit);
    }

    public static MargemLucroProdutoResponseDTO toResponse(MargemLucroProdutoDTO domain) {
        return new MargemLucroProdutoResponseDTO(
                domain.nomeProduto(),
                domain.margemLucro()
        );
    }

    public static MargemLucroProdutoDTO toDTO(MargemLucroProdutoResponseDTO dto) {
        return new MargemLucroProdutoDTO(
                dto.nomeProduto(),
                dto.margemLucro()
        );
    }

    public static GetPedidosFaturamentoQuery toGetPedidosFaturamentoQuery(LocalDate inicio, LocalDate fim) {
        return new GetPedidosFaturamentoQuery(inicio, fim);
    }

    public static List<PedidosFaturamentoResponseDTO> toFaturamentoResponse(List<PedidosFaturamentoDTO> domain) {
        return domain.stream()
                .map(faturamento -> new PedidosFaturamentoResponseDTO(
                        faturamento.quantidadePedidos(),
                        faturamento.quantidadeProdutosVendidos(),
                        faturamento.faturamento(),
                        faturamento.custos(),
                        faturamento.dataPedido()
                ))
                .toList();
    }

    public static PedidosFaturamentoDTO toDTO(PedidosFaturamentoResponseDTO dto) {
        return new PedidosFaturamentoDTO(
                dto.quantidadePedidos(),
                dto.quantidadeProdutosVendidos(),
                dto.faturamento(),
                dto.custos(),
                dto.dataPedido()
        );
    }

    public static ListProdutosMaisVendidosQuery toListProdutosMaisVendidosQuery(LocalDate inicio, LocalDate fim, Integer limit) {
        return new ListProdutosMaisVendidosQuery(inicio, fim, limit);
    }

    public static List<ProdutoVendidoResponseDTO> toProdutosVendidosResponse(List<ProdutoVendidoDTO> domain) {
        return domain.stream()
                .map(produto -> new ProdutoVendidoResponseDTO(
                        produto.nomeProduto(),
                        produto.quantidadeVendida(),
                        produto.categoriaProduto()
                ))
                .toList();
    }

    public static ProdutoVendidoDTO toDTO(ProdutoVendidoResponseDTO dto) {
        return new ProdutoVendidoDTO(
                dto.nomeProduto(),
                dto.quantidadeVendida(),
                dto.categoriaProduto()
        );
    }

    public static GetProdutoMaisVendidoQuery toGetProdutoMaisVendidoQuery(LocalDate inicio, LocalDate fim, Integer limit) {
        return new GetProdutoMaisVendidoQuery(inicio, fim, limit);
    }

    public static VendasDiaSemanaDTO toDTO(VendasDiaSemanaResponseDTO dto) {
        return new VendasDiaSemanaDTO(
                dto.diaSemana(),
                dto.totalVendas()
        );
    }

    public static List<VendasDiaSemanaResponseDTO> toVendasDiaSemanaResponse(List<VendasDiaSemanaDTO> domain) {
        return domain.stream()
                .map(venda -> new VendasDiaSemanaResponseDTO(
                        venda.diaSemana(),
                        venda.totalVendas()
                ))
                .toList();
    }

    public static ListVendasPorDiaSemanaQuery toListVendasPorDiaSemanaQuery(LocalDate inicio, LocalDate fim) {
        return new ListVendasPorDiaSemanaQuery(inicio, fim);
    }

    public static List<FeriadosDTO> toDTO(List<FeriadosResponseDTO> dto) {
        return dto.stream()
                .map(feriado -> new FeriadosDTO(
                        feriado.data(),
                        feriado.nome(),
                        feriado.tipo(),
                        feriado.descricao(),
                        feriado.uf(),
                        feriado.municipio()
                ))
                .toList();
    }

    public static List<FeriadosResponseDTO> toFeriadosResponse(List<FeriadosDTO> domain) {
        return domain.stream()
                .map(feriado -> new FeriadosResponseDTO(
                        feriado.data(),
                        feriado.nome(),
                        feriado.tipo(),
                        feriado.descricao(),
                        feriado.uf(),
                        feriado.municipio()
                ))
                .toList();
    }


    public static ProdutoRecomendadoFeriadoDTO toDTO(ProdutoRecomendadoFeriadoResponseDTO dto) {
        return new ProdutoRecomendadoFeriadoDTO(
                dto.dataFeriado(),
                dto.feriado(),
                dto.produto(),
                dto.categoria()
        );
    }

    public static List<ProdutoRecomendadoFeriadoResponseDTO> toResponse(List<ProdutoRecomendadoFeriadoDTO> domain) {
        return domain.stream()
                .map(produto -> new ProdutoRecomendadoFeriadoResponseDTO(
                        produto.dataFeriado(),
                        produto.feriado(),
                        produto.produto(),
                        produto.categoria()
                ))
                .toList();
    }

    public static ListProdutosRecomendadosFeriadosQuery listProdutosRecomendadosFeriadosQuery(List<FeriadosDTO> feriados) {
        return new ListProdutosRecomendadosFeriadosQuery(feriados);
    }

}