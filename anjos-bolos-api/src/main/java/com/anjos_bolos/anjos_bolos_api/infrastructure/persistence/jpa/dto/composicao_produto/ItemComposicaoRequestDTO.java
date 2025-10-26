package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto;

public record ItemComposicaoRequestDTO(
        Integer receitaId,
        Double quantidade,
        String observacao
) {}