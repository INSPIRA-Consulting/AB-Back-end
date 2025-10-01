package com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public interface UsuarioUniquenessChecker {

    boolean existsByCpf(CPF cpf);

    boolean existsByEmail(Email email);

    boolean existsByTelefone(Telefone telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByEmailAndIdNot(Email email, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

}