package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto.ProdutoService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoCadastroDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoResponseDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes para ProdutoService")
class ProdutoServiceTest {

    private ProdutoRepository produtoRepository;
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        produtoRepository = mock(ProdutoRepository.class);
        produtoService = new ProdutoService(produtoRepository);
    }

    @Test
    @DisplayName("Deve cadastrar produto com sucesso")
    void deveCadastrarProdutoComSucesso() {
        // Arrange
        ProdutoCadastroDto dto = new ProdutoCadastroDto("Bolo de Chocolate", 30.0);
        Produto produtoSalvo = new Produto(1, "Bolo de Chocolate", 30.0);

        when(produtoRepository.existsByNomeIgnoreCase("Bolo de Chocolate")).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        // Act
        ProdutoResponseDto response = produtoService.cadastrar(dto);

        // Assert
        assertEquals(1, response.getIdProduto());
        assertEquals("Bolo de Chocolate", response.getNome());
        assertEquals(30.0, response.getValorFinal());
    }

    @Test
    @DisplayName("Não deve cadastrar produto com nome duplicado")
    void naoDeveCadastrarProdutoComNomeDuplicado() {
        // Arrange
        ProdutoCadastroDto dto = new ProdutoCadastroDto("Bolo de Chocolate", 30.0);
        when(produtoRepository.existsByNomeIgnoreCase("Bolo de Chocolate")).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> produtoService.cadastrar(dto));
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                new Produto(1, "Bolo A", 25.0),
                new Produto(2, "Bolo B", 35.0)
        );
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Act
        List<ProdutoResponseDto> lista = produtoService.listar();

        // Assert
        assertEquals(2, lista.size());
        assertEquals("Bolo A", lista.get(0).getNome());
    }

    @Test
    @DisplayName("Deve listar produtos filtrando por nome")
    void deveListarProdutosPorNome() {
        // Arrange
        List<Produto> produtos = List.of(
                new Produto(1, "Bolo de Morango", 28.0)
        );
        when(produtoRepository.findByNomeContainsIgnoreCase("morango")).thenReturn(produtos);

        // Act
        List<ProdutoResponseDto> lista = produtoService.listarPorNome("morango");

        // Assert
        assertEquals(1, lista.size());
        assertEquals("Bolo de Morango", lista.get(0).getNome());
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProdutoComSucesso() {
        // Arrange
        ProdutoAtualizacaoDto dto = new ProdutoAtualizacaoDto("Bolo Especial", 32.0);
        Produto produtoAtualizado = new Produto(1, "Bolo Especial", 32.0);

        when(produtoRepository.existsById(1)).thenReturn(true);
        when(produtoRepository.existsByNomeEqualsIgnoreCaseAndIdProdutoNot("Bolo Especial", 1)).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        // Act
        ProdutoResponseDto response = produtoService.atualizar(1, dto);

        // Assert
        assertEquals("Bolo Especial", response.getNome());
        assertEquals(32.0, response.getValorFinal());
    }

    @Test
    @DisplayName("Não deve atualizar produto inexistente")
    void naoDeveAtualizarProdutoInexistente() {
        // Arrange
        ProdutoAtualizacaoDto dto = new ProdutoAtualizacaoDto("Bolo", 20.0);
        when(produtoRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> produtoService.atualizar(99, dto));
    }

    @Test
    @DisplayName("Não deve atualizar produto com nome duplicado")
    void naoDeveAtualizarProdutoComNomeDuplicado() {
        // Arrange
        ProdutoAtualizacaoDto dto = new ProdutoAtualizacaoDto("Bolo A", 20.0);

        when(produtoRepository.existsById(1)).thenReturn(true);
        when(produtoRepository.existsByNomeEqualsIgnoreCaseAndIdProdutoNot("Bolo A", 1)).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> produtoService.atualizar(1, dto));
    }

    @Test
    @DisplayName("Deve excluir produto com sucesso")
    void deveExcluirProdutoComSucesso() {
        // Arrange
        when(produtoRepository.existsById(1)).thenReturn(true);

        // Act
        produtoService.excluir(1);

        // Assert
        verify(produtoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Não deve excluir produto inexistente")
    void naoDeveExcluirProdutoInexistente() {
        // Arrange
        when(produtoRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> produtoService.excluir(99));
    }
}
