package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

public record PedidosFaturamentoResponseDTO(
        Long quantidadePedidos,
        Long quantidadeProdutosVendidos,
        Double faturamento,
        Double custos
) {}