package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PasswordEncoderGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.CreateUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioValidator;

import java.util.Arrays;

public class CreateUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final UsuarioValidator validator;
    private final PasswordEncoderGateway passwordEncoder;

    public CreateUsuarioUseCase(UsuarioGateway gateway, UsuarioValidator validator, PasswordEncoderGateway passwordEncoder) {
        this.gateway = gateway;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario execute(CreateUsuarioCommand command) {
        CPF cpf = CPF.of(command.cpf());
        Email email = Email.of(command.email());
        Telefone telefone = Telefone.of(command.telefone());

        validator.validateUniqueness(cpf, email, telefone);

        String encodedPassword = passwordEncoder.encode(command.senha());

        if (!FuncaoUsuarioEnum.contains(command.funcao())) {
            throw new InvalidArgumentException("Função de Usuário inválida. Funções válidas: %s"
                    .formatted(FuncaoUsuarioEnum.names()));
        }

        Usuario usuario = new Usuario(
                command.nome(),
                cpf,
                email,
                encodedPassword,
                telefone,
                FuncaoUsuarioEnum.valueOf(command.funcao()));

        return gateway.save(usuario);
    }

}