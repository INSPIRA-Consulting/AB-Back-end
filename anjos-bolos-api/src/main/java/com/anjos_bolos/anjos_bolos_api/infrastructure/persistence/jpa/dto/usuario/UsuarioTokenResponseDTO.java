package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

public record UsuarioTokenResponseDTO(
        Integer id,
        String nome,
        String email,
        String funcao,
        String token
) {}