package com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido;

public record CreateDetalhamentoPedidoCommand(
        Integer itemPedidoId,
        Integer receitaId,
        String observacao
) {}