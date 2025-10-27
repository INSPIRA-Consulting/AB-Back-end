package com.anjos_bolos.anjos_bolos_api.core.domain.receita;

import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.ItemReceita;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Receita {

    private Integer id;
    private String nome;
    private List<ItemReceita> ingredientes;
    private Double custoProducao;
    private TipoReceita tipoReceita;

    public Receita() {
        this.ingredientes = new ArrayList<>();
    }

    public Receita(String nome, List<ItemReceita> ingredientes, TipoReceita tipoReceita) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.custoProducao = calcularCustoProducao();
        this.tipoReceita = tipoReceita;
    }

    public Receita(Integer id, String nome, List<ItemReceita> ingredientes, TipoReceita tipoReceita) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.custoProducao = calcularCustoProducao();
        this.tipoReceita = tipoReceita;
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

    public List<ItemReceita> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ItemReceita> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Double getCustoProducao() {
        return custoProducao;
    }

    public void setCustoProducao(Double custoProducao) {
        this.custoProducao = custoProducao;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public Double calcularCustoProducao() {
        double custoProducao = ingredientes
                .stream()
                .mapToDouble(item -> item.getIngrediente().getCustoMedida() * item.getQuantidade())
                .sum();

        return new BigDecimal(custoProducao)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}