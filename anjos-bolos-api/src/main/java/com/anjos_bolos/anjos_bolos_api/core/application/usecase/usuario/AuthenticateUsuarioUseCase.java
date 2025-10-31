package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class AuthenticateUsuarioUseCase {

    private final UsuarioGateway gateway;

    public AuthenticateUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public String execute(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new InvalidArgumentException("Email e Senha devem ser fornecidos para autenticação.");
        }

        return gateway.authenticate((usuario));
    }

}