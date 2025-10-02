package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Integer> {

    boolean existsByCpf(String cpf);

    boolean existsByTelefone(String telefone);

    boolean existsByCpfAndIdNot(String cpf, Integer id);

    boolean existsByTelefoneAndIdNot(String telefone, Integer id);

    List<ClienteEntity> findByNomeStartingWithIgnoreCase(String nome);

    Optional<ClienteEntity> findByCpf(String cpf);

}