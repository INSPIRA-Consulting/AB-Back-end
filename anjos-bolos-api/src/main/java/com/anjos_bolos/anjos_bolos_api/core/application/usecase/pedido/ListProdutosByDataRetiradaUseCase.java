package com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ItemPedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.PedidoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.ListProdutosByDataRetiradaQuery;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.ProdutosPedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRespoonseDTO;

import java.util.ArrayList;
import java.util.List;

public class ListProdutosByDataRetiradaUseCase {

    private final PedidoGateway pedidoGateway;
    private final ItemPedidoGateway itemPedidoGateway;

    public ListProdutosByDataRetiradaUseCase(PedidoGateway pedidoGateway, ItemPedidoGateway itemPedidoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.itemPedidoGateway = itemPedidoGateway;
    }

    public List<ProdutosPedidoResponseDTO> execute(ListProdutosByDataRetiradaQuery query) {

        List<Pedido> pedidos = pedidoGateway.findByDataRetirada(query.dataRetiradaInicio(), query.dataRetiradaFim());

        if (pedidos.isEmpty()) {
            throw new InvalidArgumentException("Nenhum pedido encontrado para a Data de Retirada [%s]."
                    .formatted(query.dataRetiradaInicio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }

        List<ProdutosPedidoResponseDTO> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            List<ItemPedido> itensPedido = itemPedidoGateway.findByPedidoId(pedido.getId());
            
            List<ProdutoRespoonseDTO> produtos = itensPedido.stream()
                    .map(item -> new ProdutoRespoonseDTO(
                            item.getProduto().getId(),
                            item.getProduto().getNome(),
                            item.getProduto().getPrecoFinal(),
                            item.getProduto().getCustoProducao(),
                            item.getProduto().getCategoriaProduto().getNome(),
                            null
                    ))
                    .toList();

            resultado.add(new ProdutosPedidoResponseDTO(
                    pedido.getId(),
                    pedido.getDataRetirada(),
                    produtos
            ));
        }

        return resultado;
    }

}
