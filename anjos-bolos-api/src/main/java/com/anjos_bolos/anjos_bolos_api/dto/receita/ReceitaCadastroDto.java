package com.anjos_bolos.anjos_bolos_api.dto.receita;

import com.anjos_bolos.anjos_bolos_api.entity.ReceitaPrimaryKey;

public class ReceitaCadastroDto {

    private ReceitaPrimaryKey idReceita;

    private Double quantidade;

    public ReceitaCadastroDto() {
    }

    public ReceitaCadastroDto(ReceitaPrimaryKey idReceita, Double quantidade) {
        this.idReceita = idReceita;
        this.quantidade = quantidade;
    }

    public ReceitaPrimaryKey getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(ReceitaPrimaryKey idReceita) {
        this.idReceita = idReceita;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
