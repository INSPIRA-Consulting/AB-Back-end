package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ComposicaoProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ComposicaoProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ComposicaoProdutoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Composições de Produto", description = "Operações relacionadas à Entidade de Composições de Produto")
@RestController
@RequestMapping("/composicoes-produto")
public class ComposicaoProdutoController {

    private final CreateComposicaoProdutoUseCase createComposicaoProdutoUseCase;
    private final UpdateComposicaoProdutoUseCase updateComposicaoProdutoUseCase;
    private final DeleteComposicaoProdutoUseCase deleteComposicaoProdutoUseCase;
    private final ListComposicoesProdutoByProdutoIdUseCase listComposicoesProdutoByProdutoIdUseCase;

    public ComposicaoProdutoController(CreateComposicaoProdutoUseCase createComposicaoProdutoUseCase, UpdateComposicaoProdutoUseCase updateComposicaoProdutoUseCase, DeleteComposicaoProdutoUseCase deleteComposicaoProdutoUseCase, ListComposicoesProdutoByProdutoIdUseCase listComposicoesProdutoByProdutoIdUseCase) {
        this.createComposicaoProdutoUseCase = createComposicaoProdutoUseCase;
        this.updateComposicaoProdutoUseCase = updateComposicaoProdutoUseCase;
        this.deleteComposicaoProdutoUseCase = deleteComposicaoProdutoUseCase;
        this.listComposicoesProdutoByProdutoIdUseCase = listComposicoesProdutoByProdutoIdUseCase;
    }

    @Operation(summary = "Cadastrar nova Composição de Produto", description = "Cria e salva uma nova Composição de Produto no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Composição de Produto cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Composição de Produto já existe")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ComposicaoProdutoResponseDTO> cadastrarComposicaoProduto(@RequestBody @Valid ComposicaoProdutoRequestDTO dto) {
        CreateComposicaoProdutoCommand command = ComposicaoProdutoEntityMapper.toCommand(dto);

        ComposicaoProduto composicaoProduto = createComposicaoProdutoUseCase.execute(command);

        return ResponseEntity.status(201).body(ComposicaoProdutoEntityMapper.toDTO(composicaoProduto));
    }

    @Operation(summary = "Buscar Composições de Produto por ID", description = "Busca as Composições de Produto que contenha o ID de Produto informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Composições de Produto encontradas"),
            @ApiResponse(responseCode = "204", description = "Composições de Produto não encontradas")
    })
    @GetMapping("/{produtoId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ComposicaoProdutoResponseDTO> buscarPorProdutoId(@PathVariable Integer produtoId) {
        ListComposicoesProdutoByProdutoIdQuery query = ComposicaoProdutoEntityMapper.toListComposicoesProdutoByProdutoIdQuery(produtoId);
        ComposicaoProduto composicaoProduto = listComposicoesProdutoByProdutoIdUseCase.execute(query);

        if (composicaoProduto == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ComposicaoProdutoEntityMapper.toDTO(composicaoProduto));
    }

    @Operation(summary = "Atualizar Composição de Produto", description = "Atualiza uma Composição de Produto existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Composição de Produto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Composição de Produto não encontrada"),
            @ApiResponse(responseCode = "409", description = "Composição de Produto já existe")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ComposicaoProdutoResponseDTO> atualizarComposicaoProduto(
            @Parameter(description = "ID do Produto a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid ComposicaoProdutoRequestDTO dto
    ) {
        UpdateComposicaoProdutoCommand command = ComposicaoProdutoEntityMapper.toCommand(id, dto);
        ComposicaoProduto composicaoProduto = updateComposicaoProdutoUseCase.execute(command);

        return ResponseEntity.status(200).body(ComposicaoProdutoEntityMapper.toDTO(composicaoProduto));
    }

    @Operation(summary = "Excluir Composição de Produto", description = "Remove uma Composição de Produto do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Composição de Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Composição de Produto não encontrada")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> excluirComposicaoProduto(
            @Parameter(description = "ID do Composição de Produto a ser excluído") @PathVariable Integer id
    ) {
        DeleteComposicaoProdutoCommand command = ComposicaoProdutoEntityMapper.toCommand(id);
        deleteComposicaoProdutoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}