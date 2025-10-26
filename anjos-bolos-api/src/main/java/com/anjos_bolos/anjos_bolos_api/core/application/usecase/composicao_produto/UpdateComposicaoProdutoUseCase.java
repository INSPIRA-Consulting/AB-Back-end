package com.anjos_bolos.anjos_bolos_api.core.application.usecase.composicao_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.ComposicaoProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ReceitaGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.ItemComposicaoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.command.composicao_produto.UpdateComposicaoProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.ComposicaoProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.composicao_produto.valueobject.ItemComposicao;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.Receita;

import java.util.ArrayList;
import java.util.List;

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
        if (!gateway.existsByProdutoId(command.produtoId())) {
            throw new NotFoundException("Composição de Produto para Produto ID [%d] não encontrada.".formatted(command.produtoId()));
        }

        Produto produto = produtoGateway.findById(command.produtoId());

        gateway.delete(command.produtoId());

        List<ItemComposicao> itensComposicao = new ArrayList<>();

        for (ItemComposicaoCommand itemCommand : command.receitas()) {

            Receita receita = receitaGateway.findById(itemCommand.receitaId())
                    .orElseThrow(() -> new NotFoundException("Receita com ID [%d] não encontrada"
                            .formatted(itemCommand.receitaId())));


            ItemComposicao itemComposicao = new ItemComposicao(receita, itemCommand.quantidade(), itemCommand.observacao());
            itensComposicao.add(itemComposicao);
        }

        ComposicaoProduto composicaoProduto = new ComposicaoProduto(produto, itensComposicao);

        return gateway.update(composicaoProduto);
    }

}