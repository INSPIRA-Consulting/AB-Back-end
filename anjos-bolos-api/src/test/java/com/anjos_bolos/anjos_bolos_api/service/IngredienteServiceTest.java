package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.IngredienteCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes para IngredienteService usando Double")
class IngredienteServiceTest {

    private IngredienteRepository ingredienteRepository;
    private IngredienteService ingredienteService;

    @BeforeEach
    void setUp() {
        ingredienteRepository = mock(IngredienteRepository.class);
        ingredienteService = new IngredienteService(ingredienteRepository);
    }

    @Test
    @DisplayName("Deve cadastrar um ingrediente com sucesso")
    void deveCadastrarIngredienteComSucesso() {
        // Arrange
        IngredienteCadastroDto dto = new IngredienteCadastroDto("Farinha", "kg", 5.00);

        when(ingredienteRepository.existsByNome("Farinha")).thenReturn(false);
        Ingrediente ingredienteSalvo = new Ingrediente(1, "Farinha", "kg", 5.00);
        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingredienteSalvo);

        // Act
        IngredienteResponseDto response = ingredienteService.cadastrar(dto);

        // Assert
        assertEquals(1, response.getIdIngrediente());
        assertEquals("Farinha", response.getNome());
        assertEquals("kg", response.getMedida());
        assertEquals(5.00, response.getPreco());
        verify(ingredienteRepository).save(any(Ingrediente.class));
    }

    @Test
    @DisplayName("Não deve cadastrar ingrediente com nome duplicado")
    void naoDeveCadastrarIngredienteComNomeDuplicado() {
        // Arrange
        IngredienteCadastroDto dto = new IngredienteCadastroDto("Farinha", "kg", 5.00);
        when(ingredienteRepository.existsByNome("Farinha")).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> ingredienteService.cadastrar(dto));
        verify(ingredienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os ingredientes cadastrados")
    void deveListarTodosOsIngredientes() {
        // Arrange
        List<Ingrediente> ingredientes = Arrays.asList(
                new Ingrediente(1, "Farinha", "kg", 5.00),
                new Ingrediente(2, "Açúcar", "kg", 4.00)
        );
        when(ingredienteRepository.findAll()).thenReturn(ingredientes);

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
        List<Ingrediente> ingredientes = List.of(
                new Ingrediente(1, "Farinha de Trigo", "kg", 5.00)
        );
        when(ingredienteRepository.findByNomeContainsIgnoreCase("farinha"))
                .thenReturn(ingredientes);

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

        when(ingredienteRepository.existsById(1)).thenReturn(true);
        when(ingredienteRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot("Açúcar Refinado", 1))
                .thenReturn(false);

        Ingrediente ingredienteAtualizado = new Ingrediente(1, "Açúcar Refinado", "kg", 4.50);
        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingredienteAtualizado);

        // Act
        IngredienteResponseDto response = ingredienteService.atualizar(1, dto);

        // Assert
        assertEquals(1, response.getIdIngrediente());
        assertEquals("Açúcar Refinado", response.getNome());
        assertEquals("kg", response.getMedida());
        assertEquals(4.50, response.getPreco());
        verify(ingredienteRepository).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar ingrediente que não existe")
    void naoDeveAtualizarIngredienteInexistente() {
        // Arrange
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Açúcar", "kg", 4.00);
        when(ingredienteRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> ingredienteService.atualizar(99, dto));
        verify(ingredienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar ingrediente com nome duplicado")
    void naoDeveAtualizarIngredienteComNomeDuplicado() {
        // Arrange
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Farinha", "kg", 5.00);

        when(ingredienteRepository.existsById(1)).thenReturn(true);
        when(ingredienteRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot("Farinha", 1))
                .thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitoException.class, () -> ingredienteService.atualizar(1, dto));
        verify(ingredienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve excluir um ingrediente com sucesso")
    void deveExcluirIngredienteComSucesso() {
        // Arrange
        when(ingredienteRepository.existsById(1)).thenReturn(true);

        // Act
        ingredienteService.excluir(1);

        // Assert
        verify(ingredienteRepository).deleteById(1);
    }

    @Test
    @DisplayName("Não deve excluir ingrediente que não existe")
    void naoDeveExcluirIngredienteInexistente() {
        // Arrange
        when(ingredienteRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(FalhaAutenticacaoException.class, () -> ingredienteService.excluir(99));
        verify(ingredienteRepository, never()).deleteById(any());
    }
}
