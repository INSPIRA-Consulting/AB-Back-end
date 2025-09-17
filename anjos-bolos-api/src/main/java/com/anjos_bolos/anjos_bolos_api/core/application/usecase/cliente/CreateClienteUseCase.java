package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.CreateClienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject.ClienteValidator;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class CreateClienteUseCase {
    private final ClienteGateway gateway;
    private final ClienteValidator validator;

    public CreateClienteUseCase(ClienteGateway gateway, ClienteValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public Cliente execute(CreateClienteCommand command) {
        CPF cpf = CPF.of(command.cpf());
        Telefone telefone = Telefone.of(command.telefone());

        validator.validateUniqueness(cpf, telefone);

        Cliente cliente = new Cliente(cpf, command.nome(), telefone);

        return gateway.save(cliente);
    }
}
