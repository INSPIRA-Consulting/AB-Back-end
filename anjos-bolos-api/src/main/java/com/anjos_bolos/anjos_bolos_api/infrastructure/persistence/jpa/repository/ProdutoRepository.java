package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Integer id);

    @Query("SELECT p FROM ProdutoEntity p JOIN FETCH p.categoriaProduto")
    List<ProdutoEntity> findAllWithCategoriaProduto();

    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);

    List<ProdutoEntity> findByCategoriaProdutoId(Integer categoriaProdutoId);
}
