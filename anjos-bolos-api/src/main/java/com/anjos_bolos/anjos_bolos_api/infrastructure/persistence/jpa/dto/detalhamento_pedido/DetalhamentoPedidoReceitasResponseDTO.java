package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido;

import java.util.List;

public record DetalhamentoPedidoReceitasResponseDTO(
        Integer itemPedidoId,
        List<ItemDetalhamentoDTO> receitas
) {}