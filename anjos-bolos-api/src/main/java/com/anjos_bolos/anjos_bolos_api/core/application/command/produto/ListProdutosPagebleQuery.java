package com.anjos_bolos.anjos_bolos_api.core.application.command.produto;

import org.springframework.data.domain.Pageable;

public record ListProdutosPagebleQuery(Pageable pageable) {
}
