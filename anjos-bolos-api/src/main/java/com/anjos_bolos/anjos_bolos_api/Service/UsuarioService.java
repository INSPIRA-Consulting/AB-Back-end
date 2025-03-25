package com.anjos_bolos.anjos_bolos_api.Service;

import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public boolean existePorEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    public boolean existePorNome(String nome) {
        return repository.existsByNomeIgnoreCase(nome);
    }

    public boolean existePorCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }


    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario cadastrar(Usuario usuario){

        return repository.save(usuario);
    }

    public List<Usuario> buscarPorNome(String nome){
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Usuario> buscarPorFuncao(Funcao funcao){
        return repository.findByFuncaoContainingIgnoreCase(funcao);
    }

    public boolean deletarPorId(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario atualizarPorNome(String nome,Usuario usuarioAtualizado){

        Optional<Usuario> usuarioEncontrado = repository.findByNome(nome);


        if (usuarioEncontrado.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioEncontrado.get();

        if (usuarioAtualizado.getNome() != null) {
            usuario.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getEmail() != null) {
            usuario.setEmail(usuarioAtualizado.getEmail());
        }
        if (usuarioAtualizado.getTelefone() != null) {
            usuario.setTelefone(usuarioAtualizado.getTelefone());
        }
        if (usuarioAtualizado.getCpf() != null) {
            usuario.setCpf(usuarioAtualizado.getCpf());
        }
        if (usuarioAtualizado.getSenha() != null) {
            usuario.setSenha(usuarioAtualizado.getSenha());
        }
        if (usuarioAtualizado.getFuncao() != null) {
            usuario.setFuncao(usuarioAtualizado.getFuncao());
        }

        return repository.save(usuario);
    }

}