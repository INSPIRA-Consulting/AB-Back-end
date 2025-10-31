package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.config.jwt.TokenJWTManager;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioJpaAdapter implements UsuarioGateway {

    private final UsuarioJpaRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenJWTManager tokenJWTManager;

    private final AuthenticationManager  authenticationManager;

    public UsuarioJpaAdapter(UsuarioJpaRepository repository, PasswordEncoder passwordEncoder, TokenJWTManager tokenJWTManager, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenJWTManager = tokenJWTManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = repository.save(UsuarioEntityMapper.toEntity(usuario));

        return UsuarioEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return repository.existsByCpf(cpf.toString());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return repository.existsByEmail(email.toString());
    }

    @Override
    public boolean existsByTelefone(Telefone telefone) {
        return repository.existsByTelefone(telefone.toString());
    }

    @Override
    public boolean existsByCpfAndIdNot(CPF cpf, Integer id) {
        return repository.existsByCpfAndIdNot(cpf.toString(), id);
    }

    @Override
    public boolean existsByEmailAndIdNot(Email email, Integer id) {
        return repository.existsByEmailAndIdNot(email.toString(), id);
    }

    @Override
    public boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id) {
        return repository.existsByTelefoneAndIdNot(telefone.toString(), id);
    }

    @Override
    public boolean existsByEmailAndSenha(Email email, String senha) {
        UsuarioEntity entity = repository.findByEmail(email.toString());

        return passwordEncoder.matches(senha, entity.getSenha());
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll()
                .stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario findById(Integer id) {
        return repository.findById(id)
                .map(UsuarioEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(id)));
    }

    @Override
    public List<Usuario> findByNome(String nome) {
        return repository.findByNomeStartingWithIgnoreCase(nome)
                .stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario findByCpf(CPF cpf) {
        UsuarioEntity entity = repository.findByCpf(cpf.toString());

        return UsuarioEntityMapper.toDomain(entity);
    }

    @Override
    public Usuario findByEmail(Email email) {
        UsuarioEntity entity = repository.findByEmail(email.toString());

        return UsuarioEntityMapper.toDomain(entity);
    }

    @Override
    public List<Usuario> findByFuncao(FuncaoUsuarioEnum funcao) {
        return repository.findByFuncao(funcao.getFuncao())
                .stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario update(Usuario usuario) {
        if (!repository.existsById(usuario.getId())) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(usuario.getId()));
        }

        UsuarioEntity entity = repository.save(UsuarioEntityMapper.toEntity(usuario));
        return UsuarioEntityMapper.toDomain(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Usuário com ID [%d] não encontrado.".formatted(id));
        }

        repository.deleteById(id);
    }

    @Override
    public Usuario login(Email email, String senha) {
        UsuarioEntity entity = repository.findByEmail(email.toString());

        Usuario usuario = UsuarioEntityMapper.toDomain(entity);

        return usuario;
    }

    @Override
    public String authenticate(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(usuario.getEmail().toString(), usuario.getSenha());

        final Authentication authentication = authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenJWTManager.generateToken(authentication);
    }

    @Override
    public String findEncodedSenhaByEmail(Email email) {
        UsuarioEntity entity = repository.findByEmail(email.toString());

        return entity.getSenha();
    }

}