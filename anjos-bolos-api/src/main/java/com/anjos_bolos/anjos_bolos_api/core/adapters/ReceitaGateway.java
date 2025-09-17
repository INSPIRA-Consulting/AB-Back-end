package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public interface ReceitaGateway {

    Receita save(Receita receita);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

        boolean existsByIngredientesIds(List<Integer> ingredienteIds);

    List<Receita> findAll();

    Receita findById(Integer id);

    List<Receita> findByNome(String nome);

    List<Receita> finByIngredientesIds(List<Integer> ingredienteIds);

    List<Receita> findByTipoReceitaId(Integer tipoReceitaId);

    Receita update(Receita receita);

    void delete(Integer id);
}
