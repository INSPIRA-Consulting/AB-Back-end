package com.anjos_bolos.anjos_bolos_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.repository.ProdutoRepository;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produtoParaCadastrar) {
        if (!produtoRepository.existsByNomeIgnoreCase(produtoParaCadastrar.getNome())) {
            produtoParaCadastrar.setProdutoId(null);
            Produto usuarioCadasrado = produtoRepository.save(produtoParaCadastrar);
            return ResponseEntity.status(201).body(usuarioCadasrado);
        }

        return ResponseEntity.status(409).build();
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Produto>> buscarPorNome(
            @RequestParam String nome
    ) {
        List<Produto> produtos = produtoRepository.findByNomeContainsIgnoreCase(nome);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @PutMapping("{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Integer id,
            @RequestBody Produto produtoParaAtualizar
    ) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        if (produtoRepository.existsByNomeIgnoreCaseAndProdutoIdNot(produtoParaAtualizar.getNome(), id)
        ) {
            return ResponseEntity.status(409).build();
        }

        produtoParaAtualizar.setProdutoId(id);
        Produto produtoAtualizado = produtoRepository.save(produtoParaAtualizar);
        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id
    ) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

}
