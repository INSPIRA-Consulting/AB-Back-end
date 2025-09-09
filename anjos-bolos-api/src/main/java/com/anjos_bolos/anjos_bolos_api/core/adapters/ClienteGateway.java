package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;

import java.util.List;

public interface ClienteGateway {

    Cliente save(Cliente cliente);

    boolean existsByCpf(String cpf);

    List<Cliente> findAll();

    Cliente findById(Integer id);

    Cliente findByCpf(String cpf);

    List<Cliente> findByNome(String nome);

    void update(Cliente cliente);

    void delete(Integer id);
}
