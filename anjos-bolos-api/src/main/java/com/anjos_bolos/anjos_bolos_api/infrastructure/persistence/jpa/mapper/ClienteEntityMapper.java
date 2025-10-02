package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente.ClienteRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente.ClienteResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ClienteEntity;

public class ClienteEntityMapper {

    public static ClienteResponseDTO toDTO(Cliente domain) {
        return new ClienteResponseDTO(
                domain.getId(),
                domain.getNome(),
                domain.getCpf().toString(),
                domain.getTelefone().toString()
        );
    }

    public static CreateClienteCommand toCommand(ClienteRequestDTO dto) {
        return new CreateClienteCommand(
                dto.nome(),
                dto.cpf(),
                dto.telefone()
        );
    }

    public static UpdateClienteCommand toCommand(Integer id, ClienteRequestDTO dto) {
        return new UpdateClienteCommand(
                id,
                dto.nome(),
                dto.cpf(),
                dto.telefone()
        );
    }

    public static DeleteClienteCommand toCommand(Integer id) {
        return new DeleteClienteCommand(id);
    }

    public static GetClienteByIdQuery toGetClienteByIdQuery(Integer id) {
        return new GetClienteByIdQuery(id);
    }

    public static ListClientesByNomeQuery toListClientesByNomeQuery(String nome) {
        return new ListClientesByNomeQuery(nome);
    }

    public static GetClienteByCpfQuery toGetClienteByCpfQuery(String cpf) {
        return new GetClienteByCpfQuery(cpf);
    }

    public static ClienteEntity toEntity(Cliente domain) {
        return new ClienteEntity(
                domain.getId(),
                domain.getNome(),
                domain.getCpf().toString(),
                domain.getTelefone().toString()
        );
    }

    public static Cliente toDomain(ClienteEntity entity) {
        return new Cliente(
                entity.getId(),
                entity.getNome(),
                CPF.of(entity.getCpf()),
                Telefone.of(entity.getTelefone())
        );
    }

}