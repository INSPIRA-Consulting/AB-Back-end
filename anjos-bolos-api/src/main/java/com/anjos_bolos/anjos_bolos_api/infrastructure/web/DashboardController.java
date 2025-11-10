package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.PedidosFaturamentoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.ProdutoVendidoDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.config.aws.s3.S3Adapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.MargemLucroProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.PedidosFaturamentoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.ProdutoVendidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados.FeriadosResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.DashboardMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Dashboard", description = "Operações relacionadas à Dashboard")
@RestController
@RequestMapping("/dashboards")
public class DashboardController {

    private final GetMenorMargemLucroUseCase getMenorMargemLucroUseCase;
    private final GetMaiorMargemLucroUseCase getMaiorMargemLucroUseCase;
    private final ListProdutosMaisVendidosUseCase listProdutosMaisVendidosUseCase;
    private final GetPedidosFaturamentoUseCase getPedidosFaturamentoUseCase;
    private final GetProdutoMaisVendidoUseCase getProdutoMaisVendidoUseCase;
    private final GetDiaSemanaComMaisVendasUseCase getDiaSemanaComMaisVendasUseCase;

    private final S3Adapter s3Adapter;

    public DashboardController(GetMenorMargemLucroUseCase getMenorMargemLucroUseCase, GetMaiorMargemLucroUseCase getMaiorMargemLucroUseCase, ListProdutosMaisVendidosUseCase listProdutosMaisVendidosUseCase, GetPedidosFaturamentoUseCase getPedidosFaturamentoUseCase, GetProdutoMaisVendidoUseCase getProdutoMaisVendidoUseCase, GetDiaSemanaComMaisVendasUseCase getDiaSemanaComMaisVendasUseCase, S3Adapter s3Adapter) {
        this.getMenorMargemLucroUseCase = getMenorMargemLucroUseCase;
        this.getMaiorMargemLucroUseCase = getMaiorMargemLucroUseCase;
        this.listProdutosMaisVendidosUseCase = listProdutosMaisVendidosUseCase;
        this.getPedidosFaturamentoUseCase = getPedidosFaturamentoUseCase;
        this.getProdutoMaisVendidoUseCase = getProdutoMaisVendidoUseCase;
        this.getDiaSemanaComMaisVendasUseCase = getDiaSemanaComMaisVendasUseCase;
        this.s3Adapter = s3Adapter;
    }

    @Operation(summary = "Buscar Produto com Menor Margem de Lucro (%)", description = "Busca o Produto com Menor Margem de Lucro (%) no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/menor-margem-lucro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<MargemLucroProdutoResponseDTO> buscarProdutoMenorMargemLucro() {
        GetMenorMargemLucroQuery query = DashboardMapper.toGetMenorMargemLucroQuery(1);
        MargemLucroProdutoDTO menorMargemLucro = getMenorMargemLucroUseCase.execute(query);

        return ResponseEntity.status(200).body(DashboardMapper.toResponse(menorMargemLucro));
    }

    @Operation(summary = "Buscar Produto com Maior Margem de Lucro (%)", description = "Busca o Produto com Maior Margem de Lucro (%) no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/maior-margem-lucro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<MargemLucroProdutoResponseDTO> buscarProdutoMaiorMargemLucro() {
        GetMaiorMargemLucroQuery query = DashboardMapper.toGetMaiorMargemLucroQuery(1);
        MargemLucroProdutoDTO maiorMargemLucro = getMaiorMargemLucroUseCase.execute(query);

        return ResponseEntity.status(200).body(DashboardMapper.toResponse(maiorMargemLucro));
    }

    @Operation(summary = "Buscar Produto Mais Vendidos no Período", description = "Busca os TOP 5 Produtos mais Vendidos no Período informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/produtos-mais-vendidos")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ProdutoVendidoResponseDTO>> listProdutosMaisVendidos(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
                                                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
        ListProdutosMaisVendidosQuery query = DashboardMapper.toListProdutosMaisVendidosQuery(inicio, fim, 5);
        List<ProdutoVendidoDTO> produtosVendidos = listProdutosMaisVendidosUseCase.execute(query);

        return ResponseEntity.status(200).body(DashboardMapper.toResponse(produtosVendidos));
    }

    @Operation(summary = "Buscar Informações de Pedidos no Período", description = "Busca Informações de Faturamento e Custos dos Pedidos no Período informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/pedidos-faturamento")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidosFaturamentoResponseDTO> buscarFaturamentoPedidos(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
                                                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
        GetPedidosFaturamentoQuery query = DashboardMapper.toGetPedidosFaturamentoQuery(inicio, fim);
        PedidosFaturamentoDTO faturamento = getPedidosFaturamentoUseCase.execute(query);

        return ResponseEntity.status(200).body(DashboardMapper.toResponse(faturamento));
    }

    @Operation(summary = "Buscar Produto mais Vendido no Período", description = "Busca o Produto mais Vendido no período informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/produto-mais-vendido")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> buscarProdutoMaisVendido(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
        GetProdutoMaisVendidoQuery query = DashboardMapper.toGetProdutoMaisVendidoQuery(inicio, fim, 1);
        String produtoMaisVendido = getProdutoMaisVendidoUseCase.execute(query);

        return ResponseEntity.status(200).body(produtoMaisVendido);
    }

    @Operation(summary = "Buscar Dia da Semana com maior número de Vendas no Período", description = "Busca o Dia da Semana com maior número de Vendas no período informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/dia-semana-mais-vendas")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> buscarDiaSemanaComMaisVendas(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
        GetDiaSemanaComMaisVendasQuery query = DashboardMapper.toGetDiaSemanaComMaisVendasQuery(inicio, fim, 1);
        String diaSemana = getDiaSemanaComMaisVendasUseCase.execute(query);

        return ResponseEntity.status(200).body(diaSemana);
    }

    @Operation(summary = "Buscar Feriados", description = "Busca os Feriados Nacionais armazenados no S3.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feriados encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum Feriado encontrado")
    })
    @GetMapping("/feriados-nacionais")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<FeriadosResponseDTO>> buscarFeriadosNacionais(@RequestParam Integer ano) {
        List<FeriadosResponseDTO> feriados = s3Adapter.listFeriadosNacionais(ano);

        return ResponseEntity.status(200).body(feriados);
    }

}