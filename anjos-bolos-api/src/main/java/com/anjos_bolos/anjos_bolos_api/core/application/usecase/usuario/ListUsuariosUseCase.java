package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.ListUsuariosQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.util.List;

public class ListUsuariosUseCase {
    private final UsuarioGateway gateway;

    public ListUsuariosUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<Usuario> execute(ListUsuariosQuery query) {
        List<Usuario> usuarios = gateway.findAll();

        if (usuarios.isEmpty()) {
            throw new NotFoundException("Não há usuários cadastrados.");
        }

        return usuarios;
    }
}
