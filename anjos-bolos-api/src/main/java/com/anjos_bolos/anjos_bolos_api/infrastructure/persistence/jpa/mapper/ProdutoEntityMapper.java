package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRespoonseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ProdutoEntity;

public class ProdutoEntityMapper {

    public static ProdutoRespoonseDTO toDTO(Produto domain) {
        return new ProdutoRespoonseDTO(
                domain.getId(),
                domain.getNome(),
                domain.getPrecoFinal(),
                domain.getCategoriaProduto().getNome()
        );
    }

    public static CreateProdutoCommand toCommand(ProdutoRequestDTO dto) {
        return new CreateProdutoCommand(
                dto.nome(),
                dto.precoFinal(),
                dto.categoriaProdutoId()
        );
    }

    public static UpdateProdutoCommand toCommand(Integer id, ProdutoRequestDTO dto) {
        return new UpdateProdutoCommand(
                id,
                dto.nome(),
                dto.precoFinal(),
                dto.categoriaProdutoId()
        );
    }

    public static DeleteProdutoCommand toCommand(Integer id) {
        return new DeleteProdutoCommand(id);
    }

    public static GetProdutoByIdQuery toGetProdutoByIdQuery(Integer id) {
        return new GetProdutoByIdQuery(id);
    }

    public static ListProdutosByNomeQuery toListProdutosByNomeQuery(String nome) {
        return new ListProdutosByNomeQuery(nome);
    }

    public static ListProdutosByCategoriaProdutoIdQuery toListProdutosByCategoriaProdutoIdQuery(Integer idCategoriaProduto) {
        return new ListProdutosByCategoriaProdutoIdQuery(idCategoriaProduto);
    }

    public static ProdutoEntity toEntity(Produto domain) {
        return new ProdutoEntity(
                domain.getId(),
                domain.getNome(),
                domain.getPrecoFinal(),
                CategoriaProdutoEntityMapper.toEntity(domain.getCategoriaProduto())
        );
    }

    public static Produto toDomain(ProdutoEntity entity) {
        return new Produto(
                entity.getId(),
                entity.getNome(),
                entity.getPrecoFinal(),
                CategoriaProdutoEntityMapper.toDomain(entity.getCategoriaProduto())
        );
    }
}
