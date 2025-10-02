package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByCpfAndIdNot(String cpf, Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);

    boolean existsByTelefoneAndIdNot(String telefone, Integer id);

    Optional<UsuarioEntity> findByCpf(String cpf);

    Optional<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByNomeStartingWithIgnoreCase(String nome);

    List<UsuarioEntity> findByFuncao(String funcao);

}