package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto.CreateComposicaoProdutoUseCase;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto.DeleteComposicaoProdutoUseCase;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto.ListComposicoesProdutoByProdutoIdUseCase;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto.UpdateComposicaoProdutoUseCase;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ComposicaoProdutoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ProdutoJpaAdapter;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ReceitaJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComposicaoProdutoBeanConfig {

    @Bean
    public CreateComposicaoProdutoUseCase createComposicaoProdutoUseCase(ComposicaoProdutoJpaAdapter adapter,
                                                                         ProdutoJpaAdapter produtoAdapter,
                                                                         ReceitaJpaAdapter receitaAdapter) {
        return new CreateComposicaoProdutoUseCase(adapter, produtoAdapter, receitaAdapter);
    }

    @Bean
    public UpdateComposicaoProdutoUseCase updateComposicaoProdutoUseCase(ComposicaoProdutoJpaAdapter adapter,
                                                                         ProdutoJpaAdapter produtoAdapter,
                                                                         ReceitaJpaAdapter receitaAdapter) {
        return new UpdateComposicaoProdutoUseCase(adapter, produtoAdapter, receitaAdapter);
    }

    @Bean
    public DeleteComposicaoProdutoUseCase deleteComposicaoProdutoUseCase(ComposicaoProdutoJpaAdapter adapter) {
        return new DeleteComposicaoProdutoUseCase(adapter);
    }

    @Bean
    public ListComposicoesProdutoByProdutoIdUseCase listComposicoesProdutoByProdutoIdUseCase(ComposicaoProdutoJpaAdapter adapter) {
        return new ListComposicoesProdutoByProdutoIdUseCase(adapter);
    }

}