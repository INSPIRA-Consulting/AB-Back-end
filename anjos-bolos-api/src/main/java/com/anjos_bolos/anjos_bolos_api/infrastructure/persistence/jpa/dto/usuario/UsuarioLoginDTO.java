package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

public record UsuarioLoginDTO(
        String email,
        String senha
) {}