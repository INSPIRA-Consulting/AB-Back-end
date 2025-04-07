package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(Produto novoProduto) {
        Boolean produtoExistePorNome = produtoRepository.existsByNomeIgnoreCase(novoProduto.getNome());

        if (produtoExistePorNome) {
            throw new EntidadeConflitoException("Já existe um Produto '%s' cadastrado.".formatted(novoProduto.getNome()));
        }

        novoProduto.setIdProduto(null);

        Produto produtoCadastrado = produtoRepository.save(novoProduto);

        return produtoCadastrado;
    }

    public List<Produto> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos;
    }

    public List<Produto> listarPorNome(String nomeProduto) {
        List<Produto> produtos = produtoRepository.findByNomeContainsIgnoreCase(nomeProduto);

        return produtos;
    }

    public Produto atualizar(Integer idProduto, Produto produtoParaAtualizar) {
        Boolean existePorId = produtoRepository.existsById(idProduto);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        Boolean existePorNome = produtoRepository.existsByNomeEqualsIgnoreCaseAndIdProdutoNot(
                produtoParaAtualizar.getNome(), idProduto
        );

        if (existePorNome) {
            throw new EntidadeConflitoException("Já existe um Produto '%s' cadastrado.".formatted(produtoParaAtualizar.getNome()));
        }

        produtoParaAtualizar.setIdProduto(idProduto);
        Produto produtoAtualizado = produtoRepository.save(produtoParaAtualizar);

        return produtoAtualizado;
    }

    public void excluir(Integer idProduto) {
        Boolean existePorId = produtoRepository.existsById(idProduto);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        produtoRepository.deleteById(idProduto);
    }

}
