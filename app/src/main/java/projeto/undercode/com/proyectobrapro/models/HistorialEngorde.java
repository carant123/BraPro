package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialEngorde {

    private Integer id_historial_engorde;
    private Integer id_animal;
    private Integer peso;
    private String fecha_medicion;
    private String nombre;

    public HistorialEngorde() {
    }

    public HistorialEngorde(Integer id_historial_engorde, Integer id_animal, Integer peso, String fecha_medicion, String nombre) {
        this.id_historial_engorde = id_historial_engorde;
        this.id_animal = id_animal;
        this.peso = peso;
        this.fecha_medicion = fecha_medicion;
        this.nombre = nombre;
    }

    public Integer getId_historial_engorde() {
        return id_historial_engorde;
    }

    public void setId_historial_engorde(Integer id_historial_engorde) {
        this.id_historial_engorde = id_historial_engorde;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getFecha_medicion() {
        return fecha_medicion;
    }

    public void setFecha_medicion(String fecha_medicion) {
        this.fecha_medicion = fecha_medicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
