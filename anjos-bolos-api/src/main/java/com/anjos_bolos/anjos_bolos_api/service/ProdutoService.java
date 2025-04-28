package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.produto.ProdutoResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.mapper.ProdutoMapper;
import com.anjos_bolos.anjos_bolos_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDto cadastrar(ProdutoCadastroDto novoProdutoDto) {
        Boolean produtoExistePorNome = produtoRepository.existsByNomeIgnoreCase(novoProdutoDto.getNome());

        if (produtoExistePorNome) {
            throw new EntidadeConflitoException("Já existe um Produto '%s' cadastrado.".formatted(novoProdutoDto.getNome()));
        }

        Produto produtoParaSalvar = ProdutoMapper.toProduto(novoProdutoDto);
        produtoParaSalvar.setIdProduto(null);

        Produto produtoSalvo = produtoRepository.save(produtoParaSalvar);

        return ProdutoMapper.toProdutoResponseDto(produtoSalvo);
    }

    public List<ProdutoResponseDto> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(ProdutoMapper::toProdutoResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDto> listarPorNome(String nomeProduto) {
        List<Produto> produtosFiltrados = produtoRepository.findByNomeContainsIgnoreCase(nomeProduto);
        return produtosFiltrados.stream()
                .map(ProdutoMapper::toProdutoResponseDto)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDto atualizar(Integer idProduto, ProdutoAtualizacaoDto produtoParaAtualizarDto) {
        Boolean existePorId = produtoRepository.existsById(idProduto);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        Boolean existePorNome = produtoRepository.existsByNomeEqualsIgnoreCaseAndIdProdutoNot(
                produtoParaAtualizarDto.getNome(), idProduto
        );

        if (existePorNome) {
            throw new EntidadeConflitoException("Já existe um Produto '%s' cadastrado.".formatted(produtoParaAtualizarDto.getNome()));
        }

        Produto produtoParaSalvar = ProdutoMapper.toProduto(produtoParaAtualizarDto);
        produtoParaSalvar.setIdProduto(idProduto);

        Produto produtoAtualizado = produtoRepository.save(produtoParaSalvar);

        return ProdutoMapper.toProdutoResponseDto(produtoAtualizado);
    }

    public void excluir(Integer idProduto) {
        Boolean existePorId = produtoRepository.existsById(idProduto);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        produtoRepository.deleteById(idProduto);
    }
}
