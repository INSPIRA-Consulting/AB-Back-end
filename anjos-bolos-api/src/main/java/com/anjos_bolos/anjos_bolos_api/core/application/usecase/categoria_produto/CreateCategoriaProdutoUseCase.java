package com.anjos_bolos.anjos_bolos_api.core.application.usecase.categoria_produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.categoria_produto.CreateCategoriaProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class CreateCategoriaProdutoUseCase {

    private final CategoriaProdutoGateway gateway;

    public CreateCategoriaProdutoUseCase(CategoriaProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public CategoriaProduto execute(CreateCategoriaProdutoCommand command) {
        if (gateway.existsByNome(command.nome())) {
            throw new EntityAlreadyExistsException("Já existe uma Categoria de Produto com este nome.");
        }

        CategoriaProduto categoriaProduto = new CategoriaProduto(command.nome(), command.descricao());

        return gateway.save(categoriaProduto);
    }

}