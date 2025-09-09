package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.ListIngredientesQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

import java.util.List;

public class ListIngredientesUseCase {
    private final IngredienteGateway gateway;

    public ListIngredientesUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Ingrediente> execute(ListIngredientesQuery query) {
        return gateway.findAll();
    }
}
