package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject.ClienteUniquenessChecker;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import org.springframework.stereotype.Component;

@Component
public class ClienteUniquenessCheckerJpa implements ClienteUniquenessChecker {

    private final ClienteJpaAdapter adapter;

    public ClienteUniquenessCheckerJpa(ClienteJpaAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return adapter.existsByCpf(cpf);
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
    public boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id) {
        return adapter.existsByTelefoneAndIdNot(telefone, id);
    }

}