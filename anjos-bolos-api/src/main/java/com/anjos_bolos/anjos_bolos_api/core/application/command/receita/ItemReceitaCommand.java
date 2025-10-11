package com.anjos_bolos.anjos_bolos_api.core.application.command.receita;

public record ItemReceitaCommand(
        Integer ingredienteId,
        Double quantidade,
        String unidadeMedida
) {}