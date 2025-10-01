package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.UpdateComposicaoProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

public class UpdateComposicaoProdutoUseCase {

    private final ComposicaoProdutoGateway gateway;
    private final ProdutoGateway produtoGateway;
    private final ReceitaGateway receitaGateway;

    public UpdateComposicaoProdutoUseCase(ComposicaoProdutoGateway gateway, ProdutoGateway produtoGateway, ReceitaGateway receitaGateway) {
        this.gateway = gateway;
        this.produtoGateway = produtoGateway;
        this.receitaGateway = receitaGateway;
    }

    public ComposicaoProduto execute(UpdateComposicaoProdutoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Composição de Produto com ID [%d] não encontrada.".formatted(command.id()));
        }

        if (gateway.existsByProdutoIdAndReceitaIdAndIdNot(command.produtoId(), command.receitaId(), command.id())) {
            throw new EntityAlreadyExistsException("""
                    Já existe uma outra Composição de Produto para o Produto com ID [%d] 
                    e Receita com ID [%d]""".formatted(command.produtoId(), command.receitaId()));
        }

        Produto produto = produtoGateway.findById(command.produtoId());
        Receita receita = receitaGateway.findById(command.receitaId());

        ComposicaoProduto composicaoProduto = gateway.findById(command.id());
        composicaoProduto.setProduto(produto);
        composicaoProduto.setReceita(receita);
        composicaoProduto.setQuantidade(composicaoProduto.getQuantidade());
        composicaoProduto.setObservacao(composicaoProduto.getObservacao());

        return gateway.update(composicaoProduto);
    }

}