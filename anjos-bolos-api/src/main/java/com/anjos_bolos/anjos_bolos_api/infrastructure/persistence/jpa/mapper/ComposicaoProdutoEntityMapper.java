package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.valueobject.ItemComposicao;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ComposicaoProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ComposicaoProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ItemComposicaoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.composicao_produto.ItemComposicaoResponsetDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto.ComposicaoProdutoEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComposicaoProdutoEntityMapper {

    public static ComposicaoProdutoResponseDTO toDTO(ComposicaoProduto domain) {
        Map<Integer, ItemComposicaoResponsetDTO> receitasMap = domain.getReceitas()
                .stream()
                .collect(Collectors.toMap(
                        itemComposicao -> itemComposicao.getReceita().getId(),
                        itemComposicao -> new ItemComposicaoResponsetDTO(
                                itemComposicao.getReceita().getId(),
                                itemComposicao.getReceita().getNome(),
                                itemComposicao.getReceita().getTipoReceita().getNome(),
                                itemComposicao.getQuantidade(),
                                itemComposicao.getObservacao()
                        ),
                        (existing, replacement) -> existing
                ));

        List<ItemComposicaoResponsetDTO> receitas = new java.util.ArrayList<>(receitasMap.values());

        return new ComposicaoProdutoResponseDTO(
                domain.getProduto().getId(),
                receitas
        );
    }

    public static List<ItemComposicaoCommand> toCommand(List<ItemComposicaoRequestDTO> dto) {
        return dto.stream()
                .map(item -> new ItemComposicaoCommand(
                        item.receitaId(),
                        item.quantidade(),
                        item.observacao()
                ))
                .toList();
    }

    public static CreateComposicaoProdutoCommand toCommand(ComposicaoProdutoRequestDTO dto) {
        return new CreateComposicaoProdutoCommand(
                dto.produtoId(),
                toCommand(dto.receitas())
        );
    }

    public static UpdateComposicaoProdutoCommand toCommand(Integer id, ComposicaoProdutoRequestDTO dto) {
        return new UpdateComposicaoProdutoCommand(
                id,
                toCommand(dto.receitas())
        );
    }

    public static DeleteComposicaoProdutoCommand toCommand(Integer id) {
        return new DeleteComposicaoProdutoCommand(id);
    }

    public static ListComposicoesProdutoByProdutoIdQuery toListComposicoesProdutoByProdutoIdQuery(Integer produtoId) {
        return new ListComposicoesProdutoByProdutoIdQuery(produtoId);
    }

    public static List<ComposicaoProdutoEntity> toEntityList(ComposicaoProduto domain) {
        return domain.getReceitas()
                .stream()
                .flatMap(itemComposicao ->
                        itemComposicao.getReceita().getIngredientes()
                                .stream()
                                .map(itemReceita -> new ComposicaoProdutoEntity(
                                        domain.getProduto().getId(),
                                        itemComposicao.getReceita().getId(),
                                        itemReceita.getIngrediente().getId(),
                                        ProdutoEntityMapper.toEntity(domain.getProduto()),
                                        ReceitaEntityMapper.toEntity(itemComposicao.getReceita()),
                                        itemComposicao.getQuantidade(),
                                        itemComposicao.getObservacao()
                                ))
                )
                .toList();
    }

    public static ComposicaoProduto toDomain(List<ComposicaoProdutoEntity> entities) {
        List<ItemComposicao> receitas = entities
                .stream()
                .map(entity -> new ItemComposicao(
                        ReceitaEntityMapper.toDomain(entity.getReceita()),
                        entity.getQuantidade(),
                        entity.getObservacao()
                ))
                .toList();

        return new ComposicaoProduto(
                ProdutoEntityMapper.toDomain(entities.getFirst().getProduto()),
                receitas
        );
    }

}
