package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.IngredienteEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Ingredientes", description = "Operações relacionadas à Entidade de Ingredientes")
@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final CreateIngredienteUseCase createIngredienteUseCase;
    private final UpdateIngredienteUseCase updateIngredienteUseCase;
    private final DeleteIngredienteUseCase deleteIngredienteUseCase;
    private final ListIngredientesUseCase listIngredientesUseCase;
    private final ListIngredientesPageableUseCase listIngredientesPageableUseCase;
    private final GetIngredienteByIdUseCase getIngredienteByIdUseCase;
    private final ListIngredienteByNomeUseCase listIngredienteByNomeUseCase;

    public IngredienteController(
            CreateIngredienteUseCase createIngredienteUseCase,
            UpdateIngredienteUseCase updateIngredienteUseCase,
            DeleteIngredienteUseCase deleteIngredienteUseCase,
            ListIngredientesUseCase listIngredientesUseCase,
            ListIngredientesPageableUseCase listIngredientesPageableUseCase,
            GetIngredienteByIdUseCase getIngredienteByIdUseCase,
            ListIngredienteByNomeUseCase listIngredienteByNomeUseCase) {
        this.createIngredienteUseCase = createIngredienteUseCase;
        this.updateIngredienteUseCase = updateIngredienteUseCase;
        this.deleteIngredienteUseCase = deleteIngredienteUseCase;
        this.listIngredientesUseCase = listIngredientesUseCase;
        this.listIngredientesPageableUseCase = listIngredientesPageableUseCase;
        this.getIngredienteByIdUseCase = getIngredienteByIdUseCase;
        this.listIngredienteByNomeUseCase = listIngredienteByNomeUseCase;
    }

    @Operation(summary = "Cadastrar novo Ingrediente", description = "Cria e salva um novo Ingrediente no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Ingrediente já existe")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<IngredienteResponseDTO> cadastrarIngrediente(@RequestBody @Valid IngredienteRequestDTO dto) {
        CreateIngredienteCommand command = IngredienteEntityMapper.toCommand(dto);
        Ingrediente ingrediente = createIngredienteUseCase.execute(command);

        return ResponseEntity.status(201).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Listar todos os Ingredientes", description = "Retorna uma lista com todos os Ingredientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Ingrediente encontrado")
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Page<IngredienteResponseDTO>> listarIngredientes(Pageable paginacao) {
        ListIngredientesPageableQuery query = new ListIngredientesPageableQuery(paginacao);
        Page<Ingrediente> ingredientes = listIngredientesPageableUseCase.execute(query);

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes
                .map(IngredienteEntityMapper::toDTO));
    }

    @Operation(summary = "Buscar Ingrediente por ID", description = "Busca um Ingrediente que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado"),
            @ApiResponse(responseCode = "204", description = "Ingrediente não encontrado")
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<IngredienteResponseDTO> buscarPorIdIngrediente(@PathVariable Integer id) {
        GetIngredienteByIdQuery query = IngredienteEntityMapper.toGetIngredienteByIdQuery(id);
        Ingrediente ingrediente = getIngredienteByIdUseCase.execute(query);

        if (ingrediente == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Buscar Ingredientes por nome", description = "Filtra Ingredientes que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Ingrediente encontrado")
    })
    @GetMapping("/filtro-nome")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<IngredienteResponseDTO>> listarPorNomeIngrediente(@RequestParam String nome) {
        ListIngredientesByNomeQuery query = IngredienteEntityMapper.toListIngredientesByNomeQuery(nome);
        List<Ingrediente> ingredientes = listIngredienteByNomeUseCase.execute(query);

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientes
                .stream()
                .map(IngredienteEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Ingrediente", description = "Atualiza um Ingrediente existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Ingrediente com esse nome já existe")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<IngredienteResponseDTO> atualizarIngrediente(
            @Parameter(description = "ID do Ingrediente a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid IngredienteRequestDTO dto
    ) {
        UpdateIngredienteCommand command = IngredienteEntityMapper.toCommand(id, dto);
        Ingrediente ingrediente = updateIngredienteUseCase.execute(command);

        return ResponseEntity.status(200).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Excluir Ingrediente", description = "Remove um Ingrediente do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> excluirIngrediente(
            @Parameter(description = "ID do Ingrediente a ser excluído") @PathVariable Integer id
    ) {
        DeleteIngredienteCommand command = IngredienteEntityMapper.toCommand(id);
        deleteIngredienteUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}