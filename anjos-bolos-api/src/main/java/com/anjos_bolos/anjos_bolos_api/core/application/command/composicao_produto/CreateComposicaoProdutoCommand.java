package com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto;

import java.util.List;

public record CreateComposicaoProdutoCommand(
        Integer produtoId,
        List<ItemComposicaoCommand> receitas
) {}