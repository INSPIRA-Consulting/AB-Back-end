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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "http://localhost:5173")
public class IngredienteController {
    private final CreateIngredienteUseCase createIngredienteUseCase;
    private final UpdateIngredienteUseCase updateIngredienteUseCase;
    private final DeleteIngredienteUseCase deleteIngredienteUseCase;
    private final ListIngredientesUseCase listIngredientesUseCase;
    private final GetIngredienteByIdUseCase getIngredienteByIdUseCase;
    private final ListIngredienteByNomeUseCase listIngredienteByNomeUseCase;

    public IngredienteController(
            CreateIngredienteUseCase createIngredienteUseCase,
            UpdateIngredienteUseCase updateIngredienteUseCase,
            DeleteIngredienteUseCase deleteIngredienteUseCase,
            ListIngredientesUseCase listIngredientesUseCase,
            GetIngredienteByIdUseCase getIngredienteByIdUseCase,
            ListIngredienteByNomeUseCase listIngredienteByNomeUseCase) {
        this.createIngredienteUseCase = createIngredienteUseCase;
        this.updateIngredienteUseCase = updateIngredienteUseCase;
        this.deleteIngredienteUseCase = deleteIngredienteUseCase;
        this.listIngredientesUseCase = listIngredientesUseCase;
        this.getIngredienteByIdUseCase = getIngredienteByIdUseCase;
        this.listIngredienteByNomeUseCase = listIngredienteByNomeUseCase;
    }

    @Operation(summary = "Cadastrar novo ingrediente", description = "Cria e salva um novo ingrediente no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Ingrediente já existe")
    })
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> cadastrarIngrediente(@RequestBody @Valid IngredienteRequestDTO dto) {
        CreateIngredienteCommand command = IngredienteEntityMapper.toCommand(dto);
        Ingrediente ingrediente = createIngredienteUseCase.execute(command);

        return ResponseEntity.status(201).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Listar todos os ingredientes", description = "Retorna uma lista com todos os ingredientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum ingrediente encontrado")
    })
    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listarIngredientes() {
        ListIngredientesQuery query = new ListIngredientesQuery();
        List<Ingrediente> ingredientes = listIngredientesUseCase.execute(query);

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes
                .stream()
                .map(IngredienteEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar ingredientes por ID", description = "Busca um ingrediente que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado"),
            @ApiResponse(responseCode = "204", description = "Ingrediente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> buscarPorIdIngrediente(@PathVariable Integer id) {
        GetIngredienteByIdQuery query = new GetIngredienteByIdQuery(id);
        Ingrediente ingrediente = getIngredienteByIdUseCase.execute(query);

        if (ingrediente == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Buscar ingredientes por nome", description = "Filtra ingredientes que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum ingrediente encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<IngredienteResponseDTO>> listarPorNomeIngrediente(@RequestParam String nome) {
        ListIngredientesByNomeQuery query = new ListIngredientesByNomeQuery(nome);
        List<Ingrediente> ingredientes = listIngredienteByNomeUseCase.execute(query);

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientes
                .stream()
                .map(IngredienteEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar ingrediente", description = "Atualiza um ingrediente existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Ingrediente com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> atualizarIngrediente(
            @Parameter(description = "ID do ingrediente a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid IngredienteRequestDTO dto
    ) {
        UpdateIngredienteCommand command = IngredienteEntityMapper.toCommand(id, dto);
        Ingrediente ingrediente = updateIngredienteUseCase.execute(command);

        return ResponseEntity.status(200).body(IngredienteEntityMapper.toDTO(ingrediente));
    }

    @Operation(summary = "Excluir ingrediente", description = "Remove um ingrediente do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirIngrediente(
            @Parameter(description = "ID do ingrediente a ser excluído") @PathVariable Integer id
    ) {
        DeleteIngredienteCommand command = new DeleteIngredienteCommand(id);
        deleteIngredienteUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }
}
