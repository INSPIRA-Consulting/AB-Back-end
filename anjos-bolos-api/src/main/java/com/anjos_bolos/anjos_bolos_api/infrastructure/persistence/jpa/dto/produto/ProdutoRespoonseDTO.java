package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto;

public record ProdutoRespoonseDTO(
        Integer id,
        String nome,
        Double precoFinal,
        String categoriaProduto
) {
}
