package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 22/12/2016.
 */

public class Balance2 {

    private String tipo_elemento;
    private String custo_total;
    private String tipo_balance;
    private String id_usuario;
    private String data_balance;

    public Balance2(String tipo_elemento, String custo_total, String tipo_balance, String id_usuario, String data_balance) {
        this.tipo_elemento = tipo_elemento;
        this.custo_total = custo_total;
        this.tipo_balance = tipo_balance;
        this.id_usuario = id_usuario;
        this.data_balance = data_balance;
    }

    public String getTipo_elemento() {
        return tipo_elemento;
    }

    public void setTipo_elemento(String tipo_elemento) {
        this.tipo_elemento = tipo_elemento;
    }

    public String getCusto_total() {
        return custo_total;
    }

    public void setCusto_total(String custo_total) {
        this.custo_total = custo_total;
    }

    public String getTipo_balance() {
        return tipo_balance;
    }

    public void setTipo_balance(String tipo_balance) {
        this.tipo_balance = tipo_balance;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getData_balance() {
        return data_balance;
    }

    public void setData_balance(String data_balance) {
        this.data_balance = data_balance;
    }
}
