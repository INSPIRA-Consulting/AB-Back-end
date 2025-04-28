package com.anjos_bolos.anjos_bolos_api.dto.receita;

import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.entity.ReceitaPrimaryKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

public class ReceitaListagemDto {

    @EmbeddedId
    private ReceitaPrimaryKey idReceita;

    @ManyToOne @MapsId("fkProduto")
    private Produto produto;

    @ManyToOne @MapsId("fkIngrediente")
    private Ingrediente ingrediente;

    private Double quantidade;

    public ReceitaListagemDto(ReceitaPrimaryKey idReceita, Produto produto, Ingrediente ingrediente, Double quantidade) {
        this.idReceita = idReceita;
        this.produto = produto;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public ReceitaPrimaryKey getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(ReceitaPrimaryKey idReceita) {
        this.idReceita = idReceita;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
