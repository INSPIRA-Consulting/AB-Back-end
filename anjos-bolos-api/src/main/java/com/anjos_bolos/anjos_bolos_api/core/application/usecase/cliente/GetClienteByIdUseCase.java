package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.GetClienteByIdQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;

public class GetClienteByIdUseCase {
    private final ClienteGateway gateway;

    public GetClienteByIdUseCase(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public Cliente execute(GetClienteByIdQuery query) {
        Cliente cliente = gateway.findById(query.id());

        if (cliente == null) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado.".formatted(query.id()));
        }

        return cliente;
    }
}