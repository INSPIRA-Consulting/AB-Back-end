package com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto;

import java.util.List;

public record UpdateComposicaoProdutoCommand(
        Integer produtoId,
        List<ItemComposicaoCommand> receitas
) {}