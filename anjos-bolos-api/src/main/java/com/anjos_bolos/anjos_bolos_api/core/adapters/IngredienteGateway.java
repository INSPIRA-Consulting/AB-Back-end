package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IngredienteGateway {
    Ingrediente save(Ingrediente ingrediente);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    Page<Ingrediente> findAll();

    Page<Ingrediente> findAll(Pageable pageable);

    Ingrediente findById(Integer id);

    List<Ingrediente> findByNome(String nome);

    Ingrediente update(Ingrediente ingrediente);

    void delete(Integer id);
}
