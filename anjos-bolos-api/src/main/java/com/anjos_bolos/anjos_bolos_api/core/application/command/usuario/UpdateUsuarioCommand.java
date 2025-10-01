package com.anjos_bolos.anjos_bolos_api.core.application.command.usuario;

public record UpdateUsuarioCommand(
        Integer id,
        String nome,
        String cpf,
        String email,
        String senha,
        String telefone,
        String funcao
) {}