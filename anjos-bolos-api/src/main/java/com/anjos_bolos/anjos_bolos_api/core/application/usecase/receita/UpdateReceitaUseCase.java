package com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.ItemReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.UpdateReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.DuplicateKeyException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.ItemReceita;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.UnidadeMedidaEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.List;
import java.util.stream.IntStream;

public class UpdateReceitaUseCase {

    private final ReceitaGateway gateway;
    private final IngredienteGateway ingredienteGateway;
    private final TipoReceitaGateway tipoReceitaGateway;


    public UpdateReceitaUseCase(ReceitaGateway gateway, IngredienteGateway ingredienteGateway, TipoReceitaGateway tipoReceitaGateway) {
        this.gateway = gateway;
        this.ingredienteGateway = ingredienteGateway;
        this.tipoReceitaGateway = tipoReceitaGateway;
    }

    public Receita execute(UpdateReceitaCommand command) {
        if (gateway.existsByNomeAndIdNot(command.nome(), command.id())) {
            throw new EntityAlreadyExistsException("Já existe uma Receita com o nome: '%s'."
                    .formatted(command.nome()));
        }

        List<Integer> ingredienteIds = command.ingredientes()
                .stream()
                .map(ItemReceitaCommand::ingredienteId)
                .toList();

        if (gateway.existsByIngredientesIdsAndIdNot(ingredienteIds, command.id())) {
            throw new EntityAlreadyExistsException("Já existe uma Receita com os mesmos Ingredientes.");
        }

        long totalIngredientes = ingredienteIds.size();
        long ingredientesUnicos = ingredienteIds
                .stream()
                .distinct()
                .count();

        if (totalIngredientes != ingredientesUnicos) {
            throw new DuplicateKeyException("A Receita não pode conter Ingredientes duplicados.");
        }

        List<ItemReceita> ingredientes = IntStream.range(0, ingredienteIds.size())
                .mapToObj(i -> {
                    Ingrediente ingrediente = ingredienteGateway.findById(ingredienteIds.get(i));
                    if (ingrediente == null) {
                        throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(ingredienteIds.get(i)));
                    }
                    return new ItemReceita(
                            ingrediente,
                            command.ingredientes().get(i).quantidade(),
                            UnidadeMedidaEnum.from(command.ingredientes().get(i).unidadeMedida())
                    );
                })
                .toList();

        TipoReceita tipoReceita = tipoReceitaGateway.findById(command.tipoReceitaId());

        Receita receita = new Receita(
                command.id(),
                command.nome(),
                ingredientes,
                tipoReceita
        );

        return gateway.update(receita);
    }

}