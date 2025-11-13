package com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido;

public record UpdateItemPedidoCommand(
        Integer id,
        Integer pedidoId,
        Integer produtoId,
        Double precoUnitario,
        Integer quantidade,
        Double peso
) {}