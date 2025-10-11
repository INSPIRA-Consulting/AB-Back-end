package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita;

import java.util.List;

public record ReceitaResponseDTO(
        Integer id,
        String nome,
        List<ItemReceitaResponseDTO> ingredientes,
        String tipoReceita
) {}