package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioUniquenessChecker;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUniquenessCheckerJpa implements UsuarioUniquenessChecker {

    private final UsuarioJpaAdapter adapter;

    public UsuarioUniquenessCheckerJpa(UsuarioJpaAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return adapter.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return adapter.existsByEmail(email);
    }

    @Override
    public boolean existsByTelefone(Telefone telefone) {
        return adapter.existsByTelefone(telefone);
    }

    @Override
    public boolean existsByCpfAndIdNot(CPF cpf, Integer id) {
        return adapter.existsByCpfAndIdNot(cpf, id);
    }

    @Override
    public boolean existsByEmailAndIdNot(Email email, Integer id) {
        return adapter.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id) {
        return existsByTelefoneAndIdNot(telefone, id);
    }

}