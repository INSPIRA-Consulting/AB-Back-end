package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoGateway {

    Pedido save(Pedido pedido);

    boolean existsById(Integer id);

    List<Pedido> findAll();

    Pedido findById(Integer id);

    List<Pedido> findByClienteId(Integer clienteId);

    List<Pedido> findByClienteCpf(String clienteCpf);

    List<Pedido> findByStatus(String status);

    List<Pedido> findByDataPedido(LocalDateTime dataPedido);

    List<Pedido> findByDataRetirada(LocalDateTime dataRetirada);

    List<Pedido> findByDataPagamento(LocalDateTime dataPagamento);

    List<Pedido> findByFormaPagamento(String formaPagamento);

    Pedido update(Pedido pedido);

    void delete(Integer id);

}