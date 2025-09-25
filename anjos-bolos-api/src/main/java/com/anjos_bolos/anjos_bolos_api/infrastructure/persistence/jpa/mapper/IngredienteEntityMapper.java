package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.ingrediente.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;

public class IngredienteEntityMapper {

    public static IngredienteResponseDTO toDTO(Ingrediente domain) {
        return new IngredienteResponseDTO(
                domain.getId(),
                domain.getNome(),
                domain.getCustoMedida()
        );
    }

    public static CreateIngredienteCommand toCommand(IngredienteRequestDTO dto) {
        return new CreateIngredienteCommand(
                dto.nome(),
                dto.valorEmbalagem(),
                dto.quantidadeEmbalagem()
        );
    }

    public static UpdateIngredienteCommand toCommand(Integer id, IngredienteRequestDTO dto) {
        return new UpdateIngredienteCommand(
                id,
                dto.nome(),
                dto.valorEmbalagem(),
                dto.quantidadeEmbalagem()
        );
    }

    public static DeleteIngredienteCommand toCommand(Integer id) {
        return new DeleteIngredienteCommand(id);
    }

    public static GetIngredienteByIdQuery toGetIngredienteByIdQuery(Integer id) {
        return new GetIngredienteByIdQuery(id);
    }

    public static ListIngredientesByNomeQuery toListIngredientesByNomeQuery(String nome) {
        return new ListIngredientesByNomeQuery(nome);
    }

    public static IngredienteEntity toEntity(Ingrediente domain) {
        return new IngredienteEntity(
                domain.getId(),
                domain.getNome(),
                domain.getCustoMedida()
        );
    }

    public static Ingrediente toDomain(IngredienteEntity entity) {
        return new Ingrediente(
                entity.getId(),
                entity.getNome(),
                entity.getCustoMedida()
        );
    }
}
