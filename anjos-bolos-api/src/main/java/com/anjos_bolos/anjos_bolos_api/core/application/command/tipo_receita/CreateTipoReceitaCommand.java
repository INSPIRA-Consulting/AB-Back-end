package com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita;

public record CreateTipoReceitaCommand(
        String nome,
        String descricao
) {}