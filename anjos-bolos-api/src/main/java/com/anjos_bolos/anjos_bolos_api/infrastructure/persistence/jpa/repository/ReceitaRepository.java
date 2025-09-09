package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ReceitaPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Receita;

import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, ReceitaPrimaryKey> {
    Optional<Receita> findByProduto_IdProduto(Integer idProduto);

    Boolean existsByProduto_IdProduto(Integer idProduto);

    void deleteAllByProduto_IdProduto(Integer idProduto);
}
