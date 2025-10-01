package com.anjos_bolos.anjos_bolos_api.core.application.command.receita;

public record UpdateReceitaCommand(
        Integer id,
        String nome,
        Integer ingredienteId,
        Double quantidade,
        String unidadeMedida,
        Integer tipoReceitaId
) {}