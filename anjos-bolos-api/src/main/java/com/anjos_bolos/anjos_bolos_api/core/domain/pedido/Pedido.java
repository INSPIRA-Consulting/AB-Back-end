package com.anjos_bolos.anjos_bolos_api.core.domain.pedido;

import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class Pedido {
    private Integer id;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataPagamento;
    private StatusPedidoEnum status;
    private String observacao;
    private Usuario usuario;
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(LocalDateTime dataPedido, LocalDateTime dataEntrega, LocalDateTime dataPagamento, StatusPedidoEnum status, String observacao, Usuario usuario, Cliente cliente) {
        this.dataPedido = dataPedido;
        this.dataEntrega = dataEntrega;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.observacao = observacao;
        this.usuario = usuario;
        this.cliente = cliente;
    }

    public Pedido(Integer id, LocalDateTime dataPedido, LocalDateTime dataEntrega, LocalDateTime dataPagamento, StatusPedidoEnum status, String observacao, Usuario usuario, Cliente cliente) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.dataEntrega = dataEntrega;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.observacao = observacao;
        this.usuario = usuario;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
