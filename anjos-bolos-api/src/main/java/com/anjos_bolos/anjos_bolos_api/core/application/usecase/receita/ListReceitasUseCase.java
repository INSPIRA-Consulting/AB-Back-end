package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.ListReceitasQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public class ListReceitasUseCase {

    private final ReceitaGateway gateway;

    public ListReceitasUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<Receita> execute(ListReceitasQuery query) {
        List<Receita> receitas = gateway.findAll();

        if (receitas.isEmpty()) {
            throw new NotFoundException("Não há Receitas cadastradas.");
        }

        return receitas;
    }

}