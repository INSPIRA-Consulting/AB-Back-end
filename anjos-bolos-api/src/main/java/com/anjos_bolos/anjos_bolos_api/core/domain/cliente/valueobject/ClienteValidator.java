package com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class ClienteValidator {
    private final ClienteUniquenessChecker checker;

    public ClienteValidator(ClienteUniquenessChecker checker) {
        this.checker = checker;
    }

    public void validateUniqueness(CPF cpf, Telefone telefone) {
        if (checker.existsByCpf(cpf)) {
            throw new EntityAlreadyExistsException("Cliente com CPF %s já existe.".formatted(cpf));
        }
        if (checker.existsByTelefone(telefone)) {
            throw new EntityAlreadyExistsException("Cliente com Telefone %s já existe.".formatted(telefone));
        }
    }

    public void validateUniqueness(Integer id, CPF cpf, Telefone telefone) {
        if (checker.existsByCpfExceptId(cpf, id)) {
            throw new EntityAlreadyExistsException("Outro Cliente já está cadastrado com o CPF %s.".formatted(cpf));
        }
        if (checker.existsByTelefoneExcept(telefone, id)) {
            throw new EntityAlreadyExistsException("Outro Cliente já está cadastrado com o Telefone %s.".formatted(telefone));
        }
    }
}
