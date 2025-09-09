package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoCadastroDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoResponseDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;

public class ProdutoMapper {

    public static Produto toProduto(ProdutoCadastroDto dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setValorFinal(dto.getValorFinal());
        return produto;
    }

    public static Produto toProduto(ProdutoAtualizacaoDto dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setValorFinal(dto.getValorFinal());
        return produto;
    }

    public static ProdutoResponseDto toProdutoResponseDto(Produto produto) {
        return new ProdutoResponseDto(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getValorFinal()
        );
    }
}
