package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.List;

public interface TipoReceitaGateway {

    TipoReceita save(TipoReceita tipoReceita);

    boolean existsByTipoReceita(String tipoReceita);

    List<TipoReceita> findAll();

    TipoReceita findById(Integer id);

    TipoReceita findByTipoReceita(String tipoReceita);

    void update(TipoReceita tipoReceita);

    void delete(Integer id);
}
