package school.sptech.Inspira.Entity;

import jakarta.persistence.*;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingrediente_id;

    private String nome;
    private String medida;
    private Double preco;


    public Integer getIngrediente_id() {
        return ingrediente_id;
    }

    public void setIngrediente_id(Integer ingrediente_id) {
        this.ingrediente_id = ingrediente_id;
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
