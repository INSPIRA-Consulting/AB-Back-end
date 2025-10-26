package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.composicao_produto.ComposicaoProdutoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ComposicaoProdutoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ComposicaoProdutoJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComposicaoProdutoJpaAdapter implements ComposicaoProdutoGateway {

    private final ComposicaoProdutoJpaRepository repository;

    public ComposicaoProdutoJpaAdapter(ComposicaoProdutoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ComposicaoProduto save(ComposicaoProduto composicaoProduto) {
        List<ComposicaoProdutoEntity> entities = repository.saveAll(ComposicaoProdutoEntityMapper.toEntityList(composicaoProduto));

        return ComposicaoProdutoEntityMapper.toDomain(entities);
    }

    @Override
    public boolean existsByProdutoId(Integer produtoId) {
        return repository.existsByProdutoId(produtoId);
    }

    @Override
    public boolean existsByProdutoIdAndReceitaId(Integer produtoId, Integer receitaId) {
        return repository.existsByProdutoIdAndReceitaId(produtoId, receitaId);
    }

    @Override
    public ComposicaoProduto findAllByProdutoId(Integer produtoId) {
        List<ComposicaoProdutoEntity> entities = repository.findAllByProdutoIdOrderByReceitaId(produtoId);

        return ComposicaoProdutoEntityMapper.toDomain(entities);
    }

    @Override
    @Transactional
    public ComposicaoProduto update(ComposicaoProduto composicaoProduto) {
        repository.deleteByProdutoId(composicaoProduto.getProduto().getId());

        return save(composicaoProduto);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteByProdutoId(id);
    }
}