package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

import java.util.List;

public interface ClienteGateway {

    Cliente save(Cliente cliente);

    boolean existsById(Integer id);

    boolean existsByCpf(CPF cpf);

    boolean existsByTelefone(Telefone telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

    List<Cliente> findAll();

    Cliente findById(Integer id);

    Cliente findByCpf(CPF cpf);

    List<Cliente> findByNome(String nome);

    Cliente update(Cliente cliente);

    void delete(Integer id);

}