package com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.CreateItemPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class CreateItemPedidoUseCase {

    private final ItemPedidoGateway gateway;
    private final PedidoGateway pedidoGateway;
    private final ProdutoGateway produtoGateway;

    public CreateItemPedidoUseCase(ItemPedidoGateway gateway, PedidoGateway pedidoGateway, ProdutoGateway produtoGateway) {
        this.gateway = gateway;
        this.pedidoGateway = pedidoGateway;
        this.produtoGateway = produtoGateway;
    }

    public ItemPedido execute(CreateItemPedidoCommand command) {
        if (gateway.existsByPedidoIdAndProdutoId(command.pedidoId(), command.produtoId())) {
            throw new EntityAlreadyExistsException("""
                    Já existe um Item de Pedido para o Pedido com ID [%d]
                    e Produto com ID [%d].""".formatted(command.pedidoId(), command.produtoId()));
        }

        Pedido pedido = pedidoGateway.findById(command.pedidoId());
        Produto produto = produtoGateway.findById(command.produtoId());

        ItemPedido itemPedido = new ItemPedido(
                pedido,
                produto,
                command.quantidade(),
                command.valorFinal(),
                command.custoProducao(),
                command.peso()
        );

        return gateway.save(itemPedido);
    }

}