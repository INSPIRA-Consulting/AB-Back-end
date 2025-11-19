package com.anjos_bolos.anjos_bolos_api.core.domain.feriados;

import java.time.LocalDate;

public record FeriadosDTO(
        LocalDate data,
        String nome,
        String tipo,
        String descricao,
        String uf,
        String municipio
) {}