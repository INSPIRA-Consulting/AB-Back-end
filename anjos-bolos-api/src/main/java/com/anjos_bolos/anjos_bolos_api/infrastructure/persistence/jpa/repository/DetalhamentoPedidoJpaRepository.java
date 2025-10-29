package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.DetalhamentoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalhamentoPedidoJpaRepository extends JpaRepository<DetalhamentoPedidoEntity, Integer> {

    boolean existsById(Integer id);

    boolean existsByItemPedidoIdAndReceitaId(Integer itemPedidoId, Integer receitaId);

    boolean existsByItemPedidoIdAndReceitaIdAndIdNot(Integer itemPedidoId, Integer receitaId, Integer id);

    List<DetalhamentoPedidoEntity> findByItemPedidoId(Integer id);

}