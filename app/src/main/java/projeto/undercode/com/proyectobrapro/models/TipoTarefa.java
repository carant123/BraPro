package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 07/12/2016.
 */

public class TipoTarefa {

    private Integer Id_tipo_tarea;
    private String Nombre;

    public TipoTarefa() {
    }

    public TipoTarefa(Integer id_tipo_tarea, String nombre) {
        Id_tipo_tarea = id_tipo_tarea;
        Nombre = nombre;
    }

    public Integer getId_tipo_tarea() {
        return Id_tipo_tarea;
    }

    public void setId_tipo_tarea(Integer id_tipo_tarea) {
        Id_tipo_tarea = id_tipo_tarea;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
