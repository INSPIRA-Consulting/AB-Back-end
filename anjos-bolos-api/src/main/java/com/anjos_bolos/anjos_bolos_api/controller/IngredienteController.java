package com.anjos_bolos.anjos_bolos_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    @Autowired
    private IngredienteRepository repositoryIngrediente;

    @PostMapping
    public ResponseEntity<Ingrediente> cadastrarIngrediente(@RequestBody Ingrediente ingredienteParaCadastrar) {
        Boolean ingredienteExistePorNome = repositoryIngrediente.existsByNome(ingredienteParaCadastrar.getNome());

        if (ingredienteExistePorNome) {
            return ResponseEntity.status(409).build();
        }

        Ingrediente ingredienteCadastrado = repositoryIngrediente.save(ingredienteParaCadastrar);

        return ResponseEntity.status(201).body(ingredienteCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        List<Ingrediente> ingredientes = repositoryIngrediente.findAll();

        if (ingredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientes);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Ingrediente>> listarPorNomeIngrediente(
            @RequestParam String nomeIngrediente
    ) {
        List<Ingrediente> ingredientesFiltrados = repositoryIngrediente.findByNomeContainsIgnoreCase(nomeIngrediente);

        if (ingredientesFiltrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ingredientesFiltrados);
    }

    @PatchMapping("{idIngrediente}")
    public ResponseEntity<Ingrediente> atualizarPrecoIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestParam Double precoParaAtualizar
    ) {
        Optional<Ingrediente> optIngrediente = repositoryIngrediente.findById(idIngrediente);

        if (optIngrediente.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Ingrediente ingredienteAtualizado = optIngrediente.get();
        ingredienteAtualizado.setPreco(precoParaAtualizar);

        return ResponseEntity.status(200).body(repositoryIngrediente.save(ingredienteAtualizado));
    }

    @PutMapping("{idIngrediente}")
    public ResponseEntity<Ingrediente> atualizarIngrediente (
            @PathVariable Integer idIngrediente,
            @RequestBody Ingrediente ingredienteParaAtualizar
    ) {
        Optional<Ingrediente> optIngrediente = repositoryIngrediente.findById(idIngrediente);

        if (optIngrediente.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Boolean ingredienteExistePorNome = repositoryIngrediente.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot(
                ingredienteParaAtualizar.getNome(), idIngrediente
        );

        if (ingredienteExistePorNome) {
            return ResponseEntity.status(409).build();
        }

        ingredienteParaAtualizar.setIdIngrediente(idIngrediente);

        return ResponseEntity.status(200).body(repositoryIngrediente.save(ingredienteParaAtualizar));
    }

    @DeleteMapping("/{idIngrediente}")
    public ResponseEntity<Void> excluirIngrediente(
        @PathVariable Integer idIngrediente
    ) {
        Boolean produtoExistePorId = repositoryIngrediente.existsById(idIngrediente);

        if (!produtoExistePorId) {
            return ResponseEntity.status(404).build();
        }

        repositoryIngrediente.deleteById(idIngrediente);

        return ResponseEntity.status(204).build();
    }

}
