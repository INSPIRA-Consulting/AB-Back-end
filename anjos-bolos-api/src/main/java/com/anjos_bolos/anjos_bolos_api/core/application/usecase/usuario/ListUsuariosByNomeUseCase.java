package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.ListUsuariosByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.util.List;

public class ListUsuariosByNomeUseCase {

    private final UsuarioGateway gateway;

    public ListUsuariosByNomeUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<Usuario> execute(ListUsuariosByNomeQuery query) {
        List<Usuario> usuarios = gateway.findByNome(query.nome());

        if (usuarios.isEmpty()) {
            throw new NotFoundException("Nenhum usuário encontrado com o nome: %s"
                    .formatted(query.nome()));
        }

        return usuarios;
    }

}