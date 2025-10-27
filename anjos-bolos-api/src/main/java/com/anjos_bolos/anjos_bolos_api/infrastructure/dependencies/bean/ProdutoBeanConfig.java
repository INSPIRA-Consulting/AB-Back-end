package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.CategoriaProdutoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ProdutoJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoBeanConfig {

    @Bean
    public CreateProdutoUseCase createProdutoUseCase(ProdutoJpaAdapter adapter, CategoriaProdutoJpaAdapter categoriaProdutoAdapter) {
        return new CreateProdutoUseCase(adapter, categoriaProdutoAdapter);
    }
    @Bean
    public UpdateProdutoUseCase updateProdutoUseCase(ProdutoJpaAdapter adapter, CategoriaProdutoJpaAdapter categoriaProdutoAdapter) {
        return new UpdateProdutoUseCase(adapter, categoriaProdutoAdapter);
    }
    @Bean
    public DeleteProdutoUseCase deleteProdutoUseCase(ProdutoJpaAdapter adapter) {
        return new DeleteProdutoUseCase(adapter);
    }
    @Bean
    public ListProdutosUseCase listProdutosUseCase(ProdutoJpaAdapter adapter) {
        return new ListProdutosUseCase(adapter);
    }
    @Bean
    public ListProdutosPagebleUseCase listProdutosPagebleUseCase(ProdutoJpaAdapter adapter) {
        return new ListProdutosPagebleUseCase(adapter);
    }
    @Bean
    public GetProdutoByIdUseCase getProdutoByIdUseCase(ProdutoJpaAdapter adapter) {
        return new GetProdutoByIdUseCase(adapter);
    }
    @Bean
    public ListProdutosByNomeUseCase listProdutosByNomeUseCase(ProdutoJpaAdapter adapter) {
        return new ListProdutosByNomeUseCase(adapter);
    }
    @Bean
    public ListProdutosByCategoriaProdutoIdUseCase listProdutosByCategoriaProdutoIdUseCase(ProdutoJpaAdapter adapter) {
        return new ListProdutosByCategoriaProdutoIdUseCase(adapter);
    }

}