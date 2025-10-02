package com.anjos_bolos.anjos_bolos_api.core.application.command.cliente;

public record UpdateClienteCommand(
        Integer id,
        String nome,
        String cpf,
        String telefone
) {}