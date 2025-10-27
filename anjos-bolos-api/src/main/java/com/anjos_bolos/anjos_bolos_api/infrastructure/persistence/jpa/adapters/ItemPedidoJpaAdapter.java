package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ItemPedidoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ItemPedidoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ItemPedidoJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoJpaAdapter  implements ItemPedidoGateway {

    private final ItemPedidoJpaRepository repository;


    public ItemPedidoJpaAdapter(ItemPedidoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemPedido save(ItemPedido itemPedido) {
        ItemPedidoEntity entity = repository.save(ItemPedidoEntityMapper.toEntity(itemPedido));

        return ItemPedidoEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByPedidoIdAndProdutoId(Integer pedidoId, Integer produtoId) {
        return repository.existsByPedidoIdAndProdutoId(pedidoId, produtoId);
    }

    @Override
    public boolean existsByPedidoIdAndProdutoIdAndIdNot(Integer pedidoId, Integer produtoId, Integer id) {
        return repository.existsByPedidoIdAndProdutoIdAndIdNot(pedidoId, produtoId, id);
    }

    @Override
    public List<ItemPedido> findAll() {
        return repository.findAll()
                .stream()
                .map(ItemPedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public ItemPedido findById(Integer id) {
        return repository.findById(id)
                .map(ItemPedidoEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<ItemPedido> findByPedidoId(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(ItemPedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public ItemPedido update(ItemPedido itemPedido) {
        if (!repository.existsById(itemPedido.getId())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(itemPedido.getId()));
        }

        ItemPedidoEntity entity = repository.save(ItemPedidoEntityMapper.toEntity(itemPedido));
        return ItemPedidoEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado."
                    .formatted(id));
        }

        repository.deleteById(id);
    }

}