package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByDataPagamentoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public class ListPedidosByDataPagamentoUseCase {

    private final PedidoGateway gateway;

    public ListPedidosByDataPagamentoUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosByDataPagamentoQuery query) {
        if (query.dataPagamento().isAfter(LocalDateTime.now())) {
            throw new InvalidArgumentException("A Data de Pagamento não pode ser no futuro.");
        }

        List<Pedido> pedidos = gateway.findByDataPagamento(query.dataPagamento());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido encontrado para a Data de Pagamento [%s]."
                    .formatted(query.dataPagamento()));
        }

        return pedidos;
    }

}