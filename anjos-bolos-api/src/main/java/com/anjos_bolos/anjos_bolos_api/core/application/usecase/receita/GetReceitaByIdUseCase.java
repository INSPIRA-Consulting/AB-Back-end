package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.GetReceitaByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class GetReceitaByIdUseCase {

    private final ReceitaGateway gateway;

    public GetReceitaByIdUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public Receita execute(GetReceitaByIdQuery query) {
        return gateway.findById(query.id())
                .orElseThrow(() -> new NotFoundException("Receita com ID [%d] não encontrada.".formatted(query.id())));
    }

}