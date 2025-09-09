package com.anjos_bolos.anjos_bolos_api.core.domain.receita;

import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.ItemReceita;
import com.anjos_bolos.anjos_bolos_api.core.domain.tipo_receita.TipoReceita;

import java.util.ArrayList;
import java.util.List;

public class Receita {
    private Integer id;
    private List<ItemReceita> ingredientes;
    private TipoReceita tipoReceita;

    public Receita() {
        this.ingredientes = new ArrayList<>();
    }

    public Receita(Integer id, List<ItemReceita> ingredientes, TipoReceita tipoReceita) {
        this.id = id;
        this.ingredientes = new ArrayList<>();
        this.tipoReceita = tipoReceita;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemReceita> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ItemReceita> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }
}
