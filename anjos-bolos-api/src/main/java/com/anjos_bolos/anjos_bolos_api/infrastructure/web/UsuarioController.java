package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final UpdateUsuarioUseCase updateUsuarioUseCase;
    private final DeleteUsuarioUseCase deleteUsuarioUseCase;
    private final ListUsuariosUseCase listUsuariosUseCase;
    private final GetUsuarioByIdUseCase getUsuarioByIdUseCase;
    private final GetUsuarioByCpfUseCase getUsuarioByCpfUseCase;
    private final GetUsuarioByEmailUseCase getUsuarioByEmailUseCase;
    private final ListUsuariosByNomeUseCase listUsuariosByNomeUseCase;
    private final ListUsuariosByFuncaoUseCase listUsuariosByFuncaoUseCase;

    public UsuarioController(CreateUsuarioUseCase createUsuarioUseCase, UpdateUsuarioUseCase updateUsuarioUseCase, DeleteUsuarioUseCase deleteUsuarioUseCase, ListUsuariosUseCase listUsuariosUseCase, GetUsuarioByIdUseCase getUsuarioByIdUseCase, GetUsuarioByCpfUseCase getUsuarioByCpfUseCase, GetUsuarioByEmailUseCase getUsuarioByEmailUseCase, ListUsuariosByNomeUseCase listUsuariosByNomeUseCase, ListUsuariosByFuncaoUseCase listUsuariosByFuncaoUseCase) {
        this.createUsuarioUseCase = createUsuarioUseCase;
        this.updateUsuarioUseCase = updateUsuarioUseCase;
        this.deleteUsuarioUseCase = deleteUsuarioUseCase;
        this.listUsuariosUseCase = listUsuariosUseCase;
        this.getUsuarioByIdUseCase = getUsuarioByIdUseCase;
        this.getUsuarioByCpfUseCase = getUsuarioByCpfUseCase;
        this.getUsuarioByEmailUseCase = getUsuarioByEmailUseCase;
        this.listUsuariosByNomeUseCase = listUsuariosByNomeUseCase;
        this.listUsuariosByFuncaoUseCase = listUsuariosByFuncaoUseCase;
    }

    @Operation(summary = "Cadastrar novo Usuário", description = "Cria e salva um novo Usuário no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {
        CreateUsuarioCommand command = UsuarioEntityMapper.toCommand(dto);
        Usuario usuario = createUsuarioUseCase.execute(command);

        return ResponseEntity.status(201).body(UsuarioEntityMapper.toDTO(usuario));
    }

    @Operation(summary = "Listar todos os Usuários", description = "Retorna uma lista com todos os Usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Usuário encontrado")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        ListUsuariosQuery query = new ListUsuariosQuery();
        List<Usuario> usuarios = listUsuariosUseCase.execute(query);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios
                .stream()
                .map(UsuarioEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Usuário por ID", description = "Busca um Usuário que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "204", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Integer id) {
        GetUsuarioByIdQuery query = UsuarioEntityMapper.toGetUsuarioByIdQuery(id);
        Usuario usuario = getUsuarioByIdUseCase.execute(query);

        if (usuario == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toDTO(usuario));
    }

    @Operation(summary = "Buscar Usuário por CPF", description = "Busca um Usuário que contenha o CPF informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "204", description = "Usuário não encontrado")
    })
    @GetMapping("/filtro-cpf")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorCpf(@RequestParam String cpf) {
        GetUsuarioByCpfQuery query = UsuarioEntityMapper.toGetUsuarioByCpfQuery(cpf);
        Usuario usuario = getUsuarioByCpfUseCase.execute(query);

        if (usuario == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toDTO(usuario));
    }

    @Operation(summary = "Buscar Usuário por Email", description = "Busca um Usuário que contenha o Email informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "204", description = "Usuário não encontrado")
    })
    @GetMapping("/filtro-email")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorEmail(@RequestParam String email) {
        GetUsuarioByEmailQuery query = UsuarioEntityMapper.toGetUsuarioByEmailQuery(email);
        Usuario usuario = getUsuarioByEmailUseCase.execute(query);

        if (usuario == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toDTO(usuario));
    }

    @Operation(summary = "Buscar Usuários por Nome", description = "Filtra Usuários que contenham parte do Nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Usuário encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosPorNome(@RequestParam String nome) {
        ListUsuariosByNomeQuery query = UsuarioEntityMapper.toListUsuariosByNomeQuery(nome);
        List<Usuario> usuarios = listUsuariosByNomeUseCase.execute(query);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios
                .stream()
                .map(UsuarioEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Usuários por Função", description = "Filtra Usuários que contenham a Função informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Usuário encontrado")
    })
    @GetMapping("/filtro-funcao")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosPorFuncao(@RequestParam String funcao) {
        ListUsuariosByFuncaoQuery query = UsuarioEntityMapper.toListUsuariosByFuncaoQuery(funcao);
        List<Usuario> usuarios = listUsuariosByFuncaoUseCase.execute(query);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios
                .stream()
                .map(UsuarioEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza um Usuário existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @Parameter(description = "ID do Usuário a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid UsuarioRequestDTO dto
    ) {
        UpdateUsuarioCommand command = UsuarioEntityMapper.toCommand(id, dto);
        Usuario usuario = updateUsuarioUseCase.execute(command);

        return ResponseEntity.status(200).body(UsuarioEntityMapper.toDTO(usuario));
    }

    @Operation(summary = "Excluir Usuário", description = "Remove um Usuário do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(
            @Parameter(description = "ID do Usuário a ser excluído") @PathVariable Integer id
    ) {
        DeleteUsuarioCommand command = UsuarioEntityMapper.toCommand(id);
        deleteUsuarioUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}