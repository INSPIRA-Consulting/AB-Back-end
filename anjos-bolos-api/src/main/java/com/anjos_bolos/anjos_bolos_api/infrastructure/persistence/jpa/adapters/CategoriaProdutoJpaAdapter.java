package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.CategoriaProdutoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.CategoriaProdutoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.CategoriaProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProdutoJpaAdapter implements CategoriaProdutoGateway {
    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoJpaAdapter(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaProduto save(CategoriaProduto categoriaProduto) {
        CategoriaProdutoEntity entity = repository.save(CategoriaProdutoEntityMapper.toEntity(categoriaProduto));

        return CategoriaProdutoEntityMapper.toDomain(entity);
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
    public List<CategoriaProduto> findAll() {
        return repository.findAll()
                .stream()
                .map(CategoriaProdutoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public CategoriaProduto findById(Integer id) {
        return repository.findById(id)
                .map(CategoriaProdutoEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Categoria de Produto com ID [%d] não encontrada.".formatted(id)));
    }

    @Override
    public List<CategoriaProduto> findByNome(String nome) {
         return repository.findByNomeContainingIgnoreCase(nome)
                 .stream()
                 .map(CategoriaProdutoEntityMapper::toDomain)
                 .toList();
    }

    @Override
    public CategoriaProduto update(CategoriaProduto categoriaProduto) {
        if (!repository.existsById(categoriaProduto.getId())) {
            throw new NotFoundException("Categoria de Produto com ID [%d] não encontrada."
                    .formatted(categoriaProduto.getId()));
        }

        CategoriaProdutoEntity entity = repository.save(CategoriaProdutoEntityMapper.toEntity(categoriaProduto));

        return CategoriaProdutoEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Categoria de Produto com ID [%d] não encontrada."
                    .formatted(id));
        }

        repository.deleteById(id);
    }
}
