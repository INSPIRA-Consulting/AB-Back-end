package school.sptech.Inspira;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produtoParaCadastrar) {
        produtoParaCadastrar.setId(null);

        List<Ingrediente> ingredientesAtualizados = new ArrayList<>();
        for (Ingrediente ingrediente : produtoParaCadastrar.getIngredientes()) {
            Optional<Ingrediente> ingredienteExistente = ingredienteRepository.findByNome(ingrediente.getNome());
            if (ingredienteExistente.isPresent()) {
                ingredientesAtualizados.add(ingredienteExistente.get());
            } else {
                ingredientesAtualizados.add(ingredienteRepository.save(ingrediente));
            }
        }
        produtoParaCadastrar.setIngredientes(ingredientesAtualizados);

        Produto produtoSalvo = produtoRepository.save(produtoParaCadastrar);
        return ResponseEntity.status(201).body(produtoSalvo);
    }


}
