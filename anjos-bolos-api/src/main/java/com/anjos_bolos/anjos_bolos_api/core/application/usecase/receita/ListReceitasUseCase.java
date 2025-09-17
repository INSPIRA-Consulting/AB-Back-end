package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;

public class ListReceitasUseCase {
    private final ReceitaGateway gateway;

    public ListReceitasUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }
}
