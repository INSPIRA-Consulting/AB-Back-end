package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.ListTiposReceitaByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.List;

public class ListTiposReceitaByNomeUseCase {
    private final TipoReceitaGateway gateway;

    public ListTiposReceitaByNomeUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<TipoReceita> execute(ListTiposReceitaByNomeQuery query) {
        List<TipoReceita> tiposReceita = gateway.findByNome(query.nome());

        if (tiposReceita.isEmpty()) {
            throw new NotFoundException("Nenhum Tipo de Receita encontrado com o nome: %s"
                    .formatted(query.nome()));
        }

        return tiposReceita;
    }
}
