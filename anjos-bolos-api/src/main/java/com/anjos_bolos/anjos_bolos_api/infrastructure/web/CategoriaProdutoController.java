package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.categoria_produto.CategoriaProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.categoria_produto.CategoriaProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.CategoriaProdutoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias-produtos")
public class CategoriaProdutoController {
    private final CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase;
    private final UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase;
    private final DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase;
    private final ListCategoriasProdutoUseCase listCategoriasProdutoUseCase;
    private final GetCategoriaProdutoByIdUseCase getCategoriaProdutoByIdUseCase;
    private final ListCategoriasProdutoByNomeUseCase listCategoriasProdutoByNomeUseCase;

    public CategoriaProdutoController(CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase, UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase, DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase, ListCategoriasProdutoUseCase listCategoriasProdutoUseCase, GetCategoriaProdutoByIdUseCase getCategoriaProdutoByIdUseCase, ListCategoriasProdutoByNomeUseCase listCategoriasProdutoByNomeUseCase) {
        this.createCategoriaProdutoUseCase = createCategoriaProdutoUseCase;
        this.updateCategoriaProdutoUseCase = updateCategoriaProdutoUseCase;
        this.deleteCategoriaProdutoUseCase = deleteCategoriaProdutoUseCase;
        this.listCategoriasProdutoUseCase = listCategoriasProdutoUseCase;
        this.getCategoriaProdutoByIdUseCase = getCategoriaProdutoByIdUseCase;
        this.listCategoriasProdutoByNomeUseCase = listCategoriasProdutoByNomeUseCase;
    }

    @Operation(summary = "Cadastrar nova Categoria de Produto", description = "Cria e salva uma nova Categoria de Produto no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria de Produto cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Categoria de Produto já existe")
    })
    @PostMapping
    public ResponseEntity<CategoriaProdutoResponseDTO> cadastrarCategoriaProduto(@RequestBody @Valid CategoriaProdutoRequestDTO dto) {
        CreateCategoriaProdutoCommand command = CategoriaProdutoEntityMapper.toCommand(dto);
        CategoriaProduto categoriaProduto = createCategoriaProdutoUseCase.execute(command);

        return ResponseEntity.status(201).body(CategoriaProdutoEntityMapper.toDTO(categoriaProduto));
    }

    @Operation(summary = "Listar todos as Categorias de Produto", description = "Retorna uma lista com todas as Categorias de Produtos cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias de Produto encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Categoria de Produto encontrada")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> listarCategoriasProduto() {
        ListCategoriasProdutoQuery query = new ListCategoriasProdutoQuery();
        List<CategoriaProduto> categoriasProduto = listCategoriasProdutoUseCase.execute(query);

        if (categoriasProduto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categoriasProduto
                .stream()
                .map(CategoriaProdutoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Categoria de Produto por ID", description = "Busca uma Categoria de Produto que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de Produto encontrada"),
            @ApiResponse(responseCode = "204", description = "Categoria de Produto não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> buscarPorIdCategoriaProduto(@PathVariable Integer id) {
        GetCategoriaProdutoByIdQuery query = new GetCategoriaProdutoByIdQuery(id);
        CategoriaProduto categoriaProduto = getCategoriaProdutoByIdUseCase.execute(query);

        if (categoriaProduto == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(CategoriaProdutoEntityMapper.toDTO(categoriaProduto));
    }

    @Operation(summary = "Buscar Categorias de Produto por Nome", description = "Filtra Categorias de Produto que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum ingrediente encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> listarPorNomeCategoriaProduto(@RequestParam String nome) {
        ListCategoriasProdutoByNomeQuery query = new ListCategoriasProdutoByNomeQuery(nome);
        List<CategoriaProduto> categoriasProduto = listCategoriasProdutoByNomeUseCase.execute(query);

        if (categoriasProduto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categoriasProduto
                .stream()
                .map(CategoriaProdutoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Categoria de Produto", description = "Atualiza uma Categoria de Produto existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de Produto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria de Produto não encontrada"),
            @ApiResponse(responseCode = "409", description = "Categoria de Produto com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> atualizarCategoriaProduto(
            @Parameter(description = "ID da Categoria de Produto a ser atualizada") @PathVariable Integer id,
            @RequestBody @Valid CategoriaProdutoRequestDTO dto
    ) {
        UpdateCategoriaProdutoCommand command = CategoriaProdutoEntityMapper.toCommand(id, dto);
        CategoriaProduto categoriaProduto = updateCategoriaProdutoUseCase.execute(command);

        return ResponseEntity.status(200).body(CategoriaProdutoEntityMapper.toDTO(categoriaProduto));
    }

    @Operation(summary = "Excluir Categoria de Produto", description = "Remove uma Categoria de Produto do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria de Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria de Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoriaProduto(
            @Parameter(description = "ID da Categoria de Produto a ser excluída") @PathVariable Integer id
    ) {
        DeleteCategoriaProdutoCommand command = new DeleteCategoriaProdutoCommand(id);
        deleteCategoriaProdutoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }
}
