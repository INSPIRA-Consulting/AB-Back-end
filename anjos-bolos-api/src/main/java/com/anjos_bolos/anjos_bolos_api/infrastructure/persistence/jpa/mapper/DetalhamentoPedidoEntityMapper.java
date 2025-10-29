package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoReceitasResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.ItemDetalhamentoDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.DetalhamentoPedidoEntity;

import java.util.List;

public class DetalhamentoPedidoEntityMapper {

    public static DetalhamentoPedidoResponseDTO toDTO(DetalhamentoPedido domain) {
        return new DetalhamentoPedidoResponseDTO(
                domain.getItemPedido().getId(),
                domain.getReceita().getId(),
                domain.getReceita().getNome(),
                domain.getReceita().getTipoReceita().getNome(),
                domain.getObservacao()
        );
    }

    public static DetalhamentoPedidoReceitasResponseDTO toDTO(Integer itemPedidoId, List<DetalhamentoPedido> domain) {
        List<ItemDetalhamentoDTO> receitas = domain.stream()
                .map(detalhamento -> new ItemDetalhamentoDTO(
                        detalhamento.getReceita().getNome(),
                        detalhamento.getReceita().getTipoReceita().getNome(),
                        detalhamento.getObservacao()
                ))
                .toList();

        return new DetalhamentoPedidoReceitasResponseDTO(
                itemPedidoId,
                receitas
        );
    }

    public static CreateDetalhamentoPedidoCommand toCommand(DetalhamentoPedidoRequestDTO dto) {
        return new CreateDetalhamentoPedidoCommand(
                dto.fkItemPedido(),
                dto.fkReceita(),
                dto.observacao()
        );
    }

    public static UpdateDetalhamentoPedidoCommand toCommand(Integer id, DetalhamentoPedidoRequestDTO  dto) {
        return new UpdateDetalhamentoPedidoCommand(
                id,
                dto.fkItemPedido(),
                dto.fkReceita(),
                dto.observacao()
        );
    }

    public static DeleteDetalhamentoPedidoCommand toCommand(Integer id) {
        return new DeleteDetalhamentoPedidoCommand(id);
    }

    public static GetDetalhamentoPedidoByIdQuery toGetDetalhamentoPedidoByIdQuery(Integer id) {
        return new GetDetalhamentoPedidoByIdQuery(id);
    }

    public static ListDetalhamentoPedidoByItemPedidoIdQuery toListDetalhamentoPedidoByItemPedidoIdQuery(Integer itemPedidoId) {
        return new ListDetalhamentoPedidoByItemPedidoIdQuery(itemPedidoId);
    }

    public static DetalhamentoPedidoEntity toEntity(DetalhamentoPedido domain) {
        return new DetalhamentoPedidoEntity(
                domain.getId(),
                ItemPedidoEntityMapper.toEntity(domain.getItemPedido()),
                ReceitaEntityMapper.toEntity(domain.getReceita()),
                domain.getObservacao()
        );
    }

    public static DetalhamentoPedido toDomain(DetalhamentoPedidoEntity entity) {
        return new DetalhamentoPedido(
                entity.getId(),
                ItemPedidoEntityMapper.toDomain(entity.getItemPedido()),
                ReceitaEntityMapper.toDomain(entity.getReceita()),
                entity.getObservacao()
        );
    }

}