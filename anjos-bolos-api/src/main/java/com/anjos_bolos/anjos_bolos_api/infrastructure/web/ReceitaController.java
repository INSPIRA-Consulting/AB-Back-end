package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ReceitaRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ReceitaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ReceitaEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Receitas", description = "Operações relacionadas à Entidade de Receitas")
@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final CreateReceitaUseCase createReceitaUseCase;
    private final UpdateReceitaUseCase updateReceitaUseCase;
    private final DeleteReceitaUseCase deleteReceitaUseCase;
    private final ListReceitasUseCase listReceitasUseCase;
    private final GetReceitaByIdUseCase getReceitaByIdUseCase;
    private final ListReceitasByNomeUseCase listReceitasByNomeUseCase;
    private final ListReceitasByIngredienteIdsUseCase listReceitasByIngredienteIdsUseCase;
    private final ListReceitasByTipoReceitaIdUseCase listReceitasByTipoReceitaIdUseCase;

    public ReceitaController(CreateReceitaUseCase createReceitaUseCase, UpdateReceitaUseCase updateReceitaUseCase, DeleteReceitaUseCase deleteReceitaUseCase, ListReceitasUseCase listReceitasUseCase, GetReceitaByIdUseCase getReceitaByIdUseCase, ListReceitasByNomeUseCase listReceitasByNomeUseCase, ListReceitasByIngredienteIdsUseCase listReceitasByIngredienteIdsUseCase, ListReceitasByTipoReceitaIdUseCase listReceitasByTipoReceitaIdUseCase) {
        this.createReceitaUseCase = createReceitaUseCase;
        this.updateReceitaUseCase = updateReceitaUseCase;
        this.deleteReceitaUseCase = deleteReceitaUseCase;
        this.listReceitasUseCase = listReceitasUseCase;
        this.getReceitaByIdUseCase = getReceitaByIdUseCase;
        this.listReceitasByNomeUseCase = listReceitasByNomeUseCase;
        this.listReceitasByIngredienteIdsUseCase = listReceitasByIngredienteIdsUseCase;
        this.listReceitasByTipoReceitaIdUseCase = listReceitasByTipoReceitaIdUseCase;
    }

    @Operation(summary = "Cadastrar nova Receita", description = "Cria e salva um nova Receita no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Receita já existe")
    })
    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> cadastrarReceita(@RequestBody @Valid ReceitaRequestDTO dto) {
        CreateReceitaCommand command = ReceitaEntityMapper.toCommand(dto);
        Receita receita = createReceitaUseCase.execute(command);

        return ResponseEntity.status(201).body(ReceitaEntityMapper.toDTO(receita));
    }

    @Operation(summary = "Listar todas as Receitas", description = "Retorna uma lista com todas as Receitas cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Receita encontrada")
    })
    @GetMapping
    public ResponseEntity<List<ReceitaResponseDTO>> listarReceitas() {
        ListReceitasQuery query = new ListReceitasQuery();
        List<Receita> receitas = listReceitasUseCase.execute(query);

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(receitas
                .stream()
                .map(ReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Receita por ID", description = "Busca uma Receita que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita encontrado"),
            @ApiResponse(responseCode = "204", description = "Receita não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> buscarPorIdReceita(@PathVariable Integer id) {
        GetReceitaByIdQuery query = ReceitaEntityMapper.toGetReceitaByIdQuery(id);
        Receita receita = getReceitaByIdUseCase.execute(query);

        if (receita == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ReceitaEntityMapper.toDTO(receita));
    }

    @Operation(summary = "Buscar Receitas por nome", description = "Filtra Receitas que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Receita encontrada")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<ReceitaResponseDTO>> listarPorNomeReceita(@RequestParam String nome) {
        ListReceitasByNomeQuery query = ReceitaEntityMapper.toListReceitasByNomeQuery(nome);
        List<Receita> receitas = listReceitasByNomeUseCase.execute(query);

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(receitas
                .stream()
                .map(ReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Receitas por Ingredientes", description = "Filtra Receitas que contenham os Ingredientes informados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Receita encontrada")
    })
    @GetMapping("/filtro-ingredientes")
    public ResponseEntity<List<ReceitaResponseDTO>> buscarPorIdCategoriaReceita(@RequestBody List<Integer> ingredienteIds) {
        ListReceitasByIngredienteIdsQuery query = ReceitaEntityMapper.toListReceitasByIngredienteIdsQuery(ingredienteIds);
        List<Receita> receitas = listReceitasByIngredienteIdsUseCase.execute(query);

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(receitas
                .stream()
                .map(ReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Receitas por Tipo de Receita", description = "Filtra Receitas que contenham o Tipo de Receita informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Receita encontrada")
    })
    @GetMapping("/tipo/{id}")
    public ResponseEntity<List<ReceitaResponseDTO>> listarPorIdTipoReceita(@PathVariable Integer id) {
        ListReceitasByTipoReceitaIdQuery query = ReceitaEntityMapper.toListReceitasByTipoReceitaIdQuery(id);
        List<Receita> receitas = listReceitasByTipoReceitaIdUseCase.execute(query);

        if (receitas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(receitas
                .stream()
                .map(ReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Receita", description = "Atualiza uma Receita existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "409", description = "Receita já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> atualizarReceita(
            @Parameter(description = "ID do Receita a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid ReceitaRequestDTO dto
    ) {
        UpdateReceitaCommand command = ReceitaEntityMapper.toCommand(id, dto);
        Receita receita = updateReceitaUseCase.execute(command);

        return ResponseEntity.status(200).body(ReceitaEntityMapper.toDTO(receita));
    }

    @Operation(summary = "Excluir Receita", description = "Remove uma Receita do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirReceita(
            @Parameter(description = "ID do Receita a ser excluído") @PathVariable Integer id
    ) {
        DeleteReceitaCommand command = ReceitaEntityMapper.toCommand(id);
        deleteReceitaUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}