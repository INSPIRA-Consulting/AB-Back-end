package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByNomeContainsIgnoreCase(String nome);

    Boolean existsByNomeEqualsIgnoreCaseAndIdProdutoNot(String nome, Integer id);

    Boolean existsByNomeIgnoreCase(String nome);
}
