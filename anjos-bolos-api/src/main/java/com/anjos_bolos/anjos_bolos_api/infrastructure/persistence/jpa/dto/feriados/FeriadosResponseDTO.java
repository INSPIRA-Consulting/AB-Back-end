package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record FeriadosResponseDTO(
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        String nome,
        String tipo,
        String descricao,
        String uf,
        Integer codigo_ibge
) {}