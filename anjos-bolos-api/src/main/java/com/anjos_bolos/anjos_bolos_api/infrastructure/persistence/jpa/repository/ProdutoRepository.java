package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
