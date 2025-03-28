package com.anjos_bolos.anjos_bolos_api.controller;


import com.anjos_bolos.anjos_bolos_api.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produtoParaCadastrar) {
        Produto produtoCadastrado = produtoService.cadastrar(produtoParaCadastrar);

        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listar();

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Produto>> buscarPorNomeProduto(
            @RequestParam String nomeProduto
    ) {
        List<Produto> produtos = produtoService.listarPorNome(nomeProduto);

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
        Produto produtoAtualizado = produtoService.atualizar(idProduto, produtoParaAtualizar);

        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("{idProduto}")
    public ResponseEntity<Void> excluirProduto(
            @PathVariable Integer idProduto
    ) {
        produtoService.excluir(idProduto);

        return ResponseEntity.status(204).build();
    }

}
