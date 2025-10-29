package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard;

public record ProdutoVendidoResponseDTO(
        String nomeProduto,
        Long quantidadeVendida,
        String categoriaProduto
) {}