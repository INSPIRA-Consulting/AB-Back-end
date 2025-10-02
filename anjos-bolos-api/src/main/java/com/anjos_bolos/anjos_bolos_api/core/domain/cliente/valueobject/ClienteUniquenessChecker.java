package com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public interface ClienteUniquenessChecker {

    boolean existsByCpf(CPF cpf);

    boolean existsByTelefone(Telefone telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

}