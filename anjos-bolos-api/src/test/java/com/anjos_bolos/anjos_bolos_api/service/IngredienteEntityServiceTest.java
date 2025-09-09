package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.IngredienteService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.IngredienteCadastroDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteResponseDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.IngredienteJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes para IngredienteService usando Double")
class IngredienteEntityServiceTest {

    private IngredienteJpaRepository ingredienteJpaRepository;
    private IngredienteService ingredienteService;

    @BeforeEach
    void setUp() {
        ingredienteJpaRepository = mock(IngredienteJpaRepository.class);
        ingredienteService = new IngredienteService(ingredienteJpaRepository);
    }

    @Test
    @DisplayName("Deve cadastrar um ingrediente com sucesso")
    void deveCadastrarIngredienteComSucesso() {
        // Arrange
        IngredienteCadastroDto dto = new IngredienteCadastroDto("Farinha", "kg", 5.00);

        when(ingredienteJpaRepository.existsByNome("Farinha")).thenReturn(false);
        IngredienteEntity ingredienteEntitySalvo = new IngredienteEntity(1, "Farinha", "kg", 5.00);
        when(ingredienteJpaRepository.save(any(IngredienteEntity.class))).thenReturn(ingredienteEntitySalvo);

        // Act
        IngredienteResponseDto response = ingredienteService.cadastrar(dto);

        // Assert
        assertEquals(1, response.getIdIngrediente());
        assertEquals("Farinha", response.getNome());
        assertEquals("kg", response.getMedida());
        assertEquals(5.00, response.getPreco());
        verify(ingredienteJpaRepository).save(any(IngredienteEntity.class));
    }

    @Test
    @DisplayName("Não deve cadastrar ingrediente com nome duplicado")
    void naoDeveCadastrarIngredienteComNomeDuplicado() {
        // Arrange
        IngredienteCadastroDto dto = new IngredienteCadastroDto("Farinha", "kg", 5.00);
        when(ingredienteJpaRepository.existsByNome("Farinha")).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> ingredienteService.cadastrar(dto));
        verify(ingredienteJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os ingredientes cadastrados")
    void deveListarTodosOsIngredientes() {
        // Arrange
        List<IngredienteEntity> ingredienteEntities = Arrays.asList(
                new IngredienteEntity(1, "Farinha", "kg", 5.00),
                new IngredienteEntity(2, "Açúcar", "kg", 4.00)
        );
        when(ingredienteJpaRepository.findAll()).thenReturn(ingredienteEntities);

        // Act
        List<IngredienteResponseDto> lista = ingredienteService.listar();

        // Assert
        assertEquals(2, lista.size());
        assertEquals("Farinha", lista.get(0).getNome());
        assertEquals("Açúcar", lista.get(1).getNome());
    }

    @Test
    @DisplayName("Deve listar ingredientes filtrando por nome")
    void deveListarIngredientesPorNome() {
        // Arrange
        List<IngredienteEntity> ingredienteEntities = List.of(
                new IngredienteEntity(1, "Farinha de Trigo", "kg", 5.00)
        );
        when(ingredienteJpaRepository.findByNomeContainsIgnoreCase("farinha"))
                .thenReturn(ingredienteEntities);

        // Act
        List<IngredienteResponseDto> lista = ingredienteService.listarPorNome("farinha");

        // Assert
        assertEquals(1, lista.size());
        assertEquals("Farinha de Trigo", lista.get(0).getNome());
    }

    @Test
    @DisplayName("Deve atualizar um ingrediente com sucesso")
    void deveAtualizarIngredienteComSucesso() {
        // Arrange
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Açúcar Refinado", "kg", 4.50);

        when(ingredienteJpaRepository.existsById(1)).thenReturn(true);
        when(ingredienteJpaRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot("Açúcar Refinado", 1))
                .thenReturn(false);

        IngredienteEntity ingredienteEntityAtualizado = new IngredienteEntity(1, "Açúcar Refinado", "kg", 4.50);
        when(ingredienteJpaRepository.save(any(IngredienteEntity.class))).thenReturn(ingredienteEntityAtualizado);

        // Act
        IngredienteResponseDto response = ingredienteService.atualizar(1, dto);

        // Assert
        assertEquals(1, response.getIdIngrediente());
        assertEquals("Açúcar Refinado", response.getNome());
        assertEquals("kg", response.getMedida());
        assertEquals(4.50, response.getPreco());
        verify(ingredienteJpaRepository).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar ingrediente que não existe")
    void naoDeveAtualizarIngredienteInexistente() {
        // Arrange
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Açúcar", "kg", 4.00);
        when(ingredienteJpaRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> ingredienteService.atualizar(99, dto));
        verify(ingredienteJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar ingrediente com nome duplicado")
    void naoDeveAtualizarIngredienteComNomeDuplicado() {
        // Arrange
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Farinha", "kg", 5.00);

        when(ingredienteJpaRepository.existsById(1)).thenReturn(true);
        when(ingredienteJpaRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot("Farinha", 1))
                .thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> ingredienteService.atualizar(1, dto));
        verify(ingredienteJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve excluir um ingrediente com sucesso")
    void deveExcluirIngredienteComSucesso() {
        // Arrange
        when(ingredienteJpaRepository.existsById(1)).thenReturn(true);

        // Act
        ingredienteService.excluir(1);

        // Assert
        verify(ingredienteJpaRepository).deleteById(1);
    }

    @Test
    @DisplayName("Não deve excluir ingrediente que não existe")
    void naoDeveExcluirIngredienteInexistente() {
        // Arrange
        when(ingredienteJpaRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> ingredienteService.excluir(99));
        verify(ingredienteJpaRepository, never()).deleteById(any());
    }
}
