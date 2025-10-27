package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Integer> {

    boolean existsById(Integer id);

    List<PedidoEntity> findByClienteId(Integer clienteId);

    List<PedidoEntity> findByClienteCpf(String clienteCpf);

    List<PedidoEntity> findByStatus(String status);

    List<PedidoEntity> findByDataPedido(LocalDateTime dataPedido);

    List<PedidoEntity> findByDataRetirada(LocalDateTime dataRetirada);

    List<PedidoEntity> findByDataPagamento(LocalDateTime dataPagamento);

    List<PedidoEntity> findByFormaPagamento(String formaPagamento);

}