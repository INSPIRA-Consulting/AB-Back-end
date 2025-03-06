package school.sptech.Inspira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    @Autowired
    private IngredienteRepository repository;

    @PostMapping
    public ResponseEntity<Ingrediente> cadastrar(
            @RequestBody Ingrediente ingredienteParaCadastrar
    ) {

        ingredienteParaCadastrar.setId(null);
        Ingrediente ingredienteSalvo = repository.save(ingredienteParaCadastrar);

        return ResponseEntity.status(201).body(ingredienteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listar() {
        List<Ingrediente> listaIngredientes = repository.findAll();

        if (listaIngredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaIngredientes);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Ingrediente>> listarPorNome(
            @RequestParam String nome
    ) {
        List<Ingrediente> response = repository.findByNomeContainsIgnoreCase(nome);

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
        Optional<Ingrediente> ingredienteAchado = repository.findById(id);

        if (ingredienteAchado.isPresent()) {
            Ingrediente ingrediente = ingredienteAchado.get();
            ingrediente.setPreco(precoParaAtualizar);

            return ResponseEntity.status(200).body(repository.save(ingrediente));
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Ingrediente> atualizar (
            @PathVariable Integer id,
            @RequestBody Ingrediente ingredienteParaAtualizar
    ) {
        Optional<Ingrediente> ingredienteAchado = repository.findById(id);

        if (ingredienteAchado.isPresent()) {
            ingredienteParaAtualizar.setId(id);
            return ResponseEntity.status(200).body(repository.save(ingredienteParaAtualizar));
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar (
        @PathVariable Integer id
    ) {
        Optional<Ingrediente> ingredienteAchado = repository.findById(id);

        if (ingredienteAchado.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

}
