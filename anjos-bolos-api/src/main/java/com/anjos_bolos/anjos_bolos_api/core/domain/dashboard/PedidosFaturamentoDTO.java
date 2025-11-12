package com.anjos_bolos.anjos_bolos_api.core.domain.dashboard;

import java.time.LocalDate;

public record PedidosFaturamentoDTO(
        Long quantidadePedidos,
        Long quantidadeProdutosVendidos,
        Double faturamento,
        Double custos,
        LocalDate dataPedido
) {}