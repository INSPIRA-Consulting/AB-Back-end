package com.anjos_bolos.anjos_bolos_api.mapper;

import com.anjos_bolos.anjos_bolos_api.dto.usuario.UsuarioCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.usuario.UsuarioLoginDto;
import com.anjos_bolos.anjos_bolos_api.dto.usuario.UsuarioResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;

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

    public static UsuarioResponseDto toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioResponseDto usuarioDto = new UsuarioResponseDto();
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setFuncao(usuario.getFuncao());
        return usuarioDto;
    }



}
