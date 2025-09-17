package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.CreateReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.ItemReceita;

import java.util.List;

public class CreateReceitaUseCase {
    private final ReceitaGateway gateway;
    private final IngredienteGateway ingredienteGateway;

    public CreateReceitaUseCase(ReceitaGateway gateway, IngredienteGateway ingredienteGateway) {
        this.gateway = gateway;
        this.ingredienteGateway = ingredienteGateway;
    }

//    public Receita execute(CreateReceitaCommand command) {
//        if (gateway.existsByNome(command.nome())) {
//            throw new EntityAlreadyExistsException("Já existe uma Receita com o nome: %s"
//                    .formatted(command.nome()));
//        }
//
//        if (gateway.existsByIngredientesIds(command.ingredienteIds())) {
//            throw new EntityAlreadyExistsException("Já existe uma Receita com os mesmos ingredientes");
//        }
//
//        List<ItemReceita> ingredientes = command.ingredienteIds()
//                .stream()
//                .map(ingredienteId -> {
//                    Ingrediente ingrediente = ingredienteGateway.findById(ingredienteId);
//                    if (ingrediente == null) {
//                        throw new NotFoundException("Ingrediente com ID [%d] não encontrado."
//                                .formatted(ingredienteId));
//                    }
//                    return new ItemReceita(ingrediente);
//
//        Receita receita = new Receita()
//    }
}
