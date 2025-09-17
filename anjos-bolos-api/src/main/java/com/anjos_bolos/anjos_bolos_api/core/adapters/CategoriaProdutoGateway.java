package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

import java.util.List;

public interface CategoriaProdutoGateway {

    CategoriaProduto save(CategoriaProduto categoriaProduto);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    List<CategoriaProduto> findAll();

    CategoriaProduto findById(Integer id);

    CategoriaProduto findByNome(String nome);

    CategoriaProduto update(CategoriaProduto categoriaProduto);

    void delete(Integer id);
}
