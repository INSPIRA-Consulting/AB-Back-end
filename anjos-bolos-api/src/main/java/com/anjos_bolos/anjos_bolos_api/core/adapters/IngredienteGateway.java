package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import java.util.List;

public interface IngredienteGateway {

    Ingrediente save(Ingrediente ingrediente);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    List<Ingrediente>findAll();

    Ingrediente findById(Integer id);

    List<Ingrediente> findByNome(String nome);

    Ingrediente update(Ingrediente ingrediente);

    void delete(Integer id);

}