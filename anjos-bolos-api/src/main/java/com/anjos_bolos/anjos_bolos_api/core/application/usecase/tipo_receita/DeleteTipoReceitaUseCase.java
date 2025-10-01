package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.DeleteTipoReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteTipoReceitaUseCase {

    private final TipoReceitaGateway gateway;

    public DeleteTipoReceitaUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteTipoReceitaCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Tipo de Receita com ID [%d] não encontrado.");
        }

        gateway.delete(command.id());
    }

}