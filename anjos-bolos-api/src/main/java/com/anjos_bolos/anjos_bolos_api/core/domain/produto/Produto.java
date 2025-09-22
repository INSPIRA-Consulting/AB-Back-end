package com.anjos_bolos.anjos_bolos_api.core.domain.produto;

import com.anjos_bolos.anjos_bolos_api.core.domain.categoria_produto.CategoriaProduto;

public class Produto {
    private Integer id;
    private String nome;
    private Double precoFinal;
    private CategoriaProduto categoriaProduto;
    private String imagemUrl;

    public Produto() {
    }

    public Produto(String nome, Double precoFinal, CategoriaProduto categoriaProduto) {
        this.nome = nome;
        this.precoFinal = precoFinal;
        this.categoriaProduto = categoriaProduto;
    }

    public Produto(Integer id, String nome, Double precoFinal, CategoriaProduto categoriaProduto) {
        this.id = id;
        this.nome = nome;
        this.precoFinal = precoFinal;
        this.categoriaProduto = categoriaProduto;
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

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getImagemUrl() { return imagemUrl; }

    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}
