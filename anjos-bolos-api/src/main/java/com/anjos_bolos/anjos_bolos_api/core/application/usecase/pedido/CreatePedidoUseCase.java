package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.CreatePedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class CreatePedidoUseCase {

    private final PedidoGateway gateway;
    private final UsuarioGateway usuarioGateway;
    private final ClienteGateway clienteGateway;

    public CreatePedidoUseCase(PedidoGateway gateway, UsuarioGateway usuarioGateway, ClienteGateway clienteGateway) {
        this.gateway = gateway;
        this.usuarioGateway = usuarioGateway;
        this.clienteGateway = clienteGateway;
    }

    public Pedido execute (CreatePedidoCommand command) {
        Usuario usuario = usuarioGateway.findById(command.usuarioId());
        Cliente cliente = clienteGateway.findById(command.clienteId());
        StatusPedidoEnum status = StatusPedidoEnum.from(command.status());
        FormaPagamentoEnum formaPagamento = FormaPagamentoEnum.from(command.formaPagamento());
        LocalDateTime dataPagamento = command.dataPagamento();

        if (usuario == null) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(command.usuarioId()));
        }

        if (cliente == null) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado.".formatted(command.clienteId()));
        }

        if (status == StatusPedidoEnum.PENDENTE_PAGAMENTO) {
            dataPagamento = null;
            formaPagamento = FormaPagamentoEnum.NAO_REALIZADO;
        }

        if (status == StatusPedidoEnum.CONFIRMADO || status == StatusPedidoEnum.FINALIZADO) {
            if (dataPagamento == null || formaPagamento == null || formaPagamento == FormaPagamentoEnum.NAO_REALIZADO) {
                throw new IllegalArgumentException("Data de Pagamento e Forma de Pagamento devem ser fornecidas para o Status [%s].".formatted(status));
            }
        }

        if (status == StatusPedidoEnum.CANCELADO) {
            throw new IllegalArgumentException("Não é possível criar um Pedido com o Status [CANCELADO].");
        }

        Pedido pedido = new Pedido(
                command.dataPedido(),
                command.dataRetirada(),
                dataPagamento,
                formaPagamento,
                StatusPedidoEnum.from(command.status()),
                command.observacao(),
                usuario,
                cliente
        );

        return gateway.save(pedido);
    }

}