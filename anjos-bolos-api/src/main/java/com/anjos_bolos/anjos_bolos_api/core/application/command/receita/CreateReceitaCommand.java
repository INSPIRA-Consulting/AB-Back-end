package com.anjos_bolos.anjos_bolos_api.core.application.command.receita;

import java.util.List;

public record CreateReceitaCommand(
        String nome,
        List<Integer> ingredienteIds,
        List<Double> quantidades,
        List<String> unidadesMedida,
        Integer tipoReceitaId
) {
}
