package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.LoginUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.UnauthorizedAcessException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class LoginUsuarioUseCase {

    private final UsuarioGateway gateway;

    public LoginUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario execute(LoginUsuarioCommand command) {
        if (command.email() == null || command.senha() == null) {
            throw new InvalidArgumentException("Email e Senha devem ser fornecidos para Login.");
        }

        if (!gateway.existsByEmailAndSenha(Email.of(command.email()), command.senha())) {
            throw new UnauthorizedAcessException("Usuário e/ou Senha inválidos.");
        }

        return gateway.login(Email.of(command.email()), command.senha());
    }

}