package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.dashboard.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.DashboardJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DashboardBeanConfig {

    @Bean
    public GetMenorMargemLucroUseCase getMenorMargemLucroUseCase(DashboardJpaAdapter adapter) {
        return new GetMenorMargemLucroUseCase(adapter);
    }

    @Bean
    public GetMaiorMargemLucroUseCase getMaiorMargemLucroUseCase(DashboardJpaAdapter adapter) {
        return new GetMaiorMargemLucroUseCase(adapter);
    }

    @Bean
    public ListProdutosMaisVendidosUseCase listProdutosMaisVendidosUseCase(DashboardJpaAdapter adapter) {
        return new ListProdutosMaisVendidosUseCase(adapter);
    }

    @Bean
    public GetPedidosFaturamentoUseCase getPedidosFaturamentoUseCase(DashboardJpaAdapter adapter) {
        return new GetPedidosFaturamentoUseCase(adapter);
    }

    @Bean
    public GetProdutoMaisVendidoUseCase getProdutoMaisVendidoUseCase(DashboardJpaAdapter adapter) {
        return new GetProdutoMaisVendidoUseCase(adapter);
    }

    @Bean
    public ListVendasPorDiaSemanaUseCase getDiaSemanaComMaisVendasUseCase(DashboardJpaAdapter adapter) {
        return new ListVendasPorDiaSemanaUseCase(adapter);
    }

}