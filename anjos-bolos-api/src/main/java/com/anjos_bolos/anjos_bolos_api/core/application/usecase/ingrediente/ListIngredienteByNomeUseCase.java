package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.ListIngredientesByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

import java.util.List;

public class ListIngredienteByNomeUseCase {
    private final IngredienteGateway gateway;

    public ListIngredienteByNomeUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Ingrediente> execute(ListIngredientesByNomeQuery query) {
        List<Ingrediente> ingredientes = gateway.findByNome(query.nome());

        if (ingredientes.isEmpty()) {
            throw new NotFoundException("Nenhum Ingrediente encontrado com o nome: '%s'." + query.nome());
        }

        return ingredientes;
    }
}
