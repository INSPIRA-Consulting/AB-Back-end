package com.anjos_bolos.anjos_bolos_api.core.application.command.receita;

import java.util.List;

public record UpdateReceitaCommand(
        Integer id,
        String nome,
        List<ItemReceitaCommand> ingredientes,
        Integer tipoReceitaId
) {}