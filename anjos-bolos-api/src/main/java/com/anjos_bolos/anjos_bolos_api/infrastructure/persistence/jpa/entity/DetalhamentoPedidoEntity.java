package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.receita.ReceitaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Detalhamento_Pedido")
public class DetalhamentoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkItemPedido")
    private ItemPedidoEntity itemPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "fkReceita", referencedColumnName = "id"),
            @JoinColumn(name = "fkIngrediente", referencedColumnName = "fkIngrediente")
    })
    private ReceitaEntity receita;

    private String observacao;

    public DetalhamentoPedidoEntity() {
    }

    public DetalhamentoPedidoEntity(Integer id, ItemPedidoEntity itemPedido, ReceitaEntity receita, String observacao) {
        this.id = id;
        this.itemPedido = itemPedido;
        this.receita = receita;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemPedidoEntity getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedidoEntity itemPedido) {
        this.itemPedido = itemPedido;
    }

    public ReceitaEntity getReceita() {
        return receita;
    }

    public void setReceita(ReceitaEntity receita) {
        this.receita = receita;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}