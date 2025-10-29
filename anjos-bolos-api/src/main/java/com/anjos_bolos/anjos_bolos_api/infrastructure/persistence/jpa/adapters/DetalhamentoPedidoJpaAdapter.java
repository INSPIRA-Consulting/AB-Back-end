package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DetalhamentoPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.DetalhamentoPedidoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.DetalhamentoPedidoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.DetalhamentoPedidoJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalhamentoPedidoJpaAdapter implements DetalhamentoPedidoGateway {

    private final DetalhamentoPedidoJpaRepository repository;

    public DetalhamentoPedidoJpaAdapter(DetalhamentoPedidoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public DetalhamentoPedido save(DetalhamentoPedido detalhamentoPedido) {
        DetalhamentoPedidoEntity entity = repository.save(DetalhamentoPedidoEntityMapper.toEntity(detalhamentoPedido));

        return DetalhamentoPedidoEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByItemPedidoIdAndReceitaId(Integer itemPedidoId, Integer receitaId) {
        return repository.existsByItemPedidoIdAndReceitaId(itemPedidoId, receitaId);
    }

    @Override
    public boolean existsByItemPedidoIdAndReceitaIdAndIdNot(Integer itemPedidoId, Integer receitaId, Integer id) {
        return repository.existsByItemPedidoIdAndReceitaIdAndIdNot(itemPedidoId, receitaId, id);
    }

    @Override
    public List<DetalhamentoPedido> findAll() {
        return repository.findAll()
                .stream()
                .map(DetalhamentoPedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public DetalhamentoPedido findById(Integer id) {
        return repository.findById(id)
                .map(DetalhamentoPedidoEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("DetalhamentoPedido com ID [%d] não encontrado"
                        .formatted(id)));
    }

    @Override
    public List<DetalhamentoPedido> findByItemPedidoId(Integer id) {
        return repository.findByItemPedidoId(id)
                .stream()
                .map(DetalhamentoPedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public DetalhamentoPedido update(DetalhamentoPedido detalhamentoPedido) {
        if (!repository.existsById(detalhamentoPedido.getId())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(detalhamentoPedido.getId()));
        }

        DetalhamentoPedidoEntity entity = repository.save(DetalhamentoPedidoEntityMapper.toEntity(detalhamentoPedido));
        return DetalhamentoPedidoEntityMapper.toDomain(entity);
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