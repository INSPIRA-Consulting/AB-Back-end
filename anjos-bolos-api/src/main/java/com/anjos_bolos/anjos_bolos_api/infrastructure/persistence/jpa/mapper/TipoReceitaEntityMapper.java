package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.tipo_receita.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.tipo_receita.TipoReceitaRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.tipo_receita.TipoReceitaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.TipoReceitaEntity;

public class TipoReceitaEntityMapper {

    public static TipoReceitaResponseDTO toDTO(TipoReceita domain) {
        return new TipoReceitaResponseDTO(
                domain.getId(),
                domain.getNome().toUpperCase(),
                domain.getDescricao()
        );
    }

    public static CreateTipoReceitaCommand toCommand(TipoReceitaRequestDTO dto) {
        return new CreateTipoReceitaCommand(
                dto.nome().toUpperCase(),
                dto.descricao()
        );
    }

    public static UpdateTipoReceitaCommand toCommand(Integer id, TipoReceitaRequestDTO dto) {
        return new UpdateTipoReceitaCommand(
                id,
                dto.nome().toUpperCase(),
                dto.descricao()
        );
    }

    public static DeleteTipoReceitaCommand toCommand(Integer id) {
        return new DeleteTipoReceitaCommand(id);
    }

    public static GetTipoReceitaByIdQuery toGetTipoReceitaByIdQuery(Integer id) {
        return new GetTipoReceitaByIdQuery(id);
    }

    public static ListTiposReceitaByNomeQuery toGetTipoReceitaByNomeQuery(String nome) {
        return new ListTiposReceitaByNomeQuery(nome);
    }

    public static TipoReceitaEntity toEntity(TipoReceita domain) {
        return new TipoReceitaEntity(
                domain.getId(),
                domain.getNome().toUpperCase(),
                domain.getDescricao()
        );
    }

    public static TipoReceita toDomain(TipoReceitaEntity entity) {
        return new TipoReceita(
                entity.getId(),
                entity.getNome().toUpperCase(),
                entity.getDescricao()
        );
    }

}
