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
    private ProdutoRepository repositoryProduto;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produtoParaCadastrar) {
        Boolean produtoExistePorNome = repositoryProduto.existsByNomeIgnoreCase(produtoParaCadastrar.getNome());

        if (produtoExistePorNome) {
            return ResponseEntity.status(409).build();
        }

        produtoParaCadastrar.setIdProduto(null);
        Produto produtoCadastrado = repositoryProduto.save(produtoParaCadastrar);

        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = repositoryProduto.findAll();

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Produto>> buscarPorNomeProduto(
            @RequestParam String nomeProduto
    ) {
        List<Produto> produtos = repositoryProduto.findByNomeContainsIgnoreCase(nomeProduto);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @PutMapping("{idProduto}")
    public ResponseEntity<Produto> atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestBody Produto produtoParaAtualizar
    ) {
        Boolean produtoExistePorId = repositoryProduto.existsById(idProduto);

        if (!produtoExistePorId) {
            return ResponseEntity.status(404).build();
        }

        Boolean produtoExistePorNome = repositoryProduto.existsByNomeEqualsIgnoreCaseAndIdProdutoNot(
                produtoParaAtualizar.getNome(), idProduto
        );

        if (produtoExistePorNome) {
            return ResponseEntity.status(409).build();
        }

        produtoParaAtualizar.setIdProduto(idProduto);
        Produto produtoAtualizado = repositoryProduto.save(produtoParaAtualizar);

        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("{idProduto}")
    public ResponseEntity<Void> excluirProduto(
            @PathVariable Integer idProduto
    ) {
        Boolean produtoExistePorId = repositoryProduto.existsById(idProduto);

        if (!produtoExistePorId) {
            return ResponseEntity.status(404).build();
        }

        repositoryProduto.deleteById(idProduto);

        return ResponseEntity.status(204).build();
    }

}
