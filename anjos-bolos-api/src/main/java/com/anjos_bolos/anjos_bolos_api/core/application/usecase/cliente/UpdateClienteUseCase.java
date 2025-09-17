package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.UpdateClienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject.ClienteValidator;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class UpdateClienteUseCase {
    private final ClienteGateway gateway;
    private final ClienteValidator validator;

    public UpdateClienteUseCase(ClienteGateway gateway, ClienteValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public Cliente execute(UpdateClienteCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado".formatted(command.id()));
        }

        CPF cpf = CPF.of(command.cpf());
        Telefone telefone = Telefone.of(command.telefone());

        validator.validateUniqueness(command.id(), cpf, telefone);

        Cliente cliente = gateway.findById(command.id());
        cliente.setCpf(cpf);
        cliente.setNome(command.nome());
        cliente.setTelefone(telefone);

        return gateway.update(cliente);
    }
}
