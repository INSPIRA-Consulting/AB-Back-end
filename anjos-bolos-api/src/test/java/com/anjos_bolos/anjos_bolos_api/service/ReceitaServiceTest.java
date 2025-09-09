package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.receita.ReceitaService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Receita;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.ReceitaPrimaryKey;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.IngredienteJpaRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ProdutoRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private IngredienteJpaRepository ingredienteJpaRepository;

    @InjectMocks
    private ReceitaService receitaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar uma nova receita com sucesso")
    void cadastrar_ReceitaComSucesso() {
        // Arrange
        Produto produto = new Produto(1, "Bolo", 40.0);
        IngredienteEntity ingredienteEntity = new IngredienteEntity(2, "Farinha", "kg", 5.0);
        ReceitaPrimaryKey id = new ReceitaPrimaryKey(1, 2);
        Receita receita = new Receita(id, produto, ingredienteEntity, 2.0);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(ingredienteJpaRepository.findById(2)).thenReturn(Optional.of(ingredienteEntity));
        when(receitaRepository.existsById(id)).thenReturn(false);
        when(receitaRepository.save(any())).thenReturn(receita);

        // Act
        Receita resultado = receitaService.cadastrar(receita);

        // Assert
        assertEquals(produto, resultado.getProduto());
        assertEquals(ingredienteEntity, resultado.getIngrediente());
        assertEquals(2.0, resultado.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar receita com produto inexistente")
    void cadastrar_ProdutoInexistente() {
        // Arrange
        Receita receita = new Receita(new ReceitaPrimaryKey(99, 2), null, null, 2.0);

        when(produtoRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> receitaService.cadastrar(receita));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar receita com ingrediente inexistente")
    void cadastrar_IngredienteInexistente() {
        // Arrange
        Produto produto = new Produto(1, "Bolo", 40.0);
        Receita receita = new Receita(new ReceitaPrimaryKey(1, 99), produto, null, 2.0);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(ingredienteJpaRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> receitaService.cadastrar(receita));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar receita duplicada")
    void cadastrar_ReceitaDuplicada() {
        // Arrange
        Produto produto = new Produto(1, "Bolo", 40.0);
        IngredienteEntity ingredienteEntity = new IngredienteEntity(2, "Farinha", "kg", 5.0);
        ReceitaPrimaryKey id = new ReceitaPrimaryKey(1, 2);
        Receita receita = new Receita(id, produto, ingredienteEntity, 2.0);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(ingredienteJpaRepository.findById(2)).thenReturn(Optional.of(ingredienteEntity));
        when(receitaRepository.existsById(id)).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> receitaService.cadastrar(receita));
    }

    @Test
    @DisplayName("Deve listar todas as receitas")
    void listar_TodasReceitas() {
        // Arrange
        Receita receita = new Receita();
        when(receitaRepository.findAll()).thenReturn(List.of(receita));

        // Act
        List<Receita> resultado = receitaService.listar();

        // Assert
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve buscar receita por ID do produto")
    void buscarReceitaPorProduto_Sucesso() {
        // Arrange
        Receita receita = new Receita();
        when(receitaRepository.findByProduto_IdProduto(1)).thenReturn(Optional.of(receita));

        // Act
        Receita resultado = receitaService.buscarReceitaPorProduto(1);

        // Assert
        assertEquals(receita, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar receita inexistente")
    void buscarReceitaPorProduto_Inexistente() {
        // Arrange
        when(receitaRepository.findByProduto_IdProduto(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> receitaService.buscarReceitaPorProduto(1));
    }

    @Test
    @DisplayName("Deve calcular preço do produto com base nos ingredientes")
    void calcularPreco_Sucesso() {
        // Arrange
        Produto produto = new Produto(1, "Bolo", 40.0);
        IngredienteEntity ingredienteEntity = new IngredienteEntity(2, "Farinha", "kg", 5.0);
        Receita receita = new Receita(new ReceitaPrimaryKey(1, 2), produto, ingredienteEntity, 3.0);

        when(produtoRepository.existsById(1)).thenReturn(true);
        when(receitaRepository.findAll()).thenReturn(List.of(receita));

        // Act
        Double preco = receitaService.calcularPreco(1);

        // Assert
        assertEquals(15.0, preco);
    }

    @Test
    @DisplayName("Deve lançar exceção ao calcular preço de produto inexistente")
    void calcularPreco_ProdutoInexistente() {
        // Arrange
        when(produtoRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> receitaService.calcularPreco(1));
    }

    @Test
    @DisplayName("Deve excluir receita por ID de produto")
    void excluir_Sucesso() {
        // Arrange
        when(receitaRepository.existsByProduto_IdProduto(1)).thenReturn(true);
        doNothing().when(receitaRepository).deleteAllByProduto_IdProduto(1);

        // Act
        receitaService.excluir(1);

        // Assert
        verify(receitaRepository, times(1)).deleteAllByProduto_IdProduto(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir receita inexistente")
    void excluir_ReceitaInexistente() {
        // Arrange
        when(receitaRepository.existsByProduto_IdProduto(1)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> receitaService.excluir(1));
    }
}
