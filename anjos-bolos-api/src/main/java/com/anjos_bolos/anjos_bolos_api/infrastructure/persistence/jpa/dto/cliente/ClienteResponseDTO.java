package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente;

public record ClienteResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String telefone
) {}