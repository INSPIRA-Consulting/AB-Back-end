package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.tipo_receita.TipoReceitaRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.tipo_receita.TipoReceitaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.TipoReceitaEntityMapper;
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

@Tag(name = "Tipos de Receita", description = "Operações relacionadas à Entidade de Tipos de Receita")
@RestController
@RequestMapping("/tipos-receitas")
public class TipoReceitaController {

    private final CreateTipoReceitaUseCase createTipoReceitaUseCase;
    private final UpdateTipoReceitaUseCase updateTipoReceitaUseCase;
    private final DeleteTipoReceitaUseCase deleteTipoReceitaUseCase;
    private final ListTiposReceitaUseCase listTiposReceitaUseCase;
    private final GetTipoReceitaByIdUseCase getTipoReceitaByIdUseCase;
    private final ListTiposReceitaByNomeUseCase listTiposReceitaByNomeUseCase;

    public TipoReceitaController(CreateTipoReceitaUseCase createTipoReceitaUseCase, UpdateTipoReceitaUseCase updateTipoReceitaUseCase, DeleteTipoReceitaUseCase deleteTipoReceitaUseCase, ListTiposReceitaUseCase listTiposReceitaUseCase, GetTipoReceitaByIdUseCase getTipoReceitaByIdUseCase, ListTiposReceitaByNomeUseCase listTiposReceitaByNomeUseCase) {
        this.createTipoReceitaUseCase = createTipoReceitaUseCase;
        this.updateTipoReceitaUseCase = updateTipoReceitaUseCase;
        this.deleteTipoReceitaUseCase = deleteTipoReceitaUseCase;
        this.listTiposReceitaUseCase = listTiposReceitaUseCase;
        this.getTipoReceitaByIdUseCase = getTipoReceitaByIdUseCase;
        this.listTiposReceitaByNomeUseCase = listTiposReceitaByNomeUseCase;
    }

    @Operation(summary = "Cadastrar novo Tipo de Receita", description = "Cria e salva um novo Tipo de Receita no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de Receita cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Tipo de Receita já existe")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<TipoReceitaResponseDTO> cadastrarTipoReceita(@RequestBody @Valid TipoReceitaRequestDTO dto) {
        CreateTipoReceitaCommand command = TipoReceitaEntityMapper.toCommand(dto);
        TipoReceita tipoReceita = createTipoReceitaUseCase.execute(command);

        return ResponseEntity.status(201).body(TipoReceitaEntityMapper.toDTO(tipoReceita));
    }

    @Operation(summary = "Listar todos as Categorias de Produto", description = "Retorna uma lista com todas as Categorias de Produtos cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de Receita encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhuma Tipo de Receita encontrado")
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TipoReceitaResponseDTO>> listarTiposReceita() {
        ListTiposReceitaQuery query = new ListTiposReceitaQuery();
        List<TipoReceita> tiposReceita = listTiposReceitaUseCase.execute(query);

        if (tiposReceita.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(tiposReceita
                .stream()
                .map(TipoReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Tipo de Receita por ID", description = "Busca um Tipo de Receita que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de Receita encontrado"),
            @ApiResponse(responseCode = "204", description = "Tipo de Receita não encontrado")
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<TipoReceitaResponseDTO> buscarPorIdTipoReceita(@PathVariable Integer id) {
        GetTipoReceitaByIdQuery query = new GetTipoReceitaByIdQuery(id);
        TipoReceita tipoReceita = getTipoReceitaByIdUseCase.execute(query);

        if (tipoReceita == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(TipoReceitaEntityMapper.toDTO(tipoReceita));
    }

    @Operation(summary = "Buscar Tipos de Receita por Nome", description = "Filtra Tipos de Receita que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de Receita encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Tipo de Receita encontrado")
    })
    @GetMapping("/filtro-nome")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TipoReceitaResponseDTO>> listarPorNomeTipoReceita(@RequestParam String nome) {
        ListTiposReceitaByNomeQuery query = new ListTiposReceitaByNomeQuery(nome);
        List<TipoReceita> tiposReceita = listTiposReceitaByNomeUseCase.execute(query);

        if (tiposReceita.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(tiposReceita
                .stream()
                .map(TipoReceitaEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Tipo de Receita", description = "Atualiza um Tipo de Receita existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de Receita atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de Receita não encontrado"),
            @ApiResponse(responseCode = "409", description = "Tipo de Receita com esse nome já existe")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<TipoReceitaResponseDTO> atualizarTipoReceita(
            @Parameter(description = "ID do Tipo de Receita a ser atualizada") @PathVariable Integer id,
            @RequestBody @Valid TipoReceitaRequestDTO dto
    ) {
        UpdateTipoReceitaCommand command = TipoReceitaEntityMapper.toCommand(id, dto);
        TipoReceita tipoReceita = updateTipoReceitaUseCase.execute(command);

        return ResponseEntity.status(200).body(TipoReceitaEntityMapper.toDTO(tipoReceita));
    }

    @Operation(summary = "Excluir Tipo de Receita", description = "Remove um Tipo de Receita do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de Receita excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de Receita não encontrado")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> excluirTipoReceita(
            @Parameter(description = "ID do Tipo de Receita a ser excluída") @PathVariable Integer id
    ) {
        DeleteTipoReceitaCommand command = new DeleteTipoReceitaCommand(id);
        deleteTipoReceitaUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}