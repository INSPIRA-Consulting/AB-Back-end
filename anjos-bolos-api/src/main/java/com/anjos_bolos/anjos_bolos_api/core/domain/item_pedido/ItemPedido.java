package com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido;

import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class ItemPedido {
    private Integer id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
    private Double valorFinal;
    private Double peso;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, Double valorFinal, Double peso) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorFinal = valorFinal;
        this.peso = peso;
    }

    public ItemPedido(Integer id, Pedido pedido, Produto produto, Integer quantidade, Double valorFinal, Double peso) {
        this.id = id;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorFinal = valorFinal;
        this.peso = peso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
}
