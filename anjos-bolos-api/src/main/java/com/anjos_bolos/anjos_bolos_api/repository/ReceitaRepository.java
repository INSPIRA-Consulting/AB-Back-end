package com.anjos_bolos.anjos_bolos_api.repository;

import com.anjos_bolos.anjos_bolos_api.entity.ReceitaPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import com.anjos_bolos.anjos_bolos_api.entity.Receita;

import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, ReceitaPrimaryKey> {
    Optional<Receita> findByProduto_IdProduto(Integer idProduto);
}
