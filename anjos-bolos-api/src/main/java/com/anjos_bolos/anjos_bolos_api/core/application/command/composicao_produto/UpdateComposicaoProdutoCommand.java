package com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto;

public record UpdateComposicaoProdutoCommand(
        Integer id,
        Integer produtoId,
        Integer receitaId,
        Integer quantidade,
        String observacao
) {}