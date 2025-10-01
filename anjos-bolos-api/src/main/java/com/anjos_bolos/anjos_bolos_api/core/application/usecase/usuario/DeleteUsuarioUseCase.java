package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.DeleteUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;

public class DeleteUsuarioUseCase {

    private final UsuarioGateway gateway;

    public DeleteUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(DeleteUsuarioCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado"
                    .formatted(command.id()));
        }

        gateway.delete(command.id());
    }

}