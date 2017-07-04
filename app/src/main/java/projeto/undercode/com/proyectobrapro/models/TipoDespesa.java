package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 26/12/2016.
 */

public class TipoDespesa {

    private int Id_tipo_despesa;
    private String Nombre;

    public TipoDespesa() {
    }

    public TipoDespesa(int id_tipo_despesa, String nombre) {
        Id_tipo_despesa = id_tipo_despesa;
        Nombre = nombre;
    }

    public int getId_tipo_despesa() {
        return Id_tipo_despesa;
    }

    public void setId_tipo_despesa(int id_tipo_despesa) {
        Id_tipo_despesa = id_tipo_despesa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
