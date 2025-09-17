package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.UpdateItemPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class UpdateItemPedidoUseCase {
    private final ItemPedidoGateway gateway;
    private final PedidoGateway pedidoGateway;
    private final ProdutoGateway produtoGateway;

    public UpdateItemPedidoUseCase(ItemPedidoGateway gateway, PedidoGateway pedidoGateway, ProdutoGateway produtoGateway) {
        this.gateway = gateway;
        this.pedidoGateway = pedidoGateway;
        this.produtoGateway = produtoGateway;
    }

    public ItemPedido execute(UpdateItemPedidoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Item de Pedido com ID [%d] não enconttado."
                    .formatted(command.id()));
        }

        if (gateway.existsByPedidoIdAndProdutoIdAndIdNot(command.pedidoId(), command.produtoId(), command.id())) {
            throw new EntityAlreadyExistsException("""
                    Já existe um Item de Pedido para o Pedido com ID [%d]
                    e Produto com ID [%d].""".formatted(command.pedidoId(), command.produtoId()));
        }

        Pedido pedido = pedidoGateway.findById(command.pedidoId());
        Produto produto = produtoGateway.findById(command.produtoId());

        ItemPedido itemPedido = gateway.findById(command.id());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(command.quantidade());
        itemPedido.setValorFinal(command.valorFinal());
        itemPedido.setPeso(command.peso());

        return gateway.update(itemPedido);
    }
}
