package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

public record UsuarioResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String email,
        String senha,
        String telefone,
        String funcao
) {}