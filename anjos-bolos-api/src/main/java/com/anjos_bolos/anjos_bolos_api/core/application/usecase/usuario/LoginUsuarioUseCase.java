package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PasswordEncoderGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.LoginUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.UnauthorizedAcessException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class LoginUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final PasswordEncoderGateway passwordEncoder;

    public LoginUsuarioUseCase(UsuarioGateway gateway, PasswordEncoderGateway passwordEncoder) {
        this.gateway = gateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario execute(LoginUsuarioCommand command) {
        if (command.email() == null || command.senha() == null) {
            throw new InvalidArgumentException("Email e Senha devem ser fornecidos para Login.");
        }

        Usuario usuario = gateway.findByEmail(Email.of(command.email()));

        String encodedSenha = gateway.findEncodedSenhaByEmail(Email.of(command.email()));

        if (usuario == null) {
            throw new UnauthorizedAcessException("Usuário e/ou Senha inválidos.");
        }

        if (encodedSenha == null || !passwordEncoder.matches(command.senha(), encodedSenha)) {
            throw new UnauthorizedAcessException("Usuário e/ou Senha inválidos.");
        }

        usuario.setSenha(command.senha());

        return usuario;
    }

}