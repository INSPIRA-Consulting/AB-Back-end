package com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard;

import java.time.LocalDate;

public record ListVendasPorDiaSemanaQuery(
        LocalDate inicio,
        LocalDate fim
) {}