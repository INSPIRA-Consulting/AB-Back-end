package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

public record UsuarioLoginResponseDTO(
        String nome,
        String funcao
) {}