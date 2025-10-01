package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.TipoReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoReceitaRepository extends JpaRepository<TipoReceitaEntity, Integer> {

    boolean existsByNome(String nome);

    List<TipoReceitaEntity> findByNomeContainingIgnoreCase(String nome);
}
