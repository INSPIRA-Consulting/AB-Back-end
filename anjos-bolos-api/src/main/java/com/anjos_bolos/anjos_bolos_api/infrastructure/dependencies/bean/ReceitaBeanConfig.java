package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.IngredienteJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ReceitaJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.TipoReceitaJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceitaBeanConfig {

    @Bean
    public CreateReceitaUseCase createReceitaUseCase(ReceitaJpaAdapter adapter, IngredienteJpaAdapter ingredienteJpaAdapter,
                                                     TipoReceitaJpaAdapter tipoReceitaJpaAdapter) {
        return new CreateReceitaUseCase(adapter, ingredienteJpaAdapter, tipoReceitaJpaAdapter);
    }

    @Bean
    public UpdateReceitaUseCase updateReceitaUseCase(ReceitaJpaAdapter adapter, IngredienteJpaAdapter ingredienteJpaAdapter,
                                                     TipoReceitaJpaAdapter tipoReceitaJpaAdapter) {
        return new UpdateReceitaUseCase(adapter, ingredienteJpaAdapter, tipoReceitaJpaAdapter);
    }

    @Bean
    public DeleteReceitaUseCase deleteReceitaUseCase(ReceitaJpaAdapter adapter) {
        return new DeleteReceitaUseCase(adapter);
    }

    @Bean
    public ListReceitasUseCase listReceitasUseCase(ReceitaJpaAdapter adapter) {
        return new ListReceitasUseCase(adapter);
    }

    @Bean
    public GetReceitaByIdUseCase getReceitaByIdUseCase(ReceitaJpaAdapter adapter) {
        return new GetReceitaByIdUseCase(adapter);
    }

    @Bean
    public ListReceitasByNomeUseCase listReceitasByNomeUseCase(ReceitaJpaAdapter adapter) {
        return new ListReceitasByNomeUseCase(adapter);
    }

    @Bean
    public ListReceitasByIngredienteIdsUseCase listReceitasByIngredienteIdsUseCase(ReceitaJpaAdapter adapter) {
        return new ListReceitasByIngredienteIdsUseCase(adapter);
    }

    @Bean
    public ListReceitasByTipoReceitaIdUseCase listReceitasByTipoReceitaIdUseCase(ReceitaJpaAdapter adapter) {
        return new ListReceitasByTipoReceitaIdUseCase(adapter);
    }


}