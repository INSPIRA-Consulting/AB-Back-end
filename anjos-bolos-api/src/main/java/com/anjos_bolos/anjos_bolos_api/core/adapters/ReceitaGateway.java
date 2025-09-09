package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Receita;

import java.util.List;

public interface ReceitaGateway {

    Receita save(Receita receita);

    boolean existsByNome(String nome);

    List<Receita> findAll();

    Receita findById(Integer id);

    List<Receita> findByNome(String nome);

    List<Receita> findByIngredienteId(List<IngredienteEntity> ingredienteEntities);

    List<Receita> findByTipoReceitaId(Integer tipoReceitaId);

    void update(Receita receita);

    void delete(Integer id);
}
