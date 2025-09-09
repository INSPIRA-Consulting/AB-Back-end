package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoCadastroDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoResponseDto;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cadastrar novo produto", description = "Cria e salva um novo produto no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Produto com nome duplicado")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrarProduto(@RequestBody @Valid ProdutoCadastroDto produtoParaCadastrar) {
        ProdutoResponseDto produtoCadastrado = produtoService.cadastrar(produtoParaCadastrar);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarProdutos() {
        List<ProdutoResponseDto> produtos = produtoService.listar();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Buscar produtos por nome", description = "Filtra produtos que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<ProdutoResponseDto>> buscarPorNomeProduto(
            @Parameter(description = "Nome parcial ou completo do produto a ser buscado")
            @RequestParam String nomeProduto
    ) {
        List<ProdutoResponseDto> produtosFiltrados = produtoService.listarPorNome(nomeProduto);
        if (produtosFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosFiltrados);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto com esse nome já existe")
    })
    @PutMapping("{idProduto}")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(
            @Parameter(description = "ID do produto a ser atualizado") @PathVariable Integer idProduto,
            @RequestBody @Valid ProdutoAtualizacaoDto produtoParaAtualizar
    ) {
        ProdutoResponseDto produtoAtualizado = produtoService.atualizar(idProduto, produtoParaAtualizar);
        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("{idProduto}")
    public ResponseEntity<Void> excluirProduto(
            @Parameter(description = "ID do produto a ser excluído") @PathVariable Integer idProduto
    ) {
        produtoService.excluir(idProduto);
        return ResponseEntity.status(204).build();
    }
}
