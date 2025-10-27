package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.ListIngredientesPageableQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import org.springframework.data.domain.Page;

public class ListIngredientesPageableUseCase {
    private final IngredienteGateway gateway;

    public ListIngredientesPageableUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public Page<Ingrediente> execute(ListIngredientesPageableQuery query) {
        Page<Ingrediente> ingredientes = gateway.findAll(query.pageable());

        if (ingredientes.isEmpty()) {
            throw new NotFoundException("Não há Ingredientes cadastrados.");
        }

        return ingredientes;
    }
}