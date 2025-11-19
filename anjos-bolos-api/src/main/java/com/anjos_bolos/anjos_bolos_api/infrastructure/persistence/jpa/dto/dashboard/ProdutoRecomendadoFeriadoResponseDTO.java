package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

import java.time.LocalDate;

public record ProdutoRecomendadoFeriadoResponseDTO(
        LocalDate dataFeriado,
        String feriado,
        String produto,
        String categoria
) {}