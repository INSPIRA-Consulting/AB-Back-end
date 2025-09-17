package com.anjos_bolos.anjos_bolos_api.core.application.command.cliente;

public record UpdateClienteCommand(
        Integer id,
        String cpf,
        String nome,
        String telefone
) {
}
