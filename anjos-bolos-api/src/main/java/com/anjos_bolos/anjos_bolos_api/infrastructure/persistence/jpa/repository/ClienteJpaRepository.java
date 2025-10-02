package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Integer> {

    boolean existsByCpf(String cpf);

    boolean existsByTelefone(String telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

    List<ClienteEntity> findByNomeContainingIgnoreCase(String nome);

    Optional<ClienteEntity> findByCpf(String cpf);

}