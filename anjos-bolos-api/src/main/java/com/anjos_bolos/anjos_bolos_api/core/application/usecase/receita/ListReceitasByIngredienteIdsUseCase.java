package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;

import java.util.List;

public class ListReceitasByIngredienteIdsUseCase {
    private final ReceitaGateway gateway;

    public ListReceitasByIngredienteIdsUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }
}