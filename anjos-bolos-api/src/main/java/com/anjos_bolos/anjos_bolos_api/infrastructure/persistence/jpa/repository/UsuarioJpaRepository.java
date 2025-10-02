package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByEmailAndIdNot(Email email, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

    Optional<UsuarioEntity> findByCpf(String cpf);

    Optional<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

    List<UsuarioEntity> findByFuncao(String funcao);

}