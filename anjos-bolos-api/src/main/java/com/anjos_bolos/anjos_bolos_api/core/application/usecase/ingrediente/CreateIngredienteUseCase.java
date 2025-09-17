package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.CreateIngredienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

public class CreateIngredienteUseCase {
    private final IngredienteGateway gateway;

    public CreateIngredienteUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public Ingrediente execute(CreateIngredienteCommand command) {
        if (gateway.existsByNome(command.nome())) {
            throw new EntityAlreadyExistsException("Já existe um Ingrediente com este nome.");
        }

        Ingrediente ingrediente = new Ingrediente(command.nome(), command.valorEmbalagem(), command.quantidadeEmbalagem());

        return gateway.save(ingrediente);
    }
}
