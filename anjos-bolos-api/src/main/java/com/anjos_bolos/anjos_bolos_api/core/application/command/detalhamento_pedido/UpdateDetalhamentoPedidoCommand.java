package com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido;

public record UpdateDetalhamentoPedidoCommand(
        Integer id,
        Integer itemPedidoId,
        Integer receitaId,
        String observacao
) {
}
