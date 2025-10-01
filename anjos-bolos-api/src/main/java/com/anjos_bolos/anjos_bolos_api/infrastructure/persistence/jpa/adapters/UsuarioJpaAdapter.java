package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.IngredienteGateway;
import com.anjos_bolos.anjos_bolos_api.core.adapters.UsuarioGateway;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.IngredienteEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.IngredienteEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.IngredienteJpaRepository;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioJpaAdapter implements UsuarioGateway {

    private final UsuarioJpaRepository repository;

    public UsuarioJpaAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
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
        return repository.existsByCpfAndIdNot(cpf, id);
    }

    @Override
    public boolean existsByEmailAndIdNot(Email email, Integer id) {
        return repository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id) {
        return repository.existsByTelefoneAndIdNot(telefone, id);
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
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario findByCpf(CPF cpf) {
        return repository.findByCpf(cpf.toString())
                .map(UsuarioEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Usuário com CPF [%s] não encontrado.".formatted(cpf)));
    }

    @Override
    public Usuario findByEmail(Email email) {
        return repository.findByEmail(email.toString())
                .map(UsuarioEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Usuário com Email [%s] não encontrado.".formatted(email)));
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

}