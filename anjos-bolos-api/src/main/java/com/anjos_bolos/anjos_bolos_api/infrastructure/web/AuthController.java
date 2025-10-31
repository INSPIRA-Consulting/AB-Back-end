package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioTokenResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Autenticação de Usuários na API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;
    private final AuthenticateUsuarioUseCase authenticateUsuarioUseCase;

    public AuthController(LoginUsuarioUseCase loginUsuarioUseCase, AuthenticateUsuarioUseCase authenticateUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.authenticateUsuarioUseCase = authenticateUsuarioUseCase;
    }

    @Operation(summary = "Autenticar Usuário", description = "Verifica se o Usuário existe no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @GetMapping
    public ResponseEntity<UsuarioTokenResponseDTO> login(@RequestParam String email, @RequestParam String senha) {
        LoginUsuarioCommand loginCommand = UsuarioEntityMapper.toCommand(email, senha);
        Usuario usuario = loginUsuarioUseCase.execute(loginCommand);
        String token = authenticateUsuarioUseCase.execute(usuario);

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toTokenDTO(usuario, token));
    }

}