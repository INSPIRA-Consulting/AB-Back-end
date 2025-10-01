package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.TipoReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.TipoReceitaEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.TipoReceitaEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.TipoReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoReceitaJpaAdapter implements TipoReceitaGateway {

    private final TipoReceitaRepository repository;

    public TipoReceitaJpaAdapter(TipoReceitaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TipoReceita save(TipoReceita tipoReceita) {
        TipoReceitaEntity entity = repository.save(TipoReceitaEntityMapper.toEntity(tipoReceita));

        return TipoReceitaEntityMapper.toDomain(entity);
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
    public List<TipoReceita> findAll() {
        return repository.findAll()
                .stream()
                .map(TipoReceitaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public TipoReceita findById(Integer id) {
        return repository.findById(id)
                .map(TipoReceitaEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Tipo de Receita com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<TipoReceita> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(TipoReceitaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public TipoReceita update(TipoReceita tipoReceita) {
        if (!repository.existsById(tipoReceita.getId())) {
            throw new NotFoundException("Tipo de Receita com ID [%d] não encontrado.".formatted(tipoReceita.getId()));
        }

        if (repository.existsByNome(tipoReceita.getNome())) {
            throw new EntityAlreadyExistsException("Tipo de Receita com nome [%s] já existe."
                    .formatted(tipoReceita.getNome()));
        }

        TipoReceitaEntity entity = repository.save(TipoReceitaEntityMapper.toEntity(tipoReceita));

        return TipoReceitaEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Tipo de Receita com ID [%d] não encontrado.".formatted(id));
        }

        repository.deleteById(id);
    }

}