package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.ListClientesQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;

import java.util.List;

public class ListClientesUseCase {
    private final ClienteGateway gateway;

    public ListClientesUseCase(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Cliente> execute(ListClientesQuery query) {
        List<Cliente> clientes = gateway.findAll();

        if (clientes.isEmpty()) {
            throw new NotFoundException("Não há Clientes cadastrados.");
        }

        return clientes;
    }
}
