package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.CreateUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioValidator;

public class CreateUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final UsuarioValidator validator;

    public CreateUsuarioUseCase(UsuarioGateway gateway, UsuarioValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public Usuario execute(CreateUsuarioCommand command) {
        CPF cpf = CPF.of(command.cpf());
        Email email = Email.of(command.email());
        Telefone telefone = Telefone.of(command.telefone());

        validator.validateUniqueness(cpf, email, telefone);

        Usuario usuario = new Usuario(
                command.nome(),
                cpf,
                email,
                command.senha(),
                telefone,
                FuncaoUsuarioEnum.valueOf(command.funcao()));

        return gateway.save(usuario);
    }

}