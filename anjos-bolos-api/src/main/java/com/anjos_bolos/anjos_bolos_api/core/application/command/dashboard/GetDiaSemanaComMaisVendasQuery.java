package com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard;

import java.time.LocalDate;

public record GetDiaSemanaComMaisVendasQuery(
        LocalDate inicio,
        LocalDate fim,
        Integer limit
) {}