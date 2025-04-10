package com.anjos_bolos.anjos_bolos_api.controller;

import com.anjos_bolos.anjos_bolos_api.service.UsuarioService;
import com.anjos_bolos.anjos_bolos_api.dto.UsuarioLoginDto;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
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
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = service.listar();


        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity <Usuario> cadastrar(
            @RequestBody Usuario usuario

    ) {

        if (service.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.status(400).build();
        }

        if (service.existePorNome(usuario.getNome())) {
            return ResponseEntity.status(400).build();
        }

        if (service.existePorCpf(usuario.getCpf())) {
            return ResponseEntity.status(400).build();
        }

        Usuario usuarioAtualizado = service.cadastrar(usuario);

        return ResponseEntity.status(201).body(usuarioAtualizado);
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticarLogin(
            @Valid @RequestBody UsuarioLoginDto usuario
    ){
        service.login(usuario.getUsuario(), usuario.getEmail(), usuario.getSenha());
        usuario.setAutenticado(true);
        return ResponseEntity.status(200).build();
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
    public ResponseEntity<Usuario> atualizarPorNome(
            @PathVariable String nome,
            @RequestBody Usuario usuarioAtualizado
    ) {

        Usuario usuario = service.atualizarPorNome(nome, usuarioAtualizado);


        if (usuario == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(usuario);
    }
}
