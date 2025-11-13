package com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido;

public record CreateItemPedidoCommand(
        Integer pedidoId,
        Integer produtoId,
        Double precoUnitario,
        Integer quantidade,
        Double peso
) {

    public CreateItemPedidoCommand(Integer pedidoId, Integer produtoId, Double precoUnitario, Integer quantidade, Double peso) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.precoUnitario = precoUnitario != null ? precoUnitario : 0.0;
        this.quantidade = quantidade;
        this.peso = peso;
    }
}