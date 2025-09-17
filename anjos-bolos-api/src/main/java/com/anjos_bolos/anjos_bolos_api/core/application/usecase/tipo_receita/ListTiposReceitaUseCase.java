package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.ListTiposReceitaQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.List;

public class ListTiposReceitaUseCase {
    private final TipoReceitaGateway gateway;

    public ListTiposReceitaUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<TipoReceita> execute(ListTiposReceitaQuery query) {
        List<TipoReceita> tiposReceita = gateway.findAll();

        if (tiposReceita.isEmpty()) {
            throw new NotFoundException("Não há Tipos de Receita cadastrados.");
        }

        return tiposReceita;
    }
}
