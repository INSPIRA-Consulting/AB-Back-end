package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        String telefone
) {}