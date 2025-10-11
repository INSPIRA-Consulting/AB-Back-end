package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReceitaGateway {

    Receita save(Receita receita);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Integer id);

    boolean existsByIngredientesIds(List<Integer> ingredienteIds);

    boolean existsByIngredientesIdsAndIdNot(List<Integer> ingredienteIds, Integer id);

    Integer findNextId();

    List<Receita> findAll();

    Optional<Receita> findById(Integer id);

    List<Receita> findByNome(String nome);

    List<Receita> findByIngredientesIds(List<Integer> ingredienteIds);

    List<Receita> findByTipoReceitaId(Integer tipoReceitaId);

    Receita update(Receita receita);

    void delete(Integer id);

}