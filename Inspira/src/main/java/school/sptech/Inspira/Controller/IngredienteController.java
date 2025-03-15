package school.sptech.Inspira.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Inspira.Entity.Ingrediente;
import school.sptech.Inspira.Repository.IngredienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @PostMapping
    public ResponseEntity<Ingrediente> cadastrar(@RequestBody Ingrediente ingrediente) {
        if (ingredienteRepository.existsByNome(ingrediente.getNome())) {
            return ResponseEntity.status(409).build();
        }

        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);
        return ResponseEntity.status(201).body(ingredienteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listar() {
        List<Ingrediente> listaIngredientes = ingredienteRepository.findAll();

        if (listaIngredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaIngredientes);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Ingrediente>> listarPorNome(
            @RequestParam String nome
    ) {
        List<Ingrediente> response = ingredienteRepository.findByNomeContainsIgnoreCase(nome);

        if (response.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ingrediente> atualizarPrecoIngrediente(
            @PathVariable Integer id,
            @RequestBody Double precoParaAtualizar
    ) {
        Optional<Ingrediente> ingredienteAchado = ingredienteRepository.findById(id);

        if (ingredienteAchado.isPresent()) {
            Ingrediente ingrediente = ingredienteAchado.get();
            ingrediente.setPreco(precoParaAtualizar);

            return ResponseEntity.status(200).body(ingredienteRepository.save(ingrediente));
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Ingrediente> atualizar (
            @PathVariable Integer id,
            @RequestBody Ingrediente ingredienteParaAtualizar
    ) {
        Optional<Ingrediente> ingredienteAchado = ingredienteRepository.findById(id);

        if (ingredienteAchado.isPresent()) {
            ingredienteParaAtualizar.setIngrediente_id(id);
            return ResponseEntity.status(200).body(ingredienteRepository.save(ingredienteParaAtualizar));
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar (
        @PathVariable Integer id
    ) {
        Optional<Ingrediente> ingredienteAchado = ingredienteRepository.findById(id);

        if (ingredienteAchado.isPresent()) {
            ingredienteRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

}
