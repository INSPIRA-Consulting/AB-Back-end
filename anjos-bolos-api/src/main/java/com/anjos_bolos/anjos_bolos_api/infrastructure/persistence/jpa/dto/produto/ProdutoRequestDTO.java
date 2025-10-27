package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto;

public record ProdutoRequestDTO(
        String nome,
        Double precoFinal,
        Double custoProducao,
        Integer categoriaProdutoId
) {}