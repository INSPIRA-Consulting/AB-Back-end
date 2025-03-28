package com.anjos_bolos.anjos_bolos_api.entity;

import jakarta.persistence.*;

    @Entity
    public class Ingrediente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idIngrediente;

        private String nome;
        private String medida;
        private Double preco;

        public Integer getIdIngrediente() {
            return idIngrediente;
        }

        public void setIdIngrediente(Integer idIngrediente) {
            this.idIngrediente = idIngrediente;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getMedida() {
            return medida;
        }

        public void setMedida(String medida) {
            this.medida = medida;
        }

        public Double getPreco() {
            return preco;
        }

        public void setPreco(Double preco) {
            this.preco = preco;
        }
    }
