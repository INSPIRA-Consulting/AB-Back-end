package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.GetUsuarioByCpfQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class GetUsuarioByCpfUseCase {
    private final UsuarioGateway gateway;

    public GetUsuarioByCpfUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario execute(GetUsuarioByCpfQuery query) {
        Usuario usuario = gateway.findByCpf(CPF.of(query.cpf()));

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado com o CPF: %s"
                    .formatted(query.cpf()));
        }

        return usuario;
    }
}