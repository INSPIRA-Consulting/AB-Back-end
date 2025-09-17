package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;

public class UpdateReceitaUseCase {
    private final ReceitaGateway gateway;
    private final IngredienteGateway ingredienteGateway;


    public UpdateReceitaUseCase(ReceitaGateway gateway, IngredienteGateway ingredienteGateway) {
        this.gateway = gateway;
        this.ingredienteGateway = ingredienteGateway;
    }
}
