package com.anjos_bolos.anjos_bolos_api.core.application.command.produto;

public record UpdateProdutoCommand(
        Integer id,
        String nome,
        Double precoFinal,
        Double custoProducao,
        Integer categoriaProdutoId
) {}