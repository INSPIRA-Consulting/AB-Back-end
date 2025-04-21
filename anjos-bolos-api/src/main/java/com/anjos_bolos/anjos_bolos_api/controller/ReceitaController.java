package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.entity.Receita;
import com.anjos_bolos.anjos_bolos_api.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @Operation(summary = "Cadastrar nova receita", description = "Cria e salva uma nova receita no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Conflito ao cadastrar receita")
    })
    @PostMapping
    public ResponseEntity<Receita> cadastrarReceita(@RequestBody Receita receitaParaCadastrar) {
        Receita receitaCadastrada = receitaService.cadastrar(receitaParaCadastrar);
        return ResponseEntity.status(201).body(receitaCadastrada);
    }

    @Operation(summary = "Listar todas as receitas", description = "Retorna uma lista com todas as receitas cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma receita encontrada")
    })
    @GetMapping
    public ResponseEntity<List<Receita>> listarReceitas() {
        List<Receita> receitas = receitaService.listar();
        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(receitas);
    }

    @Operation(summary = "Calcular custo da receita", description = "Calcula o custo de produção de uma receita e sugere um preço de venda com base no markup.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Custo calculado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto/Receita não encontrada")
    })
    @GetMapping("/calcular-preco/{idProduto}")
    public ResponseEntity<String> calcularPreco(
            @Parameter(description = "ID do produto associado à receita") @PathVariable Integer idProduto
    ) {
        Double valorCusto = receitaService.calcularPreco(idProduto);
        Double valorFinal = receitaService.buscarReceitaPorProduto(idProduto).getProduto().getValorFinal();

        return ResponseEntity.status(200).body(
                "O Custo de Produção da Receita de '' é de: R$ %.2f. Preço Sugerido: R$ %.2f. O Preço Atual desse Produto é de R$ %.2f"
                        .formatted(valorCusto, valorCusto * 1.40, valorFinal)
        );
    }

    @Operation(summary = "Excluir receita por produto", description = "Remove todas as receitas associadas a um produto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita(s) excluída(s) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou receita não encontrada")
    })
    @DeleteMapping("produto/{idProduto}")
    public ResponseEntity<Void> excluirReceita(
            @Parameter(description = "ID do produto para exclusão da(s) receita(s)") @PathVariable Integer idProduto
    ) {
        receitaService.excluir(idProduto);
        return ResponseEntity.status(204).build();
    }
}
