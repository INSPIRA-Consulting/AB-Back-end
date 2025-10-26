package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceitaJpaRepository extends JpaRepository<ReceitaEntity, ReceitaEntityId> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReceitaEntity r WHERE r.id = :id")
    boolean existsById(@Param("id") Integer id);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReceitaEntity r WHERE r.nome = :nome")
    boolean existsByNome(@Param("nome") String nome);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReceitaEntity r WHERE r.nome = :nome AND r.id != :id")
    boolean existsByNomeAndIdNot(@Param("nome") String nome, @Param("id") Integer id);

    @Query("SELECT COALESCE(MAX(r.id), 0) + 1 FROM ReceitaEntity r")
    Integer findNextId();

    @Query("SELECT r FROM ReceitaEntity r WHERE r.id = :id ORDER BY r.fkIngrediente")
    List<ReceitaEntity> findAllByIdOrderByIngredienteId(@Param("id") Integer id);

    @Query("SELECT r FROM ReceitaEntity r WHERE r.nome LIKE %:nome% ORDER BY r.id, r.fkIngrediente")
    List<ReceitaEntity> findByNome(@Param("nome") String nome);

    @Query("SELECT r FROM ReceitaEntity r WHERE r.tipoReceita.id = :tipoReceitaId ORDER BY r.id, r.fkIngrediente")
    List<ReceitaEntity> findByTipoReceitaId(@Param("tipoReceitaId") Integer tipoReceitaId);

    @Query("SELECT r FROM ReceitaEntity r WHERE r.id IN (SELECT DISTINCT r2.id FROM ReceitaEntity r2 WHERE r2.fkIngrediente IN :ingredienteIds GROUP BY r2.id HAVING COUNT(DISTINCT r2.fkIngrediente) = :size) ORDER BY r.id, r.fkIngrediente")
    List<ReceitaEntity> findByIngredientesIds(@Param("ingredienteIds") List<Integer> ingredienteIds, @Param("size") Integer size);

    @Query("SELECT CASE WHEN COUNT(DISTINCT r.id) > 0 THEN true ELSE false END FROM ReceitaEntity r WHERE r.fkIngrediente IN :ingredienteIds GROUP BY r.id HAVING COUNT(DISTINCT r.fkIngrediente) = :size")
    Boolean existsByIngredientesIds(@Param("ingredienteIds") List<Integer> ingredienteIds, @Param("size") Integer size);

    @Query("SELECT COUNT(DISTINCT r.id) FROM ReceitaEntity r WHERE r.fkIngrediente IN :ingredienteIds AND r.id != :excludeId GROUP BY r.id HAVING COUNT(DISTINCT r.fkIngrediente) = :size")
    Integer countReceitasWithSameIngredientsExcludingId(@Param("ingredienteIds") List<Integer> ingredienteIds, @Param("size") Integer size, @Param("excludeId") Integer excludeId);

    @Modifying
    @Query("DELETE FROM ReceitaEntity r WHERE r.id = :receitaId")
    void deleteByReceitaId(@Param("receitaId") Integer receitaId);

    @Modifying
    @Query("DELETE FROM ReceitaEntity r WHERE r.id = :receitaId AND r.fkIngrediente IN :ingredienteIds")
    void deleteByReceitaIdAndIngredienteIds(@Param("receitaId") Integer receitaId, @Param("ingredienteIds") List<Integer> ingredienteIds);

}