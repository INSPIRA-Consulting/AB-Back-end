package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.GetClienteByCpfQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;

public class GetClienteByCpfUseCase {
    private final ClienteGateway gateway;

    public GetClienteByCpfUseCase(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public Cliente execute(GetClienteByCpfQuery query) {
        Cliente cliente = gateway.findByCpf(query.cpf());

        if (cliente == null) {
            throw new NotFoundException("Cliente com CPF %s não encontrado.".formatted(query.cpf()));
        }

        return cliente;
    }
}