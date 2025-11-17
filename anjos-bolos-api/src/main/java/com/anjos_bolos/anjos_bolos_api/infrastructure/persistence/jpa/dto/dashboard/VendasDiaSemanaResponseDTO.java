package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

public record VendasDiaSemanaResponseDTO(
        String diaSemana,
        Long totalVendas
) {}