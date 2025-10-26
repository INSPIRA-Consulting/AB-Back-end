package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ReceitaEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ReceitaJpaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReceitaJpaAdapter implements ReceitaGateway {

    private final ReceitaJpaRepository repository;

    public ReceitaJpaAdapter(ReceitaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Receita save(Receita receita) {
        List<ReceitaEntity> entities = repository.saveAll(ReceitaEntityMapper.toEntityList(receita));

        return ReceitaEntityMapper.toDomain(entities);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id); // ✅ Use o método personalizado
    }

    @Override
    public boolean existsByNome(String nome) {
        return repository.existsByNome(nome);
    }

    @Override
    public boolean existsByNomeAndIdNot(String nome, Integer id) {
        return repository.existsByNomeAndIdNot(nome, id);
    }

    @Override
    public boolean existsByIngredientesIds(List<Integer> ingredienteIds) {
        Boolean result = repository.existsByIngredientesIds(ingredienteIds, ingredienteIds.size());
        return result != null && result;
    }

    @Override
    public boolean existsByIngredientesIdsAndIdNot(List<Integer> ingredienteIds, Integer id) {
        Integer count = repository.countReceitasWithSameIngredientsExcludingId(ingredienteIds, ingredienteIds.size(), id);
        return count != null && count > 0;
    }

    @Override
    public Integer findNextId() {
        return repository.findNextId();
    }


    @Override
    public List<Receita> findAll() {
        List<ReceitaEntity> entities = repository.findAll();

        return groupAndMapToReceitas(entities);
    }

    @Override
    public Optional<Receita> findById(Integer id) {
        List<ReceitaEntity> entities = repository.findAllByIdOrderByIngredienteId(id);

        return entities.isEmpty() ?
                Optional.empty() :
                Optional.of(ReceitaEntityMapper.toDomain(entities));
    }

    @Override
    public List<Receita> findByNome(String nome) {
        List<ReceitaEntity> entities = repository.findByNome(nome);

        return groupAndMapToReceitas(entities);
    }

    @Override
    public List<Receita> findByIngredientesIds(List<Integer> ingredienteIds) {
        List<ReceitaEntity> entities = repository.findByIngredientesIds(ingredienteIds, ingredienteIds.size());

        return groupAndMapToReceitas(entities);
    }

    @Override
    public List<Receita> findByTipoReceitaId(Integer tipoReceitaId) {
        List<ReceitaEntity> entities = repository.findByTipoReceitaId(tipoReceitaId);

        return groupAndMapToReceitas(entities);
    }

    @Override
    @Transactional
    public Receita update(Receita receita) {
        // Busca os registros existentes
        List<ReceitaEntity> existingEntities = repository.findAllByIdOrderByIngredienteId(receita.getId());

        if (existingEntities.isEmpty()) {
            throw new RuntimeException("Receita não encontrada para atualização");
        }

        // Converte a receita atualizada para entities
        List<ReceitaEntity> newEntities = ReceitaEntityMapper.toEntityList(receita);

        // Mapeia existentes por ingrediente ID
        Map<Integer, ReceitaEntity> existingByIngrediente = existingEntities.stream()
                .collect(Collectors.toMap(ReceitaEntity::getFkIngrediente, entity -> entity));

        // Mapeia novos por ingrediente ID
        Map<Integer, ReceitaEntity> newByIngrediente = newEntities.stream()
                .collect(Collectors.toMap(ReceitaEntity::getFkIngrediente, entity -> entity));

        List<ReceitaEntity> toSave = new ArrayList<>();
        List<Integer> ingredientesToDelete = new ArrayList<>();

        // 1. Atualiza registros existentes que ainda estão presentes
        for (ReceitaEntity newEntity : newEntities) {
            Integer ingredienteId = newEntity.getFkIngrediente();

            if (existingByIngrediente.containsKey(ingredienteId)) {
                // Ingrediente já existe - atualiza o registro existente
                ReceitaEntity existingEntity = existingByIngrediente.get(ingredienteId);
                existingEntity.setNome(newEntity.getNome());
                existingEntity.setQuantidade(newEntity.getQuantidade());
                existingEntity.setUnidadeMedida(newEntity.getUnidadeMedida());
                existingEntity.setTipoReceita(newEntity.getTipoReceita());
                toSave.add(existingEntity);
            } else {
                // Novo ingrediente - adiciona
                toSave.add(newEntity);
            }
        }

        // 2. Identifica ingredientes para remoção
        for (ReceitaEntity existingEntity : existingEntities) {
            Integer ingredienteId = existingEntity.getFkIngrediente();
            if (!newByIngrediente.containsKey(ingredienteId)) {
                ingredientesToDelete.add(ingredienteId);
            }
        }

        // 3. Executa as operações
        if (!ingredientesToDelete.isEmpty()) {
            repository.deleteByReceitaIdAndIngredienteIds(receita.getId(), ingredientesToDelete);
        }

        List<ReceitaEntity> savedEntities = repository.saveAll(toSave);
        return ReceitaEntityMapper.toDomain(savedEntities);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteByReceitaId(id);
    }

    private List<Receita> groupAndMapToReceitas(List<ReceitaEntity> entities) {
        return entities.stream()
                .collect(Collectors.groupingBy(ReceitaEntity::getId)) // ✅ Agrupar por ID da receita
                .values()
                .stream()
                .map(ReceitaEntityMapper::toDomain)
                .toList();
    }

}