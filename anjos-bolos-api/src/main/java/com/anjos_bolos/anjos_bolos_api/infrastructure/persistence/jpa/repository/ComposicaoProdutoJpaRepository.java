package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto.ComposicaoProdutoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto.ComposicaoProdutoEntityId;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComposicaoProdutoJpaRepository extends JpaRepository<ComposicaoProdutoEntity, ComposicaoProdutoEntityId> {

    @Query("SELECT CASE WHEN COUNT(cp) > 0 THEN true ELSE false END FROM ComposicaoProdutoEntity cp WHERE cp.fkProduto = :produtoId")
    boolean existsByProdutoId(Integer produtoId);

    boolean existsByProdutoIdAndReceitaId(Integer produtoId, Integer receitaId);

    @Query("SELECT cp FROM ComposicaoProdutoEntity cp WHERE cp.fkProduto = :produtoId ORDER BY cp.fkReceita")
    List<ComposicaoProdutoEntity> findAllByProdutoIdOrderByReceitaId(@Param("produtoId") Integer produtoId);

    @Modifying
    @Query("DELETE FROM ComposicaoProdutoEntity cp WHERE cp.fkProduto = :produtoId")
    void deleteByProdutoId(@Param("produtoId") Integer produtoId);

}