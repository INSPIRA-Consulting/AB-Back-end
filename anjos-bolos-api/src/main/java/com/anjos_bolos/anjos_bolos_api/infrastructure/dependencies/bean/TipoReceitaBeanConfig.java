package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.tipo_receita.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.TipoReceitaJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoReceitaBeanConfig {

    @Bean
    public CreateTipoReceitaUseCase createTipoReceitaUseCase(TipoReceitaJpaAdapter adapter) {
        return new CreateTipoReceitaUseCase(adapter);
    }
    @Bean
    public UpdateTipoReceitaUseCase updateTipoReceitaUseCase(TipoReceitaJpaAdapter adapter) {
        return new UpdateTipoReceitaUseCase(adapter);
    }
    @Bean
    public DeleteTipoReceitaUseCase deleteTipoReceitaUseCase(TipoReceitaJpaAdapter adapter) {
        return new DeleteTipoReceitaUseCase(adapter);
    }
    @Bean
    public ListTiposReceitaUseCase listTiposReceitaUseCase(TipoReceitaJpaAdapter adapter) {
        return new ListTiposReceitaUseCase(adapter);
    }
    @Bean
    public GetTipoReceitaByIdUseCase getTipoReceitaByIdUseCase(TipoReceitaJpaAdapter adapter) {
        return new GetTipoReceitaByIdUseCase(adapter);
    }
    @Bean
    public ListTiposReceitaByNomeUseCase listTiposReceitaByNomeUseCase(TipoReceitaJpaAdapter adapter) {
        return new ListTiposReceitaByNomeUseCase(adapter);
    }
}
