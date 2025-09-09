package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;

import java.util.List;

public interface ProdutoGateway {

    Produto save(Produto produto);

    boolean existsByNome(String nome);

    List<Produto> findAll();

    Produto findById(Integer id);

    List<Produto> findByNome(String nome);

    List<Produto> findByCategoriaProduto(CategoriaProduto categoriaProduto);

    void update(Produto produto);

    void delete(Integer id);
}
