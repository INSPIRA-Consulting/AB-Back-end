package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente;

public record IngredienteResponseDTO(
        Integer id,
        String nome,
        Double custoMedida
) {}