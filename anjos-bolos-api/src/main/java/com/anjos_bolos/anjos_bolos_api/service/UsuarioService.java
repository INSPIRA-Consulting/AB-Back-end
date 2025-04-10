package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.dto.UsuarioLoginDto;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;


    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

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

    public Usuario cadastrar(Usuario usuario) {
        return repository.save(usuario);
    }
//    public boolean isLoginValido(String email, String senha) {
//        autenticado = this.email.equalsIgnoreCase(email) && this.senha.equalsIgnoreCase(senha);
//        return autenticado;
//    }
    public UsuarioLoginDto login(String UsuarioLoginDto, String email, String senha){
        Optional<UsuarioLoginDto> usuarioExistente = repository.findByEmail(email);

        if (usuarioExistente.isEmpty()) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        UsuarioLoginDto usuarioLoginDto = usuarioExistente.get();

        boolean emailIgual = usuarioLoginDto.getEmail().equalsIgnoreCase(email);
        boolean senhaIgual = usuarioLoginDto.getSenha().equals(senha);

        if (!emailIgual || !senhaIgual) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        return usuarioLoginDto;
    }

    public List<Usuario> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Usuario> buscarPorFuncao(Funcao funcao) {
        return repository.findByFuncao(funcao);
    }

    public boolean deletarPorId(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario atualizarPorNome(String nome, Usuario usuarioAtualizado) {
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
