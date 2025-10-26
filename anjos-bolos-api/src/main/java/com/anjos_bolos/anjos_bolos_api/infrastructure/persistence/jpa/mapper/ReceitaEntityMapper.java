package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.receita.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.ItemReceita;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.UnidadeMedidaEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ItemReceitaRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ItemReceitaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ReceitaRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.receita.ReceitaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;

import javax.swing.*;
import java.util.List;

public class ReceitaEntityMapper {

    public static ReceitaResponseDTO toDTO(Receita domain) {
        List<ItemReceitaResponseDTO> ingredientes = domain.getIngredientes()
                .stream()
                .map(itemReceita -> new ItemReceitaResponseDTO(
                        itemReceita.getIngrediente().getId(),
                        itemReceita.getIngrediente().getNome(), itemReceita.getQuantidade(),
                        itemReceita.getQuantidade() < 1 || itemReceita.getQuantidade() >= 2
                                ? itemReceita.getUnidadeMedida().getPlural()
                                : itemReceita.getUnidadeMedida().getUnidadeMedida()
                ))
                .toList();

        return new ReceitaResponseDTO(
                domain.getId(),
                domain.getNome(),
                ingredientes,
                domain.getTipoReceita().getNome()
        );
    }

    public static List<ItemReceitaCommand> toCommand(List<ItemReceitaRequestDTO> dto) {
        return dto.stream()
                .map(item -> new ItemReceitaCommand(
                        item.ingredienteId(),
                        item.quantidade(),
                        item.unidadeMedida()
                ))
                .toList();
    }

    public static CreateReceitaCommand toCommand(ReceitaRequestDTO dto) {
        return new CreateReceitaCommand(
                dto.nome(),
                toCommand(dto.ingredientes()),
                dto.tipoReceitaId()
        );
    }

    public static UpdateReceitaCommand toCommand(Integer id, ReceitaRequestDTO dto) {
        return new UpdateReceitaCommand(
                id,
                dto.nome(),
                toCommand(dto.ingredientes()),
                dto.tipoReceitaId()
        );
    }

    public static DeleteReceitaCommand toCommand(Integer id) {
        return new DeleteReceitaCommand(id);
    }

    public static GetReceitaByIdQuery toGetReceitaByIdQuery(Integer id) {
        return new GetReceitaByIdQuery(id);
    }

    public static ListReceitasByNomeQuery toListReceitasByNomeQuery(String nome) {
        return new ListReceitasByNomeQuery(nome);
    }

    public static ListReceitasByIngredienteIdsQuery toListReceitasByIngredienteIdsQuery(List<Integer> ingredienteIds) {
        return new ListReceitasByIngredienteIdsQuery(ingredienteIds);
    }

    public static ListReceitasByTipoReceitaIdQuery toListReceitasByTipoReceitaIdQuery(Integer tipoReceitaId) {
        return new ListReceitasByTipoReceitaIdQuery(tipoReceitaId);
    }

    public static List<ReceitaEntity> toEntityList(Receita domain) {
        return domain.getIngredientes()
                .stream()
                .map(itemReceita -> new ReceitaEntity(
                       domain.getId(),
                       itemReceita.getIngrediente().getId(),
                       domain.getNome(),
                       IngredienteEntityMapper.toEntity(itemReceita.getIngrediente()),
                       itemReceita.getQuantidade(),
                       itemReceita.getUnidadeMedida().name(),
                       TipoReceitaEntityMapper.toEntity(domain.getTipoReceita())
               ))
                .toList();
    }

    public static ReceitaEntity toEntity(Receita domain) {
        List<ReceitaEntity> entities = toEntityList(domain);

        return entities.getFirst();
    }

    public static Receita toDomain(List<ReceitaEntity> entities) {
        ReceitaEntity first = entities.getFirst();

        List<ItemReceita> ingredientes = entities
                .stream()
                .map(entity -> new ItemReceita(
                        IngredienteEntityMapper.toDomain(entity.getIngrediente()),
                        entity.getQuantidade(),
                        UnidadeMedidaEnum.valueOf(entity.getUnidadeMedida())
                ))
                .toList();

        return new Receita(
                first.getId(),
                first.getNome(),
                ingredientes,
                TipoReceitaEntityMapper.toDomain(first.getTipoReceita())
        );
    }

    public static Receita toDomain(ReceitaEntity entity) {
        return toDomain(List.of(entity));
    }

}