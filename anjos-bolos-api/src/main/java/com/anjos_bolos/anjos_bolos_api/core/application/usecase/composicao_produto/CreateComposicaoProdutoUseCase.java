package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.CreateComposicaoProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.ItemComposicaoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.valueobject.ItemComposicao;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.ArrayList;
import java.util.List;

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
        Produto produto = produtoGateway.findById(command.produtoId());
        List<ItemComposicao> receitas = new ArrayList<>();

        for (ItemComposicaoCommand itemCommand : command.receitas()) {
            if (gateway.existsByProdutoIdAndReceitaId(command.produtoId(), itemCommand.receitaId())) {
                throw new EntityAlreadyExistsException("""
                Já existe uma Composição de Produto
                para o Produto com ID [%d] e Receita com ID [%d]
                """.formatted(command.produtoId(), itemCommand.receitaId()));
            }

            Receita receita = receitaGateway.findById(itemCommand.receitaId())
                    .orElseThrow(() -> new NotFoundException("Receita com ID [%d] não encontrada"
                            .formatted(itemCommand.receitaId())));

            ItemComposicao itemComposicao = new ItemComposicao(receita, itemCommand.quantidade(), itemCommand.observacao());
            receitas.add(itemComposicao);
        }

        ComposicaoProduto composicaoProduto = new ComposicaoProduto(produto, receitas);

        return gateway.save(composicaoProduto);
    }

}