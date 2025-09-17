package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.GetIngredienteByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

public class GetIngredienteByIdUseCase {
    private final IngredienteGateway gateway;

    public GetIngredienteByIdUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public Ingrediente execute(GetIngredienteByIdQuery query) {
        Ingrediente ingrediente = gateway.findById(query.id());

        if (ingrediente == null) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(query.id()));
        }

        return ingrediente;
    }
}