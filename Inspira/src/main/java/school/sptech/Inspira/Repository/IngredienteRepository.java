package school.sptech.Inspira.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Inspira.Entity.Ingrediente;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByNomeContainsIgnoreCase(String nome);

    Optional<Ingrediente> findByNome(String nome);

    Boolean existsByNome(String nome);
}
