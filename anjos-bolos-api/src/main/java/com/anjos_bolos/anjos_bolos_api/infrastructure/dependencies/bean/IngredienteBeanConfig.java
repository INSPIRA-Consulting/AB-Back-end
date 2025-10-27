package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.ingrediente.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.IngredienteJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredienteBeanConfig {

    @Bean
    public CreateIngredienteUseCase createIngredienteUseCase(IngredienteJpaAdapter adapter) {
        return new CreateIngredienteUseCase(adapter);
    }

    @Bean
    public UpdateIngredienteUseCase updateIngredienteUseCase(IngredienteJpaAdapter adapter) {
        return new UpdateIngredienteUseCase(adapter);
    }

    @Bean
    public DeleteIngredienteUseCase deleteIngredienteUseCase(IngredienteJpaAdapter adapter) {
        return new DeleteIngredienteUseCase(adapter);
    }

    @Bean
    public ListIngredientesUseCase listIngredientesUseCase(IngredienteJpaAdapter adapter) {
        return new ListIngredientesUseCase(adapter);
    }

    @Bean
    public ListIngredientesPageableUseCase listIngredientesPageableUseCase(IngredienteJpaAdapter adapter) {
        return new ListIngredientesPageableUseCase(adapter);
    }

    @Bean
    public GetIngredienteByIdUseCase getIngredienteByIdUseCase(IngredienteJpaAdapter adapter) {
        return new GetIngredienteByIdUseCase(adapter);
    }

    @Bean
    public ListIngredienteByNomeUseCase getIngredienteByNomeUseCase(IngredienteJpaAdapter adapter) {
        return new ListIngredienteByNomeUseCase(adapter);
    }

}