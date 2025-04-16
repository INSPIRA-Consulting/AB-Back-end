package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.dto.usuario.*;
import com.anjos_bolos.anjos_bolos_api.service.UsuarioService;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.mapper.UsuarioMapper;
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

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar() {
        List<Usuario> usuarios = service.listar();


        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<UsuarioListagemDto> usuariosDto = UsuarioMapper.toListagemDtos(usuarios);
        return ResponseEntity.ok(usuariosDto);
    }

    @PostMapping
    public ResponseEntity <UsuarioResponseDto> cadastrarUsuario(
            @RequestBody UsuarioCadastroDto usuarioCadastrado

            ) {
        Usuario entity = UsuarioMapper.toEntity(usuarioCadastrado);
        Usuario response = service.cadastro(entity);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponse(entity);

        return ResponseEntity.status(201).body(usuarioResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticarLogin(
            @Valid @RequestBody UsuarioLoginDto usuarioLogado
    ){
        Usuario entity = UsuarioMapper.toEntity(usuarioLogado);
        Usuario response = service.login(entity);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponse(entity);
        return ResponseEntity.status(200).body("Login realizado com sucesso");
    }


    @GetMapping("/{nome}")
    public ResponseEntity<List<Usuario>> buscarPorNome(
            @PathVariable String nome
    ) {
        List<Usuario> usuarios = service.buscarPorNome(nome);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/funcao")
    public ResponseEntity<List<Usuario>> buscarPorFuncao(
            @RequestParam Funcao funcao
    ) {
        List<Usuario> usuarios = service.buscarPorFuncao(funcao);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(
            @PathVariable Integer id
    ){
        boolean usuarioExiste = service.deletarPorId(id);

        if(usuarioExiste){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{nome}")
    public ResponseEntity<UsuarioResponseDto> atualizarPorNome(
            @PathVariable String nome,
            @RequestBody UsuarioAtualizacaoDto usuarioDto
    ) {


        Usuario entity = UsuarioMapper.toEntity(usuarioDto);
        Usuario response = service.atualizarPorNome(nome, entity);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponse(entity);



        if (usuarioResponseDto == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(usuarioResponseDto);
    }
}
