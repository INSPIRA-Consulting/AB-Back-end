package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.UpdatePedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

public class UpdatePedidoUseCase {

    private final PedidoGateway gateway;
    private final UsuarioGateway usuarioGateway;
    private final ClienteGateway clienteGateway;

    public UpdatePedidoUseCase(PedidoGateway gateway, UsuarioGateway usuarioGateway, ClienteGateway clienteGateway) {
        this.gateway = gateway;
        this.usuarioGateway = usuarioGateway;
        this.clienteGateway = clienteGateway;
    }

    public Pedido execute (UpdatePedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(command.id()));
        }

        Usuario usuario = usuarioGateway.findById(command.usuarioId());
        Cliente cliente = clienteGateway.findById(command.clienteId());

        if (usuario == null) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(command.usuarioId()));
        }

        if (cliente == null) {
            throw new NotFoundException("Cliente com ID [%d] não encontrado.".formatted(command.clienteId()));
        }

        Pedido pedido = gateway.findById(command.id());
        pedido.setDataPedido(command.dataPedido());
        pedido.setDataRetirada(command.dataRetirada());
        pedido.setDataPagamento(command.dataPagamento());
        pedido.setFormaPagamento(FormaPagamentoEnum.from(command.formaPagamento()));
        pedido.setStatus(StatusPedidoEnum.from(command.status()));
        pedido.setObservacao(command.observacao());
        pedido.setUsuario(usuario);
        pedido.setCliente(cliente);

        return gateway.update(pedido);
    }

}