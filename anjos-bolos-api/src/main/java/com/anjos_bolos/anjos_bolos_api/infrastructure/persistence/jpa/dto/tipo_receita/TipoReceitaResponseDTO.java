package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.tipo_receita;

public record TipoReceitaResponseDTO(
        Integer id,
        String nome,
        String descricao
) {}