package com.anjos_bolos.anjos_bolos_api.core.domain.dashboard;

public record ProdutoVendidoDTO(
        String nomeProduto,
        Long quantidadeVendida,
        String categoriaProduto
) {}