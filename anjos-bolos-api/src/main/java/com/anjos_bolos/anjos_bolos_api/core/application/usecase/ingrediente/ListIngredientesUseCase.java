package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.ListIngredientesQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

import java.util.List;

public class ListIngredientesUseCase {

    private final IngredienteGateway gateway;

    public ListIngredientesUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Ingrediente> execute(ListIngredientesQuery query) {
        List<Ingrediente> ingredientes = gateway.findAll();

        if (ingredientes.isEmpty()) {
            throw new NotFoundException("Não há Ingredientes cadastrados.");
        }

        return gateway.findAll();
    }

}