package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.IngredienteCadastroDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.ingrediente.IngredienteResponseDto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.IngredienteService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.web.IngredienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

class IngredienteEntityControllerTest {

    private IngredienteService ingredienteService;
    private IngredienteController ingredienteController;

    @BeforeEach
    void setUp() {
        ingredienteService = mock(IngredienteService.class);
        ingredienteController = new IngredienteController(ingredienteService);
    }

    @Test
    void testCadastrarIngrediente() {
        IngredienteCadastroDto dto = new IngredienteCadastroDto("Açúcar", "kg", 5.0);
        IngredienteResponseDto responseDto = new IngredienteResponseDto(1, "Açúcar", "kg", 5.0);

        when(ingredienteService.cadastrar(dto)).thenReturn(responseDto);

        ResponseEntity<IngredienteResponseDto> response = ingredienteController.cadastrarIngrediente(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto.getIdIngrediente(), response.getBody().getIdIngrediente());
        assertEquals(responseDto.getNome(), response.getBody().getNome());
        assertEquals(responseDto.getMedida(), response.getBody().getMedida());
        assertEquals(responseDto.getPreco(), response.getBody().getPreco());
    }

    @Test
    void testListarIngredientesComIngredientes() {
        List<IngredienteResponseDto> ingredientes = Arrays.asList(
                new IngredienteResponseDto(1, "Açúcar", "kg", 5.0),
                new IngredienteResponseDto(2, "Farinha", "kg", 3.5)
        );

        when(ingredienteService.listar()).thenReturn(ingredientes);

        ResponseEntity<List<IngredienteResponseDto>> response = ingredienteController.listarIngredientes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Farinha", response.getBody().get(1).getNome());
    }

    @Test
    void testListarIngredientesSemIngredientes() {
        when(ingredienteService.listar()).thenReturn(Collections.emptyList());

        ResponseEntity<List<IngredienteResponseDto>> response = ingredienteController.listarIngredientes();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testListarPorNomeIngredienteComResultado() {
        List<IngredienteResponseDto> ingredientes = List.of(
                new IngredienteResponseDto(1, "Açúcar", "kg", 5.0)
        );

        when(ingredienteService.listarPorNome("Açúcar")).thenReturn(ingredientes);

        ResponseEntity<List<IngredienteResponseDto>> response = ingredienteController.listarPorNomeIngrediente("Açúcar");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Açúcar", response.getBody().get(0).getNome());
    }

    @Test
    void testListarPorNomeIngredienteSemResultado() {
        when(ingredienteService.listarPorNome("Desconhecido")).thenReturn(Collections.emptyList());

        ResponseEntity<List<IngredienteResponseDto>> response = ingredienteController.listarPorNomeIngrediente("Desconhecido");

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testAtualizarIngrediente() {
        IngredienteAtualizacaoDto dto = new IngredienteAtualizacaoDto("Chocolate", "g", 2.0);
        IngredienteResponseDto responseDto = new IngredienteResponseDto(1, "Chocolate", "g", 2.0);

        when(ingredienteService.atualizar(1, dto)).thenReturn(responseDto);

        ResponseEntity<IngredienteResponseDto> response = ingredienteController.atualizarIngrediente(1, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Chocolate", response.getBody().getNome());
        assertEquals("g", response.getBody().getMedida());
        assertEquals(2.0, response.getBody().getPreco());
    }

    @Test
    void testExcluirIngrediente() {
        doNothing().when(ingredienteService).excluir(1);

        ResponseEntity<Void> response = ingredienteController.excluirIngrediente(1);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
