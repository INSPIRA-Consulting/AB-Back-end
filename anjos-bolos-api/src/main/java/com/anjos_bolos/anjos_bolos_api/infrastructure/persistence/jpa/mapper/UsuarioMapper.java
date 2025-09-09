package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.dto.usuario.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.*;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Usuario;

import java.util.List;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setFuncao(dto.getFuncao());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public static Usuario toEntity(UsuarioLoginDto dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public static Usuario toEntity(UsuarioAtualizacaoDto dto){
        if(dto == null){
            return null;
        }
        Usuario usuario = new Usuario();
        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }
        if (dto.getSenha() != null) {
            usuario.setSenha(dto.getSenha());
        }
        if (dto.getFuncao() != null) {
            usuario.setFuncao(dto.getFuncao());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        return usuario;

    }

    public static UsuarioResponseDto toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioResponseDto usuarioDto = new UsuarioResponseDto();
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setFuncao(usuario.getFuncao());
        return usuarioDto;
    }

    public static UsuarioListagemDto toListagemDto(Usuario entity){

        if(entity == null){
            return null;
        }
        return new UsuarioListagemDto(
                entity.getIdUsuario(),
                entity.getNome(),
                entity.getEmail(),
                entity.getFuncao()
        );
    }

    public static List<UsuarioListagemDto> toListagemDtos(List<Usuario> entities){

        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(UsuarioMapper::toListagemDto)
                .toList();
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
       UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdUsuario(usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setFuncao(usuario.getFuncao());
        usuarioTokenDto.setToken(token);
        return usuarioTokenDto;
    }



}
