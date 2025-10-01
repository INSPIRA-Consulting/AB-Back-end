package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.CreateComposicaoProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class CreateComposicaoProdutoUseCase {

    private final ComposicaoProdutoGateway gateway;
    private final ProdutoGateway produtoGateway;
    private final ReceitaGateway receitaGateway;

    public CreateComposicaoProdutoUseCase(ComposicaoProdutoGateway gateway, ProdutoGateway produtoGateway, ReceitaGateway receitaGateway) {
        this.gateway = gateway;
        this.produtoGateway = produtoGateway;
        this.receitaGateway = receitaGateway;
    }

    public ComposicaoProduto execute(CreateComposicaoProdutoCommand command) {
        if (gateway.existsByProdutoIdAndReceitaId(command.produtoId(), command.receitaId())) {
            throw new EntityAlreadyExistsException("""
                    Já existe uma Composição de Produto
                    para o Produto com ID [%d] e Receita com ID [%d]
                    """.formatted(command.produtoId(), command.receitaId()));
        }

        Produto produto = produtoGateway.findById(command.produtoId());
        Receita receita = receitaGateway.findById(command.receitaId());

        ComposicaoProduto composicaoProduto = new ComposicaoProduto(produto, receita,
                command.quantidade(), command.observacao());

        return gateway.save(composicaoProduto);
    }

}