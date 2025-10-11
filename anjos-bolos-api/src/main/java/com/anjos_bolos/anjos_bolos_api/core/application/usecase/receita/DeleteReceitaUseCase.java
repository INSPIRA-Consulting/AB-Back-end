package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.DeleteReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteReceitaUseCase {

    private final ReceitaGateway gateway;

    public DeleteReceitaUseCase(ReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteReceitaCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Receita com ID [%d] não encontrada."
                    .formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}