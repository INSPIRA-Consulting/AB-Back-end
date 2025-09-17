package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.GetUsuarioByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class GetUsuarioByIdUseCase {
    private final UsuarioGateway gateway;

    public GetUsuarioByIdUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario execute(GetUsuarioByIdQuery query) {
        Usuario usuario = gateway.findById(query.id());

        if (usuario == null) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado."
                    .formatted(query.id()));
        }

        return usuario;
    }
}