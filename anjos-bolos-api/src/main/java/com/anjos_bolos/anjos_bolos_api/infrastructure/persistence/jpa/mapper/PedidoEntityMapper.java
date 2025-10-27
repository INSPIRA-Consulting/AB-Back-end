package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.PedidoEntity;

import java.time.LocalDateTime;

public class PedidoEntityMapper {

    public static PedidoResponseDTO toDTO(Pedido domain) {
        return new PedidoResponseDTO(
                domain.getId(),
                domain.getDataPedido(),
                domain.getDataRetirada(),
                domain.getDataPagamento(),
                domain.getFormaPagamento().getFormaPagamento(),
                domain.getStatus().toString(),
                domain.getObservacao(),
                domain.getUsuario().getNome(),
                domain.getCliente().getNome()
        );
    }

    public static CreatePedidoCommand toCommand(PedidoRequestDTO dto) {
        return new CreatePedidoCommand(
                dto.dataPedido(),
                dto.dataRetirada(),
                dto.dataPagamento(),
                dto.formaPagamento(),
                dto.status(),
                dto.observacao(),
                dto.usuarioId(),
                dto.clienteId()
        );
    }

    public static UpdatePedidoCommand toCommand(Integer id, PedidoRequestDTO dto) {
        return new UpdatePedidoCommand(
                id,
                dto.dataPedido(),
                dto.dataRetirada(),
                dto.dataPagamento(),
                dto.formaPagamento(),
                dto.status(),
                dto.observacao(),
                dto.usuarioId(),
                dto.clienteId()
        );
    }

    public static DeletePedidoCommand toCommand(Integer id) {
        return new DeletePedidoCommand(id);
    }

    public static GetPedidoByIdQuery toGetPedidoByIdQuery(Integer id) {
        return new GetPedidoByIdQuery(id);
    }

    public static ListPedidosByClienteIdQuery toListPedidosByClienteIdQuery(Integer clienteId) {
        return new ListPedidosByClienteIdQuery(clienteId);
    }

    public static ListPedidosByClienteCpfQuery toListPedidosByClienteCpfQuery(String clienteCpf) {
        return new ListPedidosByClienteCpfQuery(clienteCpf);
    }

    public static ListPedidosByDataPedidoQuery toListPedidosByDataPedidoQuery(LocalDateTime dataPedido) {
        return new ListPedidosByDataPedidoQuery(dataPedido);
    }

    public static ListPedidosByDataRetiradaQuery toListPedidosByDataRetiradaQuery(LocalDateTime dataPedido,
                                                                                  LocalDateTime dataRetirada) {
        return new ListPedidosByDataRetiradaQuery(dataPedido, dataRetirada);
    }

    public static ListPedidosByDataPagamentoQuery toListPedidosByDataPagamentoQuery(LocalDateTime dataPagamento) {
        return new ListPedidosByDataPagamentoQuery(dataPagamento);
    }

    public static ListPedidosByFormaPagamentoQuery toListPedidosByFormaPagamentoQuery(String formaPagamento) {
        return new ListPedidosByFormaPagamentoQuery(formaPagamento);
    }

    public static ListPedidosByStatusQuery toListPedidosByStatusQuery(String status) {
        return new ListPedidosByStatusQuery(status);
    }

    public static PedidoEntity toEntity(Pedido domain) {
        return new PedidoEntity(
                domain.getId(),
                domain.getDataPedido(),
                domain.getDataRetirada(),
                domain.getDataPagamento(),
                domain.getFormaPagamento().toString(),
                domain.getStatus().toString(),
                domain.getObservacao(),
                UsuarioEntityMapper.toEntity(domain.getUsuario()),
                ClienteEntityMapper.toEntity(domain.getCliente())
        );
    }

    public static Pedido toDomain(PedidoEntity entity) {
        return new Pedido(
                entity.getId(),
                entity.getDataPedido(),
                entity.getDataRetirada(),
                entity.getDataPagamento(),
                FormaPagamentoEnum.valueOf(entity.getFormaPagamento()),
                StatusPedidoEnum.valueOf(entity.getStatus()),
                entity.getObservacao(),
                UsuarioEntityMapper.toDomain(entity.getUsuario()),
                ClienteEntityMapper.toDomain(entity.getCliente())
        );
    }

}