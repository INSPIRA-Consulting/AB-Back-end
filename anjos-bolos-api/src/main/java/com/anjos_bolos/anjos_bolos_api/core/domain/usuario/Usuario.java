package com.anjos_bolos.anjos_bolos_api.core.domain.usuario;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;

public class Usuario {
    private Integer id;
    private String nome;
    private CPF cpf;
    private Email email;
    private String senha;
    private Telefone telefone;
    private FuncaoUsuarioEnum funcao;

    public Usuario () {
    }

    public Usuario(String nome, CPF cpf, Email email, String senha, Telefone telefone, FuncaoUsuarioEnum funcao) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.funcao = funcao;
    }

    public Usuario(Integer id, String nome, CPF cpf, Email email, String senha, Telefone telefone, FuncaoUsuarioEnum funcao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.funcao = funcao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public FuncaoUsuarioEnum getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoUsuarioEnum funcao) {
        this.funcao = funcao;
    }
}
