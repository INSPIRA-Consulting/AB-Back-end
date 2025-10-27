package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ClienteJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.PedidoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.UsuarioJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoBeanConfig {


    @Bean
    public CreatePedidoUseCase createPedidoUseCase(PedidoJpaAdapter adapter,
                                                   UsuarioJpaAdapter usuarioAdapter,
                                                   ClienteJpaAdapter clienteAdapter) {
        return new CreatePedidoUseCase(adapter, usuarioAdapter, clienteAdapter);
    }

    @Bean
    public UpdatePedidoUseCase updatePedidoUseCase(PedidoJpaAdapter adapter,
                                                   UsuarioJpaAdapter usuarioAdapter,
                                                   ClienteJpaAdapter clienteAdapter) {
        return new UpdatePedidoUseCase(adapter, usuarioAdapter, clienteAdapter);
    }

    @Bean
    public DeletePedidoUseCase deletePedidoUseCase(PedidoJpaAdapter adapter) {
        return new DeletePedidoUseCase(adapter);
    }

    @Bean
    public ListPedidosUseCase listPedidosUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosUseCase(adapter);
    }

    @Bean
    public GetPedidoByIdUseCase getPedidoByIdUseCase(PedidoJpaAdapter adapter) {
        return new GetPedidoByIdUseCase(adapter);
    }

    @Bean
    public ListPedidosByClienteIdUseCase listPedidosByClienteIdUseCase(PedidoJpaAdapter adapter,
                                                                       ClienteJpaAdapter clienteAdapter) {
        return new ListPedidosByClienteIdUseCase(adapter, clienteAdapter);
    }

    @Bean
    public ListPedidosByClienteCpfUseCase listPedidosByClienteCpfUseCase(PedidoJpaAdapter adapter,
                                                                         ClienteJpaAdapter clienteAdapter) {
        return new ListPedidosByClienteCpfUseCase(adapter, clienteAdapter);
    }

    @Bean
    public ListPedidosByDataPedidoUseCase listPedidosByDataPedidoUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosByDataPedidoUseCase(adapter);
    }

    @Bean
    public ListPedidosByDataRetiradaUseCase listPedidosByDataRetiradaUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosByDataRetiradaUseCase(adapter);
    }

    @Bean
    public ListPedidosByDataPagamentoUseCase listPedidosByDataPagamentoUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosByDataPagamentoUseCase(adapter);
    }

    @Bean
    public ListPedidosByFormaPagamentoUseCase listPedidosByFormaPagamentoUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosByFormaPagamentoUseCase(adapter);
    }

    @Bean
    public ListPedidosByStatusUseCase listPedidosByStatusUseCase(PedidoJpaAdapter adapter) {
        return new ListPedidosByStatusUseCase(adapter);
    }

}