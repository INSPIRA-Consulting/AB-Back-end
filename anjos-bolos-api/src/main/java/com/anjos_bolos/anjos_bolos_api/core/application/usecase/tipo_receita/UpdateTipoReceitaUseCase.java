package com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.UpdateTipoReceitaCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

public class UpdateTipoReceitaUseCase {
    private final TipoReceitaGateway gateway;

    public UpdateTipoReceitaUseCase(TipoReceitaGateway gateway) {
        this.gateway = gateway;
    }

    public TipoReceita execute(UpdateTipoReceitaCommand command) {
        if (gateway.existsById(command.id())) {
            throw new NotFoundException("Tipo de Receita com ID [%d] não encontrado.".formatted(command.id()));
        }

        TipoReceita tipoReceita = gateway.findById(command.id());
        tipoReceita.setNome(command.nome());
        tipoReceita.setDescricao(command.descricao());

        return gateway.update(tipoReceita);
    }
}
