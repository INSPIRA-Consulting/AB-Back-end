package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.categoria_produto.CategoriaProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.categoria_produto.CategoriaProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.CategoriaProdutoEntity;

public class CategoriaProdutoEntityMapper {

    public static CategoriaProdutoResponseDTO toDTO(CategoriaProduto domain) {
        return new CategoriaProdutoResponseDTO(
                domain.getId(),
                domain.getNome(),
                domain.getDescricao()
        );
    }

    public static CreateCategoriaProdutoCommand toCommand(CategoriaProdutoRequestDTO dto) {
        return new CreateCategoriaProdutoCommand(
                dto.nome(),
                dto.descricao()
        );
    }

    public static UpdateCategoriaProdutoCommand toCommand(Integer id, CategoriaProdutoRequestDTO dto) {
        return new UpdateCategoriaProdutoCommand(
                id,
                dto.nome(),
                dto.descricao()
        );
    }

    public static DeleteCategoriaProdutoCommand toCommand(Integer id) {
        return new DeleteCategoriaProdutoCommand(id);
    }

    public static GetCategoriaProdutoByIdQuery toQuery(Integer id) {
        return new GetCategoriaProdutoByIdQuery(id);
    }

    public static ListCategoriasProdutoByNomeQuery toQuery(String nome) {
        return new ListCategoriasProdutoByNomeQuery(nome);
    }

    public static CategoriaProdutoEntity toEntity(CategoriaProduto domain) {
        return new CategoriaProdutoEntity(
                domain.getId(),
                domain.getNome(),
                domain.getDescricao()
        );
    }

    public static CategoriaProduto toDomain(CategoriaProdutoEntity entity) {
        return new CategoriaProduto(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao()
        );
    }
}
