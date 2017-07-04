package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 23/09/2016.
 */

public class Praga {

    private Integer Id_plaga;
    private String Nombre;
    private String Caracteristicas;
    private String Sintomas;
    private String Tratamiento;
    private String Clase;
    private String Descripcion;
    private String Prevencion;
    private String estado;

    public Praga() {
    }

    public Praga(Integer id_plaga, String nombre, String caracteristicas, String sintomas, String tratamiento, String clase, String descripcion, String prevencion) {
        Id_plaga = id_plaga;
        Nombre = nombre;
        Caracteristicas = caracteristicas;
        Sintomas = sintomas;
        Tratamiento = tratamiento;
        Clase = clase;
        Descripcion = descripcion;
        Prevencion = prevencion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_plaga() {
        return Id_plaga;
    }

    public void setId_plaga(Integer id_plaga) {
        Id_plaga = id_plaga;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCaracteristicas() {
        return Caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        Caracteristicas = caracteristicas;
    }

    public String getSintomas() {
        return Sintomas;
    }

    public void setSintomas(String sintomas) {
        Sintomas = sintomas;
    }

    public String getTratamiento() {
        return Tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        Tratamiento = tratamiento;
    }

    public String getClase() {
        return Clase;
    }

    public void setClase(String clase) {
        Clase = clase;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrevencion() {
        return Prevencion;
    }

    public void setPrevencion(String prevencion) {
        Prevencion = prevencion;
    }
}
