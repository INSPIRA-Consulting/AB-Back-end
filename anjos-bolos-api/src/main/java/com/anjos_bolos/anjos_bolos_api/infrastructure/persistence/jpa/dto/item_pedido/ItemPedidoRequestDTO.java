package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemPedidoRequestDTO(
        Integer pedidoId,
        Integer produtoId,
        @JsonProperty(defaultValue = "0.0")
        Double precoUnitario,
        Integer quantidade,
        Double peso
) {}