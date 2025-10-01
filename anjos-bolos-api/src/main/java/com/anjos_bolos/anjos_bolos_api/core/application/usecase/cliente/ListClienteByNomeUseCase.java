package com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.ListClienteByNomeQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;

import java.util.List;

public class ListClienteByNomeUseCase {

    private final ClienteGateway gateway;

    public ListClienteByNomeUseCase(ClienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Cliente> execute(ListClienteByNomeQuery query) {
        List<Cliente> clientes = gateway.findByNome(query.nome());

        if (clientes.isEmpty()) {
            throw new NotFoundException("Nenhum cliente encontrado com o nome: %s".formatted(query.nome()));
        }

        return clientes;
    }

}