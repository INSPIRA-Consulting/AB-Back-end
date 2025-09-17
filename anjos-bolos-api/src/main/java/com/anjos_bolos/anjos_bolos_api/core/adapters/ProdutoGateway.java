package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

import java.util.List;

public interface ProdutoGateway {

    Produto save(Produto produto);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Integer id);

    List<Produto> findAll();

    Produto findById(Integer id);

    List<Produto> findByNome(String nome);

    List<Produto> findByCategoriaProdutoId(Integer categoriaProdutoId);

    Produto update(Produto produto);

    void delete(Integer id);
}
