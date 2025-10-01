package com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.UpdateUsuarioCommand;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioValidator;

public class UpdateUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final UsuarioValidator validator;

    public UpdateUsuarioUseCase(UsuarioGateway gateway, UsuarioValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public Usuario execute(UpdateUsuarioCommand command) {
        CPF cpf = CPF.of(command.cpf());
        Email email = Email.of(command.email());
        Telefone telefone = Telefone.of(command.telefone());

        validator.validateUniqueness(command.id(),cpf, email, telefone);

        Usuario usuario = gateway.findById(command.id());
        usuario.setNome(command.nome());
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(command.senha());
        usuario.setTelefone(telefone);
        usuario.setFuncao(FuncaoUsuarioEnum.valueOf(command.funcao()));

        return gateway.update(usuario);
    }

}