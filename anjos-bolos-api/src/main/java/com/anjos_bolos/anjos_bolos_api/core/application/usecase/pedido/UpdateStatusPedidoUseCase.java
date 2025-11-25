package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.UpdateStatusPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;

import java.time.LocalDateTime;

public class UpdateStatusPedidoUseCase {

    private final PedidoGateway gateway;

    public UpdateStatusPedidoUseCase(PedidoGateway gateway) {
        this.gateway = gateway;
    }

    public Pedido execute (UpdateStatusPedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(command.id()));
        }

        Pedido pedido = gateway.findById(command.id());

        StatusPedidoEnum status = StatusPedidoEnum.from(command.status());

        FormaPagamentoEnum formaPagamento =
                (pedido.getFormaPagamento() == null || pedido.getFormaPagamento() == FormaPagamentoEnum.NAO_REALIZADO) ?
                FormaPagamentoEnum.from(command.formaPagamento()) :
                pedido.getFormaPagamento();

        if (pedido.getDataPagamento() != command.dataRetirada()) {
            if (command.dataRetirada() != null) {
                pedido.setDataRetirada(command.dataRetirada());
            }
        }

        if (pedido.getObservacao() != command.observacao()) {
            if (command.observacao() != null) {
                pedido.setObservacao(command.observacao());
            }
        }

        if (status == StatusPedidoEnum.PENDENTE_PAGAMENTO || status == StatusPedidoEnum.CANCELADO) {
            pedido.setStatus(status);
        }

        if (status == StatusPedidoEnum.CONFIRMADO || status == StatusPedidoEnum.FINALIZADO) {
            if (pedido.getDataPagamento() == null) {
                pedido.setDataPagamento(LocalDateTime.now());
            }

            if (formaPagamento == null || formaPagamento == FormaPagamentoEnum.NAO_REALIZADO) {
                throw new IllegalArgumentException("Forma de Pagamento deve ser fornecida para o Status [%s].".formatted(status));
            }

            pedido.setFormaPagamento(formaPagamento);

            pedido.setStatus(status);
        }


        return gateway.update(pedido);
    }

}