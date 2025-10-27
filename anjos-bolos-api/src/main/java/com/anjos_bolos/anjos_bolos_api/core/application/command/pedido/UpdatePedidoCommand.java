package com.anjos_bolos.anjos_bolos_api.core.application.command.pedido;

import java.time.LocalDateTime;

public record UpdatePedidoCommand(
        Integer id,
        LocalDateTime dataPedido,
        LocalDateTime dataRetirada,
        LocalDateTime dataPagamento,
        String formaPagamento,
        String status,
        String observacao,
        Integer usuarioId,
        Integer clienteId
) {}