package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.ListReceitasByIngredienteIdsQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public class ListReceitasByIngredienteIdsUseCase {

    private final ReceitaGateway gateway;

    public ListReceitasByIngredienteIdsUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<Receita> execute(ListReceitasByIngredienteIdsQuery query) {
        List<Receita> receitas = gateway.findByIngredientesIds(query.ingredienteIds());

        if (receitas.isEmpty()) {
            throw new NotFoundException("Nenhuma Receita encontrada com os Ingredientes de IDs [%s]."
                    .formatted(query.ingredienteIds()));
        }

        return receitas;
    }

}