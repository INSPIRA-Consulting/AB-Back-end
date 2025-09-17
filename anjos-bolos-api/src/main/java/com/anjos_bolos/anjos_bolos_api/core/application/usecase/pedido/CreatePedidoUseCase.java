package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import java.time.LocalDateTime;

public record CreatePedidoUseCase(
        LocalDateTime dataPedido,
        LocalDateTime dataEntrega,
        LocalDateTime dataPagamento,
        String status,
        String observacao,
        Integer usuarioId,
        Integer clienteId
) {
}
