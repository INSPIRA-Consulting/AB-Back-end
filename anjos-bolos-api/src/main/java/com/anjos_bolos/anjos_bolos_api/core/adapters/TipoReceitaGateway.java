package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.List;

public interface TipoReceitaGateway {

    TipoReceita save(TipoReceita tipoReceita);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    List<TipoReceita> findAll();

    TipoReceita findById(Integer id);

    TipoReceita findByNome(String nome);

    TipoReceita update(TipoReceita tipoReceita);

    void delete(Integer id);
}
