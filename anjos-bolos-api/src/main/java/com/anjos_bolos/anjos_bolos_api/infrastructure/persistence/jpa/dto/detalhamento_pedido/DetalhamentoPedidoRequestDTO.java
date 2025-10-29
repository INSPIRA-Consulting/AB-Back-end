package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido;

public record DetalhamentoPedidoRequestDTO(
        Integer fkItemPedido,
        Integer fkReceita,
        String observacao
) {}