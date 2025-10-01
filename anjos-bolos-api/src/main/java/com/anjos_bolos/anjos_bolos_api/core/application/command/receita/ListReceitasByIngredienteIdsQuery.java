package com.anjos_bolos.anjos_bolos_api.core.application.command.receita;

import java.util.List;

public record ListReceitasByIngredienteIdsQuery(List<Integer> ingredienteIds) {}