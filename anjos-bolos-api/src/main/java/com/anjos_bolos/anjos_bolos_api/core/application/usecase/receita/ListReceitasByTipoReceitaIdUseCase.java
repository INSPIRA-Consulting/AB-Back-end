package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;

public class ListReceitasByTipoReceitaIdUseCase {
    private final ReceitaGateway gateway;

    public ListReceitasByTipoReceitaIdUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }
}