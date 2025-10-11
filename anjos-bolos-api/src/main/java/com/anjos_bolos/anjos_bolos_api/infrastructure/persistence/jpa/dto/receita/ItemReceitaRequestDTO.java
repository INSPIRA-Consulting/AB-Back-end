package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita;

public record ItemReceitaRequestDTO(
        Integer ingredienteId,
        Double quantidade,
        String unidadeMedida
) {
}
