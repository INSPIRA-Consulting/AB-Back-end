package com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido;

public record UpdateItemPedidoCommand(
        Integer id,
        Integer pedidoId,
        Integer produtoId,
        Double precoUnitario,
        Integer quantidade,
        Double peso
) {

    public UpdateItemPedidoCommand(Integer id, Integer pedidoId, Integer produtoId, Double precoUnitario, Integer quantidade, Double peso) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.precoUnitario = precoUnitario != null ? precoUnitario : 0.0;
        this.quantidade = quantidade;
        this.peso = peso;
    }
}