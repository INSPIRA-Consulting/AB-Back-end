package com.anjos_bolos.anjos_bolos_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.AutenticacaoService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve carregar usuário por email com sucesso")
    void deveCarregarUsuarioPorEmail() {
        // Arrange
        String email = "grandtheft@auto.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("batataFrita");

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        // Act
        var userDetails = autenticacaoService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("batataFrita", userDetails.getPassword());

        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        String email = "naoexiste@valorant2.com";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> autenticacaoService.loadUserByUsername(email)
        );

        assertEquals(
                String.format("usuario: %s não encontrado", email),
                exception.getMessage()
        );

        verify(usuarioRepository, times(1)).findByEmail(email);
    }
}
