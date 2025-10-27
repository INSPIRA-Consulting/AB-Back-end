package school.sptech.bingo.dto;

public class BoloMensagem {
    private Double peso;
    private Double valor;
    private String recheio;
    private String massa;
    private String cobertura;

    // Construtor público sem argumentos
    public BoloMensagem() {
    }

    // Getters e setters
    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getRecheio() {
        return recheio;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public String getMassa() {
        return massa;
    }

    public void setMassa(String massa) {
        this.massa = massa;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    // Opcional: toString para log
    @Override
    public String toString() {
        return "BoloMensagem{" +
                "peso=" + peso +
                ", valor=" + valor +
                ", recheio='" + recheio + '\'' +
                ", massa='" + massa + '\'' +
                ", cobertura='" + cobertura + '\'' +
                '}';
    }
}
