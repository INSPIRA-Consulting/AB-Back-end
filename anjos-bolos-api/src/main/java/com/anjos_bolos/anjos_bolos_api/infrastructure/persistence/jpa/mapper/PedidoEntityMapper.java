package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.FormaPagamentoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.StatusPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.PedidoEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PedidoEntityMapper {

    public static PedidoResponseDTO toDTO(Pedido domain) {
        return new PedidoResponseDTO(
                domain.getId(),
                domain.getDataPedido(),
                domain.getDataRetirada(),
                domain.getDataPagamento(),
                domain.getFormaPagamento().getFormaPagamento(),
                domain.getStatus().getStatus(),
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

    public static UpdateStatusPedidoCommand toCommand(Integer id, StatusPedidoRequestDTO dto) {
        return new UpdateStatusPedidoCommand(
                id,
                dto.dataRetirada(),
                dto.formaPagamento(),
                dto.status(),
                dto.observacao()
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

    public static ListPedidosByDataRetiradaQuery toListPedidosByDataRetiradaQuery(LocalDateTime dataRetiradaInicio, LocalDateTime dataRetiradaFim) {
        return new ListPedidosByDataRetiradaQuery(dataRetiradaInicio, dataRetiradaFim);
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
                domain.getFormaPagamento().name(),
                domain.getStatus().name(),
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
                FormaPagamentoEnum.from(entity.getFormaPagamento()),
                StatusPedidoEnum.from(entity.getStatus()),
                entity.getObservacao(),
                UsuarioEntityMapper.toDomain(entity.getUsuario()),
                ClienteEntityMapper.toDomain(entity.getCliente())
        );
    }

}