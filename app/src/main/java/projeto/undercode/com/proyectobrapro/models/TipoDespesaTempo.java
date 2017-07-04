package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 26/12/2016.
 */

public class TipoDespesaTempo {

    private int Id_tipo_despesa_tempo;
    private String Nombre;


    public TipoDespesaTempo() {
    }

    public TipoDespesaTempo(int id_tipo_despesa_tempo, String nombre) {
        Id_tipo_despesa_tempo = id_tipo_despesa_tempo;
        Nombre = nombre;
    }

    public int getId_tipo_despesa_tempo() {
        return Id_tipo_despesa_tempo;
    }

    public void setId_tipo_despesa_tempo(int id_tipo_despesa_tempo) {
        Id_tipo_despesa_tempo = id_tipo_despesa_tempo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
