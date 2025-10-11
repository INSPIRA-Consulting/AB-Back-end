package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.ListReceitasByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public class ListReceitasByNomeUseCase {

    private final ReceitaGateway gateway;

    public ListReceitasByNomeUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<Receita> execute(ListReceitasByNomeQuery query) {
        List<Receita> receitas = gateway.findByNome(query.nome());

        if (receitas.isEmpty()) {
            throw new NotFoundException("Nenhuma Receita encontrada com o nome: '%s'."
                    .formatted(query.nome()));
        }

        return receitas;
    }

}