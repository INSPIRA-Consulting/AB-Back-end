package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.service.IngredienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @PostMapping
    public ResponseEntity<Ingrediente> cadastrarIngrediente(@RequestBody Ingrediente ingredienteParaCadastrar) {
        Ingrediente ingredienteCadastrado = ingredienteService.cadastrar(ingredienteParaCadastrar);

        return ResponseEntity.status(201).body(ingredienteCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.listar();

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientes);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Ingrediente>> listarPorNomeIngrediente(
            @RequestParam String nomeIngrediente
    ) {
        List<Ingrediente> ingredientesFiltrados = ingredienteService.listarPorNome(nomeIngrediente);

        if (ingredientesFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientesFiltrados);
    }

    @PutMapping("{idIngrediente}")
    public ResponseEntity<Ingrediente> atualizarIngrediente (
            @PathVariable Integer idIngrediente,
            @RequestBody Ingrediente ingredienteParaAtualizar
    ) {
        Ingrediente ingredienteAtualizado = ingredienteService.atualizar(idIngrediente, ingredienteParaAtualizar);

        return ResponseEntity.status(200).body(ingredienteAtualizado);
    }

    @DeleteMapping("/{idIngrediente}")
    public ResponseEntity<Void> excluirIngrediente(
        @PathVariable Integer idIngrediente
    ) {
        ingredienteService.excluir(idIngrediente);

        return ResponseEntity.status(204).build();
    }

}
