package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.ListUsuariosByFuncaoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.ListUsuariosByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;

import java.util.List;

public class ListUsuariosByFuncaoUseCase {

    private final UsuarioGateway gateway;

    public ListUsuariosByFuncaoUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<Usuario> execute(ListUsuariosByFuncaoQuery query) {
        List<Usuario> usuarios = gateway.findByFuncao(FuncaoUsuarioEnum.valueOf(query.funcao()));

        if (usuarios.isEmpty()) {
            throw new NotFoundException("Nenhum usuário encontrado com a Função: %s"
                    .formatted(query.funcao()));
        }

        return usuarios;
    }

}