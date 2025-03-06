package school.sptech.Inspira;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ingrediente_id;

    @ManyToMany(mappedBy = "ingredientes")
    private List<Produto> produtos;

    private String nome;
    private String medida;
    private Double preco;

    public Integer getId() {
        return ingrediente_id;
    }

    public void setId(Integer id) {
        this.ingrediente_id = id;
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
