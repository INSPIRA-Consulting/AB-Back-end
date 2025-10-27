package com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente;

import org.springframework.data.domain.Pageable;

public record ListIngredientesPageableQuery(Pageable pageable) {
}