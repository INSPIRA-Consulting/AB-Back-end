package com.anjos_bolos.anjos_bolos_api.core.application.command.cliente;

public record CreateClienteCommand(
        String cpf,
        String nome,
        String telefone
) {
}
