package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.DeleteIngredienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteIngredienteUseCase {
    private final IngredienteGateway gateway;

    public DeleteIngredienteUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteIngredienteCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }
}
