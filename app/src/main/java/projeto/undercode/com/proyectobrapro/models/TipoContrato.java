package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 26/12/2016.
 */

public class TipoContrato {

    private int Id_tipo_contrato;
    private String Nombre;

    public TipoContrato() {
    }

    public TipoContrato(int id_tipo_contrato, String nombre) {
        Id_tipo_contrato = id_tipo_contrato;
        Nombre = nombre;
    }

    public int getId_tipo_contrato() {
        return Id_tipo_contrato;
    }

    public void setId_tipo_contrato(int id_tipo_contrato) {
        Id_tipo_contrato = id_tipo_contrato;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
