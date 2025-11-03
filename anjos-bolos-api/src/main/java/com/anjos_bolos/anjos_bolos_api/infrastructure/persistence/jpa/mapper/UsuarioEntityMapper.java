package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.core.application.command.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioTokenResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;

public class UsuarioEntityMapper {

    public static UsuarioResponseDTO toDTO(Usuario domain) {
        return new UsuarioResponseDTO(
                domain.getId(),
                domain.getNome(),
                domain.getCpf().toString(),
                domain.getEmail().toString(),
                domain.getSenha(),
                domain.getTelefone().toString(),
                domain.getFuncao().getFuncao()
        );
    }

    public static CreateUsuarioCommand toCommand(UsuarioRequestDTO dto) {
        return new CreateUsuarioCommand(
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.senha(),
                dto.telefone(),
                dto.funcao()
        );
    }

    public static UpdateUsuarioCommand toCommand(Integer id, UsuarioRequestDTO dto) {
        return new UpdateUsuarioCommand(
                id,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.senha(),
                dto.telefone(),
                dto.funcao()
        );
    }

    public static DeleteUsuarioCommand toCommand(Integer id) {
        return new DeleteUsuarioCommand(id);
    }

    public static GetUsuarioByIdQuery toGetUsuarioByIdQuery(Integer id) {
        return new GetUsuarioByIdQuery(id);
    }

    public static ListUsuariosByNomeQuery toListUsuariosByNomeQuery(String nome) {
        return new ListUsuariosByNomeQuery(nome);
    }

    public static GetUsuarioByCpfQuery toGetUsuarioByCpfQuery(String cpf) {
        return new GetUsuarioByCpfQuery(cpf);
    }

    public static GetUsuarioByEmailQuery toGetUsuarioByEmailQuery(String email) {
        return new GetUsuarioByEmailQuery(email);
    }

    public static ListUsuariosByFuncaoQuery toListUsuariosByFuncaoQuery(String funcao) {
        return new ListUsuariosByFuncaoQuery(funcao);
    }

    public static UsuarioEntity toEntity(Usuario domain) {
        return new UsuarioEntity(
                domain.getId(),
                domain.getNome(),
                domain.getCpf().toString(),
                domain.getEmail().toString(),
                domain.getSenha(),
                domain.getTelefone().toString(),
                domain.getFuncao().toString()
        );
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                CPF.of(entity.getCpf()),
                Email.of(entity.getEmail()),
                entity.getSenha(),
                Telefone.of(entity.getTelefone()),
                FuncaoUsuarioEnum.from(entity.getFuncao())
        );
    }

    public static LoginUsuarioCommand toCommand(String email, String senha) {
        return new LoginUsuarioCommand(email, senha);
    }

    public static UsuarioTokenResponseDTO toTokenDTO(Usuario usuario, String token) {
        return new UsuarioTokenResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail().toString(),
                usuario.getFuncao().getFuncao(),
                token
        );
    }

}