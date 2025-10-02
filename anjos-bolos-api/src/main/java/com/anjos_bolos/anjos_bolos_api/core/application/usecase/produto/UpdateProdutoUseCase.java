package com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto;

import com.anjos_bolos.anjos_bolos_api.core.adapters.CategoriaProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.ProdutoGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.CreateProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.UpdateProdutoCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;

public class UpdateProdutoUseCase {

    private final ProdutoGateway gateway;
    private final CategoriaProdutoGateway categoriaProdutoGateway;

    public UpdateProdutoUseCase(ProdutoGateway gateway, CategoriaProdutoGateway categoriaProdutoGateway) {
        this.gateway = gateway;
        this.categoriaProdutoGateway = categoriaProdutoGateway;
    }

    public Produto execute (UpdateProdutoCommand command) {
        if (!gateway.existsById(command.id())) {
            throw new NotFoundException("Produto com ID [%d] não encontrado."
                    .formatted(command.id()));
        }

        if (gateway.existsByNomeAndIdNot(command.nome(), command.id())) {
            throw new EntityAlreadyExistsException("Já existe um Produto com o nome: %s"
                    .formatted(command.nome()));
        }

        CategoriaProduto categoriaProduto = categoriaProdutoGateway.findById(command.categoriaProdutoId());

        Produto produto = gateway.findById(command.id());
        produto.setNome(command.nome());
        produto.setPrecoFinal(command.precoFinal());
        produto.setCategoriaProduto(categoriaProduto);

        return gateway.update(produto);
    }

}