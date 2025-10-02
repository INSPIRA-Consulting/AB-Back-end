package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.CategoriaProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProdutoEntity, Integer> {

    boolean existsByNome(String nome);

    List<CategoriaProdutoEntity> findByNomeStartingWithIgnoreCase(String nome);

}