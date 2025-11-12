package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

import java.time.LocalDate;

public record PedidosFaturamentoResponseDTO(
        Long quantidadePedidos,
        Long quantidadeProdutosVendidos,
        Double faturamento,
        Double custos,
        LocalDate dataPedido
) {}