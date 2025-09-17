package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.DeleteClienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteClienteUseCase {
    private final ClienteGateway gateway;

    public DeleteClienteUseCase(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteClienteCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado.".formatted(command.id()));
        }

        gateway.delete(command.id());
    }
}
