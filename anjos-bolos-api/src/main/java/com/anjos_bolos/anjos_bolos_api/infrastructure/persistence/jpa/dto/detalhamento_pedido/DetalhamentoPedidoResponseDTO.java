package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido;

public record DetalhamentoPedidoResponseDTO(
        Integer itemPedidoId,
        Integer receitaId,
        String receitaNome,
        String tipoReceita,
        String observacao
) {}