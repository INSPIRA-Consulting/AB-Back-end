package com.anjos_bolos.anjos_bolos_api.mapper;

import com.anjos_bolos.anjos_bolos_api.dto.UsuarioCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.UsuarioLoginDto;
import com.anjos_bolos.anjos_bolos_api.dto.UsuarioResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCadastroDto dto) {
        Usuario usuario = new Usuario();
        usuario.setFuncao(dto.getFuncao());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public static Usuario toEntity(UsuarioLoginDto dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public static UsuarioResponseDto toResponse(Usuario usuario) {
        UsuarioResponseDto usuarioDto = new UsuarioResponseDto();
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setFuncao(usuario.getFuncao());
        return usuarioDto;
    }



}
