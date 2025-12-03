package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record StatusPedidoRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataRetirada,
        String formaPagamento,
        String status,
        String observacao
) {}