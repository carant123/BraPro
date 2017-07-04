package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 19/12/2016.
 */

public class Balance {

    private String tipo_elemento;
    private String custo;
    private String quantidade;
    private String custo_total;
    private String nivel;

    public Balance(String tipo_elemento, String custo, String quantidade, String custo_total, String nivel) {
        this.tipo_elemento = tipo_elemento;
        this.custo = custo;
        this.quantidade = quantidade;
        this.custo_total = custo_total;
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo_elemento() {
        return tipo_elemento;
    }

    public void setTipo_elemento(String tipo_elemento) {
        this.tipo_elemento = tipo_elemento;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getCusto_total() {
        return custo_total;
    }

    public void setCusto_total(String custo_total) {
        this.custo_total = custo_total;
    }
}
