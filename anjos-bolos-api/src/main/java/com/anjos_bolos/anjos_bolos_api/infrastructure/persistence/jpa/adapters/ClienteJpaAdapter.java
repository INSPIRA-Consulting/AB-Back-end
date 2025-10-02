package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ClienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ClienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ClienteEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ClienteJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteJpaAdapter implements ClienteGateway {

    private final ClienteJpaRepository repository;

    public ClienteJpaAdapter(ClienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = repository.save(ClienteEntityMapper.toEntity(cliente));

        return ClienteEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return repository.existsByCpf(cpf.toString());
    }

    @Override
    public boolean existsByTelefone(Telefone telefone) {
        return repository.existsByTelefone(telefone.toString());
    }

    @Override
    public boolean existsByCpfAndIdNot(CPF cpf, Integer id) {
        return repository.existsByCpfAndIdNot(cpf, id);
    }

    @Override
    public boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id) {
        return repository.existsByTelefoneAndIdNot(telefone, id);
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll()
                .stream()
                .map(ClienteEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Cliente findById(Integer id) {
        return repository.findById(id)
                .map(ClienteEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ClienteEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Cliente findByCpf(CPF cpf) {
        return repository.findByCpf(cpf.toString())
                .map(ClienteEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Usuário com CPF [%s] não encontrado.".formatted(cpf)));
    }

    @Override
    public Cliente update(Cliente cliente) {
        if (!repository.existsById(cliente.getId())) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(cliente.getId()));
        }

        ClienteEntity entity = repository.save(ClienteEntityMapper.toEntity(cliente));
        return ClienteEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(id));
        }

        repository.deleteById(id);
    }

}