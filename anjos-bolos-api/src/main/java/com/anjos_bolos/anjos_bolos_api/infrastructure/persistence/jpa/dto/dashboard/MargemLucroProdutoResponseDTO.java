package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

public record MargemLucroProdutoResponseDTO(
        String nomeProduto,
        Double margemLucro
) {}