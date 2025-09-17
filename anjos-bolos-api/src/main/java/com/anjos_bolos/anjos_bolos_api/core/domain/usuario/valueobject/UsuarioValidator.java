package com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class UsuarioValidator {
    private final UsuarioUniquenessChecker checker;

    public UsuarioValidator(UsuarioUniquenessChecker checker) {
        this.checker = checker;
    }

    public void validateUniqueness(CPF cpf, Email email, Telefone telefone) {
        if (checker.existsByCpf(cpf)) {
            throw new EntityAlreadyExistsException("Usuário com CPF %s já existe.".formatted(cpf));
        }

        if (checker.existsByEmail(email)) {
            throw new EntityAlreadyExistsException("Usuário com Email %s já existe.".formatted(email));
        }

        if (checker.existsByTelefone(telefone)) {
            throw new EntityAlreadyExistsException("Usuário com Telefone %s já existe.".formatted(telefone));
        }
    }

    public void validateUniqueness(Integer id, CPF cpf, Email email, Telefone telefone) {
        if (checker.existsByCpfAndIdNot(cpf, id)) {
            throw new EntityAlreadyExistsException("Usuário com CPF %s já existe.".formatted(cpf));
        }

        if (checker.existsByEmailAndIdNot(email, id)) {
            throw new EntityAlreadyExistsException("Usuário com Email %s já existe.".formatted(email));
        }

        if (checker.existsByTelefoneAndIdNot(telefone, id)) {
            throw new EntityAlreadyExistsException("Usuário com Telefone %s já existe.".formatted(telefone));
        }
    }
}
