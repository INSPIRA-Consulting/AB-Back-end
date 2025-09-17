package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.GetUsuarioByCpfQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.GetUsuarioByEmailQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class GetUsuarioByEmailUseCase {
    private final UsuarioGateway gateway;

    public GetUsuarioByEmailUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario execute(GetUsuarioByEmailQuery query) {
        Usuario usuario = gateway.findByEmail(Email.of(query.email()));

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado com o Email: %s"
                    .formatted(query.email()));
        }

        return usuario;
    }
}