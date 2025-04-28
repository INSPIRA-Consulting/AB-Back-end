package com.anjos_bolos.anjos_bolos_api.dto.usuario;

import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class UsuarioListagemDto {
    @NotBlank
    @Id
    @Schema(description = "Id usuário", example = "1")
    private Integer idUsuario;
    @NotBlank
    @Schema(description = "Nome do usuário", example = "Anna Rita")
    private String nome;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "ana@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Função do usuário", example = "ADMINISTRADOR")
    @Enumerated(EnumType.STRING)
    private Funcao funcao;


    public UsuarioListagemDto() {
    }

    public UsuarioListagemDto(Integer idUsuario, String nome, String email, Funcao funcao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }
}
