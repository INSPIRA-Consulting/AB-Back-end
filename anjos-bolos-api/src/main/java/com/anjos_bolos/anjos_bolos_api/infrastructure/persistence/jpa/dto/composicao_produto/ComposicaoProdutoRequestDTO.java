package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto;

import java.util.List;

public record ComposicaoProdutoRequestDTO(
    Integer produtoId,
    List<ItemComposicaoRequestDTO> receitas
) {}