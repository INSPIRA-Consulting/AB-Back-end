package com.anjos_bolos.anjos_bolos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByNomeContainsIgnoreCase(String nome);

    Optional<Ingrediente> findByNome(String nome);

    Boolean existsByNomeEqualsIgnoreCaseAndIdIngredienteNot(String nome, Integer id);

    Boolean existsByNome(String nome);
}
