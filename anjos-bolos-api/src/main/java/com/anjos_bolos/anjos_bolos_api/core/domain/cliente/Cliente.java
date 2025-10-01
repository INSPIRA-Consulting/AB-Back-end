package com.anjos_bolos.anjos_bolos_api.core.domain.cliente;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class Cliente {

    private Integer id;
    private CPF cpf;
    private String nome;
    private Telefone telefone;

    public Cliente () {
    }

    public Cliente(CPF cpf, String nome, Telefone telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Cliente(Integer id, CPF cpf, String nome, Telefone telefone) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

}