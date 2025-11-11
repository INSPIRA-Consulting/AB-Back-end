package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido.ItemPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido.ItemPedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ItemPedidoEntity;

public class ItemPedidoEntityMapper {

    public static ItemPedidoResponseDTO toDTO(ItemPedido domain) {
        return new ItemPedidoResponseDTO(
                domain.getId(),
                domain.getPedido().getId(),
                domain.getProduto().getNome(),
                domain.getQuantidade(),
                domain.getPrecoUnitario(),
                domain.getCustoProducao(),
                domain.getPeso()
        );
    }

    public static CreateItemPedidoCommand toCommand(ItemPedidoRequestDTO dto) {
        return new CreateItemPedidoCommand(
                dto.pedidoId(),
                dto.produtoId(),
                dto.quantidade(),
                dto.peso()
        );
    }

    public static UpdateItemPedidoCommand toCommand(Integer id, ItemPedidoRequestDTO dto) {
        return new UpdateItemPedidoCommand(
                id,
                dto.pedidoId(),
                dto.produtoId(),
                dto.quantidade(),
                dto.peso()
        );
    }

    public static DeleteItemPedidoCommand toCommand(Integer id) {
        return new DeleteItemPedidoCommand(id);
    }

    public static GetItemPedidoByIdQuery toGetItemPedidoByIdQuery(Integer id) {
        return new GetItemPedidoByIdQuery(id);
    }

    public static ListItensPedidoByPedidoIdQuery toListItensPedidoByPedidoIdQuery(Integer pedidoId) {
        return new ListItensPedidoByPedidoIdQuery(pedidoId);
    }

    public static ItemPedidoEntity toEntity(ItemPedido domain) {
        return new ItemPedidoEntity(
                domain.getId(),
                PedidoEntityMapper.toEntity(domain.getPedido()),
                ProdutoEntityMapper.toEntity(domain.getProduto()),
                domain.getQuantidade(),
                domain.getPrecoUnitario(),
                domain.getCustoProducao(),
                domain.getPeso()
        );
    }

    public static ItemPedido toDomain(ItemPedidoEntity entity) {
        return new ItemPedido(
                entity.getId(),
                PedidoEntityMapper.toDomain(entity.getPedido()),
                ProdutoEntityMapper.toDomain(entity.getProduto()),
                entity.getQuantidade(),
                entity.getPrecoUnitario(),
                entity.getCustoProducao(),
                entity.getPeso()
        );
    }

}