package school.sptech.Inspira;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer produto_id;

    @ManyToMany(cascade = CascadeType.ALL) // Define relação muitos-para-muitos
    @JoinTable(
            name = "produto_ingrediente", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "produto_id"), // Chave estrangeira para Produto
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id") // Chave estrangeira para Ingrediente
    )
    private List<Ingrediente> ingredientes;
    private String nome;
    private Double valor;


    public Integer getId() {
        return produto_id;
    }

    public void setId(Integer id) {
        this.produto_id = id;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
