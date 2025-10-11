package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita;

import java.util.List;

public record ReceitaRequestDTO(
        String nome,
        List<ItemReceitaRequestDTO> ingredientes,
        Integer tipoReceitaId
) {}