package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido;

public record ItemPedidoRequestDTO(
        Integer pedidoId,
        Integer produtoId,
        Integer quantidade,
        Double peso
) {}