package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.IngredienteEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.IngredienteJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteJpaAdapter implements IngredienteGateway {

    private final IngredienteJpaRepository repository;

    public IngredienteJpaAdapter(IngredienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
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
    public List<Ingrediente> findAll() {
        return repository.findAll()
                .stream()
                .map(IngredienteEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Ingrediente findById(Integer id) {
        return repository.findById(id)
                .map(IngredienteEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<Ingrediente> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(IngredienteEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Ingrediente update(Ingrediente ingrediente) {
        if (!repository.existsById(ingrediente.getId())) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(ingrediente.getId()));
        }

        IngredienteEntity entity = repository.save(IngredienteEntityMapper.toEntity(ingrediente));
        return IngredienteEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Ingrediente com ID [%d] não encontrado.".formatted(id));
        }

        repository.deleteById(id);
    }

}