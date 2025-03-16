package com.anjos_bolos.anjos_bolos_api.controller;

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
    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;


    @PostMapping
    public ResponseEntity<Receita> cadastrarReceita(@RequestBody Receita receitaParaCadastrar) {
        Optional<Produto> produto = produtoRepository.findById(receitaParaCadastrar.getProduto().getIdProduto());
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(receitaParaCadastrar.getIngrediente().getIdIngrediente());

        if (produto.isEmpty() || ingrediente.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        receitaParaCadastrar.setProduto(produto.get());
        receitaParaCadastrar.setIngrediente(ingrediente.get());
        receitaParaCadastrar.setIdReceita(null);
        Receita receitaCadastrada = receitaRepository.save(receitaParaCadastrar);

        return ResponseEntity.status(201).body(receitaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Receita>> listarReceitas() {
        List<Receita> receitas = receitaRepository.findAll();

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(receitas);
    }

    @GetMapping("/calcular-preco")
    public ResponseEntity<String> calcularPreco() {
        List<Receita> receitas = receitaRepository.findAll();
        Double valorCusto = .0;

        for (Receita receita : receitas) {
            Double precoIngrediente = receita.getIngrediente().getPreco();
            Double quantidadeIngrediente = receita.getQuantidade();

            valorCusto += precoIngrediente * quantidadeIngrediente;
        }

        return ResponseEntity.status(200).body("O valor de custo de produção desse produto é de R$ %.2f, preço sugerido R$ %.2f"
                .formatted(valorCusto, valorCusto * 1.40));
    }

    @DeleteMapping("{idReceita}")
    public ResponseEntity<Void> excluirReceita(
            @PathVariable Integer idReceita
    ) {
        Boolean receitaExistePorId = receitaRepository.existsById(idReceita);

        if (!receitaExistePorId) {
            return ResponseEntity.status(404).build();
        }

        receitaRepository.deleteById(idReceita);

        return ResponseEntity.status(204).build();
    }
}
