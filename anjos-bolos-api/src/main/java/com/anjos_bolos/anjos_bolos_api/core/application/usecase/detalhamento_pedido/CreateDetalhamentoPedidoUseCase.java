package com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.CreateDetalhamentoPedidoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class CreateDetalhamentoPedidoUseCase {

    private final DetalhamentoPedidoGateway gateway;
    private final ItemPedidoGateway itemPedidoGateway;
    private final ReceitaGateway receitaGateway;

    public CreateDetalhamentoPedidoUseCase(DetalhamentoPedidoGateway gateway, ItemPedidoGateway itemPedidoGateway, ReceitaGateway receitaGateway) {
        this.gateway = gateway;
        this.itemPedidoGateway = itemPedidoGateway;
        this.receitaGateway = receitaGateway;
    }

    public DetalhamentoPedido execute(CreateDetalhamentoPedidoCommand command) {
        if (gateway.existsByItemPedidoIdAndReceitaId(command.itemPedidoId(), command.receitaId())) {
            throw new EntityAlreadyExistsException("""
                    Já existe um Detalhamento de Pedido para o Item de Pedido com ID [%d] 
                    e Receita com ID [%d]""".formatted(command.itemPedidoId(), command.receitaId()));
        }

        ItemPedido itemPedido = itemPedidoGateway.findById(command.itemPedidoId());
        Receita receita = receitaGateway.findById(command.receitaId())
                .orElseThrow(() -> new NotFoundException("Receita com ID [%d] não encontrada"
                        .formatted(command.receitaId())));

        DetalhamentoPedido detalhamentoPedido = new DetalhamentoPedido(
                itemPedido,
                receita,
                command.observacao()
        );

        return gateway.save(detalhamentoPedido);
    }

}