package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.PedidoEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.PedidoEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.PedidoJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoJpaAdapter implements PedidoGateway {

    private final PedidoJpaRepository repository;

    public PedidoJpaAdapter(PedidoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoEntity entity = repository.save(PedidoEntityMapper.toEntity(pedido));

        return PedidoEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll()
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Pedido findById(Integer id) {
        return repository.findById(id)
                .map(PedidoEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<Pedido> findByClienteId(Integer clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByClienteCpf(String clienteCpf) {
        return repository.findByClienteCpf(clienteCpf)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByStatus(String status) {
        return repository.findByStatus(status)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByDataPedido(LocalDateTime dataPedido) {
        return repository.findByDataPedido(dataPedido)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByDataRetirada(LocalDateTime dataRetiradaInicio, LocalDateTime dataRetiradaFim) {
        return repository.findByDataRetiradaBetween(dataRetiradaInicio, dataRetiradaFim)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByDataPagamento(LocalDateTime dataPagamento) {
        return repository.findByDataPagamento(dataPagamento)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByFormaPagamento(String formaPagamento) {
        return repository.findByFormaPagamento(formaPagamento)
                .stream()
                .map(PedidoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Pedido update(Pedido pedido) {
        if (!repository.existsById(pedido.getId())) {
            throw new NotFoundException("Pedido com ID [%d] não encontrado.".formatted(pedido.getId()));
        }

        PedidoEntity entity = repository.save(PedidoEntityMapper.toEntity(pedido));
        return PedidoEntityMapper.toDomain(entity);
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