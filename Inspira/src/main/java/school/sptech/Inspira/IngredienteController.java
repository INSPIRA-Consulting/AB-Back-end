package school.sptech.Inspira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Ingrediente> listar() {
        List<Ingrediente> listaIngredientes = repository.findAll();

        if (listaIngredientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        ResponseEntity.status(200).body(listaIngredientes);
    }


}
