package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.ListReceitasByTipoReceitaIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.List;

public class ListReceitasByTipoReceitaIdUseCase {

    private final ReceitaGateway gateway;

    public ListReceitasByTipoReceitaIdUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public List<Receita> execute(ListReceitasByTipoReceitaIdQuery query) {
        List<Receita> receitas = gateway.findByTipoReceitaId(query.tipoReceitaId());

        if (receitas.isEmpty()) {
            throw new NotFoundException("Nenhuma Receita encontrada com o Tipo de Receita de ID [%d]."
                    .formatted(query.tipoReceitaId()));
        }

        return receitas;
    }

}