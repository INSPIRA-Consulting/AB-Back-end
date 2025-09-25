package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.categoria_produto;

public record CategoriaProdutoResponseDTO(
    Integer id,
    String nome,
    String descricao
) {
}
