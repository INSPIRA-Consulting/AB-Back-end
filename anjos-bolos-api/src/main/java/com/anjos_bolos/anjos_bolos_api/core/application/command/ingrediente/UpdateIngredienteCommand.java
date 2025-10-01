package com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente;

public record UpdateIngredienteCommand(
        Integer id,
        String nome,
        Double valorEmbalagem,
        Double quantidadeEmbalagem
) {}