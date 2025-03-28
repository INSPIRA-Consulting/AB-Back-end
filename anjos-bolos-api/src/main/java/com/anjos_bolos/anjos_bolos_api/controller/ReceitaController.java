package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.entity.ReceitaPrimaryKey;
import com.anjos_bolos.anjos_bolos_api.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.entity.Receita;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;
import com.anjos_bolos.anjos_bolos_api.repository.ProdutoRepository;
import com.anjos_bolos.anjos_bolos_api.repository.ReceitaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    public ResponseEntity<Receita> cadastrarReceita(@RequestBody Receita receitaParaCadastrar) {

        Receita receitaCadastrada = receitaService.cadastrar(receitaParaCadastrar);

        return ResponseEntity.status(201).body(receitaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Receita>> listarReceitas() {
        List<Receita> receitas = receitaService.listar();

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(receitas);
    }

    @GetMapping("/calcular-preco/{idProduto}")
    public ResponseEntity<String> calcularPreco(
            @PathVariable Integer idProduto
    ) {
        Double valorCusto = receitaService.calcularPreco(idProduto);
        Double valorFinal = receitaService.buscarReceitaPorProduto(idProduto).getProduto().getValorFinal();

        return ResponseEntity.status(200).body("O Custo de Produção da Receita de '' é de: R$ %.2f. Preço Sugerido: R$ %.2f. O Preço Atual desse Produto é de R$ %.2f"
                .formatted(valorCusto, valorCusto * 1.40, valorFinal));
    }

    @DeleteMapping("{idProduto}/{idIngrediente}")
    public ResponseEntity<Void> excluirReceita(
            @PathVariable Integer idProduto, @PathVariable Integer idIngrediente
    ) {
        receitaService.excluir(idProduto, idIngrediente);

        return ResponseEntity.status(204).build();
    }
}
