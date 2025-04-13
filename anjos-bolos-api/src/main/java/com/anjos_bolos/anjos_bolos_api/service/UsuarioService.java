package com.anjos_bolos.anjos_bolos_api.service;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaCadastroException;
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

    public List<Usuario> listar() {
        return repository.findAll();
    }
    
    
    public Usuario login(Usuario usuario){
        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isEmpty()) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        Usuario usuarioEncontrado = usuarioExistente.get();
        boolean emailIgual = usuarioEncontrado.getEmail().equalsIgnoreCase(usuario.getEmail());
        boolean senhaIgual = usuarioEncontrado.getSenha().equals(usuario.getSenha());

        if (!emailIgual || !senhaIgual) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        usuarioEncontrado.setAutenticado(true);
        return usuarioEncontrado;
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

    public Usuario cadastro(Usuario usuario){

        Optional<Usuario> usuarioCadastrado = repository.findByEmailAndNome(usuario.getEmail(), usuario.getNome());

        if(usuarioCadastrado.isEmpty()){
            Usuario usuarioCadastro = usuarioCadastrado.get();
            repository.save(usuarioCadastro);
        }

        throw new FalhaCadastroException("Email já foi cadastrado");

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
   
        if (usuarioAtualizado.getSenha() != null) {
            usuario.setSenha(usuarioAtualizado.getSenha());
        }
        if (usuarioAtualizado.getFuncao() != null) {
            usuario.setFuncao(usuarioAtualizado.getFuncao());
        }

        return repository.save(usuario);


        }
    }

