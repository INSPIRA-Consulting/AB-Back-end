package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;


@Entity
@Table (name = "Usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private String senha;
    @Column(unique = true)
    private String telefone;
    private String funcao;


    public UsuarioEntity() {
    }

    public UsuarioEntity(Integer id, String nome, String cpf, String email, String senha, String telefone, String funcao) {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

}