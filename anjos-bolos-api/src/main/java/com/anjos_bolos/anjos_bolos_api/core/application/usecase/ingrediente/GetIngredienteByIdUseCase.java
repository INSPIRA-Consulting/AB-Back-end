package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.GetIngredienteByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

public class GetIngredienteByIdUseCase {
    private final IngredienteGateway gateway;

    public GetIngredienteByIdUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public Ingrediente execute(GetIngredienteByIdQuery query) {
        return gateway.findById(query.id());
    }
}