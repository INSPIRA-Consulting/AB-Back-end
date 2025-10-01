package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ProdutoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ProdutoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoJpaAdapter implements ProdutoGateway {

    private final ProdutoRepository repository;

    public ProdutoJpaAdapter(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Produto save(Produto produto) {
        ProdutoEntity entity = repository.save(ProdutoEntityMapper.toEntity(produto));

        return ProdutoEntityMapper.toDomain(entity);
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
    public boolean existsByNomeAndIdNot(String nome, Integer id) {
        return repository.existsByNomeAndIdNot(nome, id);
    }

    @Override
    public List<Produto> findAll() {
        return repository.findAllWithCategoriaProduto()
                .stream()
                .map(ProdutoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Produto findById(Integer id) {
        return repository.findById(id)
                .map(ProdutoEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Produto com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<Produto> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ProdutoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Produto> findByCategoriaProdutoId(Integer categoriaProdutoId) {
        return repository.findByCategoriaProdutoId(categoriaProdutoId)
                .stream()
                .map(ProdutoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Produto update(Produto produto) {
        if (!repository.existsById(produto.getId())) {
            throw new NotFoundException("Produto com ID [%d] não encontrado."
                    .formatted(produto.getId()));
        }

        if (!repository.existsByNomeAndIdNot(produto.getNome(), produto.getId())) {
            throw new EntityAlreadyExistsException("Produto com nome '%s' já existe."
                    .formatted(produto.getNome()));
        }

        ProdutoEntity entity = repository.save(ProdutoEntityMapper.toEntity(produto));

        return ProdutoEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Produto com ID [%d] não encontrado."
                    .formatted(id));
        }

        repository.deleteById(id);
    }

}