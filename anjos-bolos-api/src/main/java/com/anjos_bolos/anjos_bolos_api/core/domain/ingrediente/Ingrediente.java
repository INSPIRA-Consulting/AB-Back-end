package com.anjos_bolos.anjos_bolos_api.core.domain.ingrediente;

public class Ingrediente {

    private Integer id;
    private String nome;
    private Double custoMedida;

    public Ingrediente() {
    }

    public Ingrediente(String nome, Double valorEmbalagem, Double quantidadeEmbalagem) {
        this.nome = nome;
        this.custoMedida = calcularCustoMedida(valorEmbalagem, quantidadeEmbalagem);
    }

    public Ingrediente(Integer id, String nome, Double custoMedida) {
        this.id = id;
        this.nome = nome;
        this.custoMedida = custoMedida;
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

    public Double getCustoMedida() {
        return custoMedida;
    }

    public void setCustoMedida(Double custoMedida) {
        this.custoMedida = custoMedida;
    }

    public Double calcularCustoMedida(Double valorEmbalagem, Double quantidadeEmbalagem) {
        if (valorEmbalagem == null || valorEmbalagem <= 0.0) {
            throw new IllegalArgumentException("O Valor da embalagem deve ser maior que zero.");
        }

        if (quantidadeEmbalagem == null || quantidadeEmbalagem <= 0.0) {
            throw new IllegalArgumentException("A Quantidade da embalagem deve ser maior que zero.");
        }

        return valorEmbalagem / quantidadeEmbalagem;
    }

}