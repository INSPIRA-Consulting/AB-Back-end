package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioLoginResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;

    public AuthController(LoginUsuarioUseCase loginUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }

    @Operation(summary = "Autenticar Usuário", description = "Verifica se o Usuário existe no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @GetMapping
    public ResponseEntity<UsuarioLoginResponseDTO> login(@RequestParam String email, @RequestParam String senha) {
        LoginUsuarioCommand command = UsuarioEntityMapper.toCommand(email, senha);
        Usuario usuario = loginUsuarioUseCase.execute(command);

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toLoginDTO(usuario));
    }

}