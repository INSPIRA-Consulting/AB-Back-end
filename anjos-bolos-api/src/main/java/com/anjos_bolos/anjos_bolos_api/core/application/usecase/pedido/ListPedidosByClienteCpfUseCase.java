package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByClienteCpfQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;

import java.util.List;

public class ListPedidosByClienteCpfUseCase {

    private final PedidoGateway gateway;
    private final ClienteGateway clienteGateway;

    public ListPedidosByClienteCpfUseCase(PedidoGateway gateway, ClienteGateway clienteGateway) {
        this.gateway = gateway;
        this.clienteGateway = clienteGateway;
    }

    public List<Pedido> execute(ListPedidosByClienteCpfQuery query) {
        if (!clienteGateway.existsByCpf(CPF.of(query.clienteCpf()))) {
            throw new NotFoundException("Cliente com CPF [%s] não encontrado.".formatted(query.clienteCpf()));
        }

        List<Pedido> pedidos = gateway.findByClienteCpf(query.clienteCpf());

        if (pedidos.isEmpty()) {
            throw new NotFoundException("Nenhum pedido encontrado para o Cliente com CPF [%s].".formatted(query.clienteCpf()));
        }

        return pedidos;
    }

}