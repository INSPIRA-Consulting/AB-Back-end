package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;

public class GetReceitaByIdUseCase {
    private final ReceitaGateway gateway;

    public GetReceitaByIdUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }
}