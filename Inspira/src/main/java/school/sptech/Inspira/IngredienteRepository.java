package school.sptech.Inspira;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByNomeContainsIgnoreCase(String nome);

    Optional<Ingrediente> findByNome(String nome);
}
