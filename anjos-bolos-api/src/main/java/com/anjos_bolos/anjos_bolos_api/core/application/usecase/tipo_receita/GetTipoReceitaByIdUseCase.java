package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.GetTipoReceitaByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

public class GetTipoReceitaByIdUseCase {
    private final TipoReceitaGateway gateway;

    public GetTipoReceitaByIdUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public TipoReceita execute(GetTipoReceitaByIdQuery query) {
        TipoReceita tipoReceita = gateway.findById(query.id());

        if (tipoReceita == null) {
            throw new NotFoundException("Tipo de Receita com ID [%d] não encontrado.".formatted(query.id()));
        }

        return tipoReceita;
    }
}