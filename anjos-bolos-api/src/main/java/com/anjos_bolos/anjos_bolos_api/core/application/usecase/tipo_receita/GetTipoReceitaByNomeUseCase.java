package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.GetTipoReceitaByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

public class GetTipoReceitaByNomeUseCase {
    private final TipoReceitaGateway gateway;

    public GetTipoReceitaByNomeUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public TipoReceita execute(GetTipoReceitaByNomeQuery query) {
        TipoReceita tipoReceita = gateway.findByNome(query.nome());

        if (tipoReceita == null) {
            throw new NotFoundException("Nenhum Tipo de Receita encontrado com o nome: %s".formatted(query.nome()));
        }

        return tipoReceita;
    }
}
