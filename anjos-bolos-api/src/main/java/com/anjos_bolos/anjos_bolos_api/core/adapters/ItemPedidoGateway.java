package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;

import java.util.List;

public interface ItemPedidoGateway {

    ItemPedido save(ItemPedido itemPedido);

    boolean existsById(Integer id);

    boolean existsByPedidoIdAndProdutoId(Integer pedidoId, Integer produtoId);

    boolean existsByPedidoIdAndProdutoIdAndIdNot(Integer pedidoId, Integer produtoId, Integer id);

    List<ItemPedido> findAll();

    ItemPedido findById(Integer id);

    List<ItemPedido> findByPedidoId(Integer pedidoId);

    ItemPedido update(ItemPedido itemPedido);

    void delete(Integer id);
}
