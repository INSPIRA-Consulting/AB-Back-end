package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.IngredienteCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteResponseDto;
import com.anjos_bolos.anjos_bolos_api.service.IngredienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @PostMapping
    public ResponseEntity<IngredienteResponseDto> cadastrarIngrediente(@RequestBody @Valid IngredienteCadastroDto ingredienteDto) {
        IngredienteResponseDto ingredienteCadastrado = ingredienteService.cadastrar(ingredienteDto);
        return ResponseEntity.status(201).body(ingredienteCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<IngredienteResponseDto>> listarIngredientes() {
        List<IngredienteResponseDto> ingredientes = ingredienteService.listar();
        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<IngredienteResponseDto>> listarPorNomeIngrediente(@RequestParam String nomeIngrediente) {
        List<IngredienteResponseDto> ingredientesFiltrados = ingredienteService.listarPorNome(nomeIngrediente);
        if (ingredientesFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientesFiltrados);
    }

    @PutMapping("/{idIngrediente}")
    public ResponseEntity<IngredienteResponseDto> atualizarIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestBody @Valid IngredienteAtualizacaoDto ingredienteParaAtualizar
    ) {
        IngredienteResponseDto ingredienteAtualizado = ingredienteService.atualizar(idIngrediente, ingredienteParaAtualizar);
        return ResponseEntity.status(200).body(ingredienteAtualizado);
    }

    @DeleteMapping("/{idIngrediente}")
    public ResponseEntity<Void> excluirIngrediente(@PathVariable Integer idIngrediente) {
        ingredienteService.excluir(idIngrediente);
        return ResponseEntity.status(204).build();
    }
}
