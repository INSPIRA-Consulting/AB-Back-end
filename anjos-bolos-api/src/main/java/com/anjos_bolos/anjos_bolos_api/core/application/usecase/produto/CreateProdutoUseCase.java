package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.CreateProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class CreateProdutoUseCase {
    private final ProdutoGateway gateway;
    private final CategoriaProdutoGateway categoriaProdutoGateway;

    public CreateProdutoUseCase(ProdutoGateway gateway, CategoriaProdutoGateway categoriaProdutoGateway) {
        this.gateway = gateway;
        this.categoriaProdutoGateway = categoriaProdutoGateway;
    }

    public Produto execute (CreateProdutoCommand command) {
        if (gateway.existsByNome(command.nome())) {
            throw new EntityAlreadyExistsException("Já existe um Produto com o nome: %s"
                    .formatted(command.nome()));
        }

        CategoriaProduto categoriaProduto = categoriaProdutoGateway.findById(command.categoriaProdutoId());

        Produto produto = new Produto(
                command.nome(),
                command.precoFinal(),
                categoriaProduto
        );

        return gateway.save(produto);
    }
}
