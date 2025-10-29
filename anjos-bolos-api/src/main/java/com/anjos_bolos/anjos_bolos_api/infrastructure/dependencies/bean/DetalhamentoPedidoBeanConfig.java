package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.DetalhamentoPedidoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ItemPedidoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ReceitaJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetalhamentoPedidoBeanConfig {


    @Bean
    public CreateDetalhamentoPedidoUseCase createDetalhamentoPedidoUseCase(DetalhamentoPedidoJpaAdapter adapter,
                                                                           ItemPedidoJpaAdapter itemPedidoAdapter,
                                                                           ReceitaJpaAdapter receitaAdapter) {
        return new CreateDetalhamentoPedidoUseCase(adapter, itemPedidoAdapter, receitaAdapter);
    }

    @Bean
    public UpdateDetalhamentoPedidoUseCase updateDetalhamentoPedidoUseCase(DetalhamentoPedidoJpaAdapter adapter,
                                                                           ItemPedidoJpaAdapter itemPedidoAdapter,
                                                                           ReceitaJpaAdapter receitaAdapter) {
        return new UpdateDetalhamentoPedidoUseCase(adapter, itemPedidoAdapter, receitaAdapter);
    }

    @Bean
    public DeleteDetalhamentoPedidoUseCase deleteDetalhamentoPedidoUseCase(DetalhamentoPedidoJpaAdapter adapter) {
        return new DeleteDetalhamentoPedidoUseCase(adapter);
    }

    @Bean
    public ListDetalhamentosPedidosUseCase listDetalhamentosPedidosUseCase(DetalhamentoPedidoJpaAdapter adapter) {
        return new ListDetalhamentosPedidosUseCase(adapter);
    }

    @Bean
    public GetDetalhamentoPedidoByIdUseCase getDetalhamentoPedidoByIdUseCase(DetalhamentoPedidoJpaAdapter adapter) {
        return new GetDetalhamentoPedidoByIdUseCase(adapter);
    }

    @Bean
    public ListDetalhamentoPedidoByItemPedidoIdUseCase listDetalhamentoPedidoByItemPedidoIdUseCase(DetalhamentoPedidoJpaAdapter adapter) {
        return new ListDetalhamentoPedidoByItemPedidoIdUseCase(adapter);
    }

}