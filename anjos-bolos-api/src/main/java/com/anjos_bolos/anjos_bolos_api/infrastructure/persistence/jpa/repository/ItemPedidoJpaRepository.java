package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoJpaRepository extends JpaRepository<ItemPedidoEntity, Integer> {

    boolean existsByPedidoIdAndProdutoId(Integer pedidoId, Integer produtoId);

    boolean existsByPedidoIdAndProdutoIdAndIdNot(Integer pedidoId, Integer produtoId, Integer id);

    List<ItemPedidoEntity> findByPedidoId(Integer pedidoId);

}