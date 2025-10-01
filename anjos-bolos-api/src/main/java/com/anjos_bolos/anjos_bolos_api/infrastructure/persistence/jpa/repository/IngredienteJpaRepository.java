package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;

import java.util.List;

public interface IngredienteJpaRepository extends JpaRepository<IngredienteEntity, Integer> {

    boolean existsByNome(String nome);

    List<IngredienteEntity> findByNomeContainingIgnoreCase(String nome);

}