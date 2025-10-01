package com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class DetalhamentoPedido {

    private Integer id;
    private ItemPedido itemPedido;
    private Receita receita;
    private String observacao;

    public DetalhamentoPedido () {
    }

    public DetalhamentoPedido(ItemPedido itemPedido, Receita receita, String observacao) {
        this.itemPedido = itemPedido;
        this.receita = receita;
        this.observacao = observacao;
    }

    public DetalhamentoPedido(Integer id, ItemPedido itemPedido, Receita receita, String observacao) {
        this.id = id;
        this.itemPedido = itemPedido;
        this.receita = receita;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}