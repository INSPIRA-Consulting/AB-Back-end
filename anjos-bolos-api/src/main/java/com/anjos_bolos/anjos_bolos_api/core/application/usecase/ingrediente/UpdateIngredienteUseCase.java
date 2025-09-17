package com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.UpdateIngredienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;

public class UpdateIngredienteUseCase {
    private final IngredienteGateway gateway;

    public UpdateIngredienteUseCase(IngredienteGateway gateway) {
        this.gateway = gateway;
    }

    public Ingrediente execute(UpdateIngredienteCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado".formatted(command.id()));
        }

        Ingrediente ingrediente = gateway.findById(command.id());
        ingrediente.setNome(command.nome());

        Double custoMedida = ingrediente.calcularCustoMedida(command.valorEmbalagem(), command.quantidadeEmbalagem());
        ingrediente.setCustoMedida(custoMedida);

        return gateway.update(ingrediente);
    }
}
