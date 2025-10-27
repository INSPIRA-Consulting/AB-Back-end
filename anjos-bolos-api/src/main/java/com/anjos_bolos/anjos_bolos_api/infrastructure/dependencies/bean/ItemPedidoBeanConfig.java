package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ItemPedidoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.PedidoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ProdutoJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemPedidoBeanConfig {


    @Bean
    public CreateItemPedidoUseCase createItemPedidoUseCase(ItemPedidoJpaAdapter adapter,
                                                           PedidoJpaAdapter pedidoAdapter,
                                                           ProdutoJpaAdapter produtoAdapter) {
        return new CreateItemPedidoUseCase(adapter, pedidoAdapter, produtoAdapter);
    }

    @Bean
    public UpdateItemPedidoUseCase updateItemPedidoUseCase(ItemPedidoJpaAdapter adapter,
                                                           PedidoJpaAdapter pedidoAdapter,
                                                           ProdutoJpaAdapter produtoAdapter) {
        return new UpdateItemPedidoUseCase(adapter, pedidoAdapter, produtoAdapter);
    }

    @Bean
    public DeleteItemPedidoUseCase deleteItemPedidoUseCase(ItemPedidoJpaAdapter adapter) {
        return new DeleteItemPedidoUseCase(adapter);
    }

    @Bean
    public ListItensPedidoUseCase listItensPedidoUseCase(ItemPedidoJpaAdapter adapter) {
        return new ListItensPedidoUseCase(adapter);
    }

    @Bean
    public GetItemPedidoByIdUseCase getItemPedidoByIdUseCase(ItemPedidoJpaAdapter adapter) {
        return new GetItemPedidoByIdUseCase(adapter);
    }

    @Bean
    public ListItensPedidoByPedidoIdUseCase listItensPedidoByPedidoIdUseCase(ItemPedidoJpaAdapter adapter) {
        return new ListItensPedidoByPedidoIdUseCase(adapter);
    }

}