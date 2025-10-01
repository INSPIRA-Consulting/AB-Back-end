package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.CreateTipoReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

public class CreateTipoReceitaUseCase {

    private final TipoReceitaGateway gateway;

    public CreateTipoReceitaUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public TipoReceita execute(CreateTipoReceitaCommand command) {
        if (gateway.existsByNome(command.nome())) {
            throw new EntityAlreadyExistsException("Já existe um Tipo de Receita com este nome.");
        }

        TipoReceita tipoReceita = new TipoReceita(command.nome(), command.descricao());

        return gateway.save(tipoReceita);
    }

}