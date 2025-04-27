package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @Operation(summary = "Cadastrar novo ingrediente", description = "Cria e salva um novo ingrediente no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Ingrediente já existe")
    })
    @PostMapping
    public ResponseEntity<Ingrediente> cadastrarIngrediente(@RequestBody Ingrediente ingredienteParaCadastrar) {
        Ingrediente ingredienteCadastrado = ingredienteService.cadastrar(ingredienteParaCadastrar);
        return ResponseEntity.status(201).body(ingredienteCadastrado);
    }

    @Operation(summary = "Listar todos os ingredientes", description = "Retorna uma lista com todos os ingredientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum ingrediente encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.listar();
        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @Operation(summary = "Buscar ingredientes por nome", description = "Filtra ingredientes que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum ingrediente encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Ingrediente>> listarPorNomeIngrediente(
            @Parameter(description = "Nome parcial ou completo do ingrediente a ser buscado")
            @RequestParam String nomeIngrediente
    ) {
        List<Ingrediente> ingredientesFiltrados = ingredienteService.listarPorNome(nomeIngrediente);
        if (ingredientesFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientesFiltrados);
    }

    @Operation(summary = "Atualizar ingrediente", description = "Atualiza um ingrediente existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Ingrediente com esse nome já existe")
    })
    @PutMapping("{idIngrediente}")
    public ResponseEntity<Ingrediente> atualizarIngrediente(
            @Parameter(description = "ID do ingrediente a ser atualizado") @PathVariable Integer idIngrediente,
            @RequestBody Ingrediente ingredienteParaAtualizar
    ) {
        Ingrediente ingredienteAtualizado = ingredienteService.atualizar(idIngrediente, ingredienteParaAtualizar);
        return ResponseEntity.status(200).body(ingredienteAtualizado);
    }

    @Operation(summary = "Excluir ingrediente", description = "Remove um ingrediente do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @DeleteMapping("/{idIngrediente}")
    public ResponseEntity<Void> excluirIngrediente(
            @Parameter(description = "ID do ingrediente a ser excluído") @PathVariable Integer idIngrediente
    ) {
        ingredienteService.excluir(idIngrediente);
        return ResponseEntity.status(204).build();
    }
}
