package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente;

public record IngredienteRequestDTO(
        String nome,
        Double valorEmbalagem,
        Double quantidadeEmbalagem
) {
}
