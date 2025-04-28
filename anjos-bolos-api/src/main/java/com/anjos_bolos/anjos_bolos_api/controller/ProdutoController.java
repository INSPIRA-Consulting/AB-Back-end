package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoResponseDto;
import com.anjos_bolos.anjos_bolos_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrarProduto(@RequestBody @Valid ProdutoCadastroDto produtoParaCadastrar) {
        ProdutoResponseDto produtoCadastrado = produtoService.cadastrar(produtoParaCadastrar);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarProdutos() {
        List<ProdutoResponseDto> produtos = produtoService.listar();

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<ProdutoResponseDto>> buscarPorNomeProduto(@RequestParam String nomeProduto) {
        List<ProdutoResponseDto> produtosFiltrados = produtoService.listarPorNome(nomeProduto);

        if (produtosFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtosFiltrados);
    }

    @PutMapping("{idProduto}")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestBody @Valid ProdutoAtualizacaoDto produtoParaAtualizar
    ) {
        ProdutoResponseDto produtoAtualizado = produtoService.atualizar(idProduto, produtoParaAtualizar);
        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("{idProduto}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Integer idProduto) {
        produtoService.excluir(idProduto);
        return ResponseEntity.status(204).build();
    }
}
