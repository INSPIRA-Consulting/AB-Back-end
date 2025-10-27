package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido;

public record ItemPedidoResponseDTO(
        Integer id,
        Integer pedidoId,
        String produto,
        Integer quantidade,
        Double valorFinal,
        Double custoProducao,
        Double peso
) {}