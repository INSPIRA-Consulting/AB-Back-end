package com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido;

public record CreateItemPedidoCommand(
        Integer pedidoId,
        Integer produtoId,
        Integer quantidade,
        Double peso
) {}