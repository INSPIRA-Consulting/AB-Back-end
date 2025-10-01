package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.CategoriaProdutoJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriaProdutoBeanConfig {

    @Bean
    public CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new CreateCategoriaProdutoUseCase(adapter);
    }
    @Bean
    public UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new UpdateCategoriaProdutoUseCase(adapter);
    }
    @Bean
    public DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new DeleteCategoriaProdutoUseCase(adapter);
    }
    @Bean
    public ListCategoriasProdutoUseCase listCategoriasProdutoUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new ListCategoriasProdutoUseCase(adapter);
    }
    @Bean
    public GetCategoriaProdutoByIdUseCase getCategoriaProdutoByIdUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new GetCategoriaProdutoByIdUseCase(adapter);
    }
    @Bean
    public ListCategoriasProdutoByNomeUseCase listCategoriasProdutoByNomeUseCase(CategoriaProdutoJpaAdapter adapter) {
        return new ListCategoriasProdutoByNomeUseCase(adapter);
    }

}