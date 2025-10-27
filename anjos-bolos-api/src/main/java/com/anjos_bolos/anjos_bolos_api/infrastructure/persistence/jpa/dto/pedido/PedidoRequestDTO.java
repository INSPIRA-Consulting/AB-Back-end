package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ClienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PedidoRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataPedido,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataRetirada,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataPagamento,
        String formaPagamento,
        String status,
        String observacao,
        Integer usuarioId,
        Integer clienteId
) {}