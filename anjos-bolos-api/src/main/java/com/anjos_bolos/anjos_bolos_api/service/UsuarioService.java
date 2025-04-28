package com.anjos_bolos.anjos_bolos_api.service;
import com.anjos_bolos.anjos_bolos_api.config.GerenciadorTokenJwt;
import com.anjos_bolos.anjos_bolos_api.dto.usuario.UsuarioTokenDto;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.exception.CadastroConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.mapper.UsuarioMapper;
import com.anjos_bolos.anjos_bolos_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public UsuarioTokenDto autenticar(Usuario usuario) {
        final var credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha()
        );

        final var authentication = authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = repository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Email ou senha inválidos"
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
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

    public Usuario cadastro(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = repository.findByEmailAndNome(usuario.getEmail(), usuario.getNome());


        if (usuarioCadastrado.isPresent()) {
            throw new CadastroConflitoException("Email já foi cadastrado");
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return repository.save(usuario);
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

