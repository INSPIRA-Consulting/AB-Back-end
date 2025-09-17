package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO(
        String email,
        String senha
) {}
