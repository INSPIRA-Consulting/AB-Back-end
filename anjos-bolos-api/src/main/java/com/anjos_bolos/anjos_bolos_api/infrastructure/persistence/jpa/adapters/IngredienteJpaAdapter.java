package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.IngredienteEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.IngredienteJpaRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Service
public class IngredienteJpaAdapter implements IngredienteGateway {

    private final IngredienteJpaRepository repository;
    private final CacheManager cacheManager;
    private final ObjectMapper cacheMapper;

    public IngredienteJpaAdapter(IngredienteJpaRepository repository, CacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
        this.cacheMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    @CacheEvict(value = {"ingredienteById", "ingredientesByNome", "ingredientesLista"}, allEntries = true)
    public Ingrediente save(Ingrediente ingrediente) {
        IngredienteEntity entity = repository.save(IngredienteEntityMapper.toEntity(ingrediente));
        return IngredienteEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return repository.existsByNome(nome);
    }

    @Override
    @Cacheable(value = "ingredientesLista")
    public Page<Ingrediente> findAll() {
        return repository.findAll(Pageable.unpaged())
                .map(IngredienteEntityMapper::toDomain);
    }

    @Override
    public Page<Ingrediente> findAll(Pageable pageable) {
        Cache cache = cacheManager.getCache("ingredientesLista");
        String key = pageable == null ? "UNPAGED" : pageable.toString();
        if (cache != null) {
            Object cachedObj = cache.get(key, Object.class);
            if (cachedObj != null) {
                PageCache cached = cacheMapper.convertValue(cachedObj, PageCache.class);
                if (cached != null && cached.getContent() != null) {
                    return new PageImpl<>(cached.getContent(), pageable, cached.getTotalElements());
                }
            }
        }

        Page<Ingrediente> page = repository.findAll(pageable)
                .map(IngredienteEntityMapper::toDomain);

        if (cache != null) {
            cache.put(key, new PageCache(page.getContent(), page.getTotalElements()));
        }
        return page;
    }

    public static class PageCache {
        private List<Ingrediente> content;
        private long totalElements;

        public PageCache() {
        }

        public PageCache(List<Ingrediente> content, long totalElements) {
            this.content = content;
            this.totalElements = totalElements;
        }

        public List<Ingrediente> getContent() {
            return content;
        }

        public void setContent(List<Ingrediente> content) {
            this.content = content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }
    }

    @Override
    @Cacheable(value = "ingredienteById", key = "#id")
    public Ingrediente findById(Integer id) {
        return repository.findById(id)
                .map(IngredienteEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    @Cacheable(value = "ingredientesByNome", key = "#nome")
    public List<Ingrediente> findByNome(String nome) {
        return repository.findByNomeStartingWithIgnoreCase(nome)
                .stream()
                .map(IngredienteEntityMapper::toDomain)
                .toList();
    }

    @Override
    @CacheEvict(value = {"ingredienteById", "ingredientesByNome", "ingredientesLista"}, allEntries = true)
    public Ingrediente update(Ingrediente ingrediente) {
        if (!repository.existsById(ingrediente.getId())) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(ingrediente.getId()));
        }

        IngredienteEntity entity = repository.save(IngredienteEntityMapper.toEntity(ingrediente));
        return IngredienteEntityMapper.toDomain(entity);
    }

    @Override
    @CacheEvict(value = {"ingredienteById", "ingredientesByNome", "ingredientesLista"}, allEntries = true)
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(id));
        }

        repository.deleteById(id);
    }
}