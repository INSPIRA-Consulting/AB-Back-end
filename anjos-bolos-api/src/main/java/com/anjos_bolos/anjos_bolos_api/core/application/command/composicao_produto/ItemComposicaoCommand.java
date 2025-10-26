package com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto;

public record ItemComposicaoCommand(
        Integer receitaId,
        Double quantidade,
        String observacao
) {}