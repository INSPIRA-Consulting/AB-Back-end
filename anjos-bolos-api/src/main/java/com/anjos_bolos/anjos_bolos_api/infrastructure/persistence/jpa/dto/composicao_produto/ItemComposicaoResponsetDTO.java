package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto;

public record ItemComposicaoResponsetDTO(
        Integer receitaId,
        String nome,
        String tipoReceita,
        Double quantidade,
        String observacao
) {}