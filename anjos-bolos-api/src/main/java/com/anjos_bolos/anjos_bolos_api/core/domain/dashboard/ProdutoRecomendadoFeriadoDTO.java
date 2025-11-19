package com.anjos_bolos.anjos_bolos_api.core.domain.dashboard;

import java.time.LocalDate;

public record ProdutoRecomendadoFeriadoDTO(
        LocalDate dataFeriado,
        String feriado,
        String produto,
        String categoria
) {}