package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;

import java.util.List;

public interface DetalhamentoPedidoGateway {

    DetalhamentoPedido save(DetalhamentoPedido detalhamentoPedido);

    boolean existsById(Integer id);

    boolean existsByItemPedidoIdAndReceitaId(Integer itemPedidoId, Integer receitaId);

    boolean existsByItemPedidoIdAndReceitaIdAndIdNot(Integer itemPedidoId, Integer receitaId, Integer id);

    List<DetalhamentoPedido> findAll();

    DetalhamentoPedido findById(Integer id);

    List<DetalhamentoPedido> findByItemPedidoId(Integer id);

    DetalhamentoPedido update(DetalhamentoPedido detalhamentoPedido);

    void delete(Integer id);

}