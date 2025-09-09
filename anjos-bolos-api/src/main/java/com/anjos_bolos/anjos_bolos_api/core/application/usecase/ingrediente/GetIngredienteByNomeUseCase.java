package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.GetIngredienteByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

import java.util.List;

public class GetIngredienteByNomeUseCase {
    private final IngredienteGateway gateway;

    public GetIngredienteByNomeUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Ingrediente> execute(GetIngredienteByNomeQuery query) {
        return gateway.findByNome(query.nome());
    }
}
