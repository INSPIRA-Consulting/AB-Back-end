package com.anjos_bolos.anjos_bolos_api.core.application.command.dashboard;

import com.anjos_bolos.anjos_bolos_api.core.domain.feriados.FeriadosDTO;

import java.util.List;

public record ListProdutosRecomendadosFeriadosQuery(
        List<FeriadosDTO> feriados
) {}