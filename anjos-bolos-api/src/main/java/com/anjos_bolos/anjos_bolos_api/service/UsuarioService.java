package com.anjos_bolos.anjos_bolos_api.service;
import com.anjos_bolos.anjos_bolos_api.controller.mapper.UsuarioMapper;
import com.anjos_bolos.anjos_bolos_api.dto.UsuarioCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.UsuarioLoginDto;
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
    
    
    public UsuarioLoginDto login(String email, String senha){
        Optional<Usuario> usuarioExistente = repository.findByEmail(email);

        if (usuarioExistente.isEmpty()) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        Usuario usuarioLogin= usuarioExistente.get();

        boolean emailIgual = usuarioLogin.getEmail().equalsIgnoreCase(email);
        boolean senhaIgual = usuarioLogin.getSenha().equals(senha);

        if (!emailIgual || !senhaIgual) {
            throw new FalhaAutenticacaoException("Login inválido");
        }

        return UsuarioMapper.toLoginDto(usuarioLogin);
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

    public UsuarioCadastroDto cadastro(String nome, String email, String senha, Funcao funcao){

        Optional<Usuario> usuarioCadastrado = repository.findByEmailAndNome(email, nome);

        if(usuarioCadastrado.isEmpty()){
            Usuario usuarioCadastro = usuarioCadastrado.get();
            repository.save(usuarioCadastro);
            return UsuarioMapper.toCadastroDto(usuarioCadastro);
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

