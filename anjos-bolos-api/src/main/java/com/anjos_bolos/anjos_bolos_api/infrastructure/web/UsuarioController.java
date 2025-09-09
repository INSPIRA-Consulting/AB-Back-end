package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioMapper;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioListagemDto>> listar() {
        List<Usuario> usuarios = service.listar();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(UsuarioMapper.toListagemDtos(usuarios));
    }

    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Conflito ao cadastrar usuário")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(
            @RequestBody UsuarioCadastroDto usuarioCadastrado
    ) {
        Usuario entity = UsuarioMapper.toEntity(usuarioCadastrado);
        Usuario response = service.cadastro(entity);
        return ResponseEntity.status(201).body(UsuarioMapper.toResponse(response));
    }

    @Operation(summary = "Login do usuário", description = "Realiza a autenticação de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> autenticarLogin(
            @Valid @RequestBody UsuarioLoginDto usuarioLogado
    ) {
        final Usuario entity = UsuarioMapper.toEntity(usuarioLogado);
        UsuarioTokenDto usuarioTokenDto = service.autenticar(entity);
        service.autenticar(entity);
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Operation(summary = "Buscar usuários por nome", description = "Busca usuários com base no nome informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado com esse nome")
    })

    @GetMapping("/{nome}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioListagemDto>> buscarPorNome(
            @PathVariable String nome
    ) {
        List<Usuario> usuarios = service.buscarPorNome(nome);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(UsuarioMapper.toListagemDtos(usuarios));
    }

    @Operation(summary = "Buscar usuários por função", description = "Filtra usuários com base na função.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário com essa função")
    })
    @GetMapping("/funcao")
    public ResponseEntity<List<UsuarioListagemDto>> buscarPorFuncao(
            @RequestParam Funcao funcao
    ) {
        List<Usuario> usuarios = service.buscarPorFuncao(funcao);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(UsuarioMapper.toListagemDtos(usuarios));
    }

    @Operation(summary = "Deletar usuário por ID", description = "Remove um usuário com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(
            @PathVariable Integer id
    ) {
        boolean usuarioExiste = service.deletarPorId(id);
        return usuarioExiste ? ResponseEntity.status(204).build() : ResponseEntity.status(404).build();
    }

    @Operation(summary = "Atualizar usuário por nome", description = "Atualiza os dados de um usuário pelo nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{nome}")
    public ResponseEntity<UsuarioResponseDto> atualizarPorNome(
            @PathVariable String nome,
            @RequestBody UsuarioAtualizacaoDto usuarioDto
    ) {
        Usuario entity = UsuarioMapper.toEntity(usuarioDto);
        Usuario response = service.atualizarPorNome(nome, entity);

        if (response == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(UsuarioMapper.toResponse(response));
    }
}
