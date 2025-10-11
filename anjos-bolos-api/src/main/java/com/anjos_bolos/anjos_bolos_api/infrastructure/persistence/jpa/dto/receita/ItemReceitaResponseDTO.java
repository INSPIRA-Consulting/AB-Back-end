package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita;

public record ItemReceitaResponseDTO(
        Integer ingredienteId,
        String nome,
        Double quantidade,
        String unidadeMedida
) {
}
