package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListPedidosByFormaPagamentoQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;

import java.util.List;

public class ListPedidosByFormaPagamentoUseCase {

    private final PedidoGateway gateway;

    public ListPedidosByFormaPagamentoUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> execute(ListPedidosByFormaPagamentoQuery query) {
        try {
            FormaPagamentoEnum.valueOf(query.formaPagamento());
        } catch (IllegalArgumentException ex) {
            throw new InvalidArgumentException("Forma de Pagamento inválida: " + query.formaPagamento());
        }

        List<Pedido> pedidos = gateway.findByStatus(query.formaPagamento());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido pago com: " + query.formaPagamento());
        }

        return pedidos;
    }

}