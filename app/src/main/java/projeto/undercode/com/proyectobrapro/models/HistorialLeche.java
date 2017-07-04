package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialLeche {

    private Integer id_historial_leche;
    private Integer id_animal;
    private Integer cantidad;
    private String fecha_obtencao;
    private String nombre;

    public HistorialLeche() {
    }

    public HistorialLeche(Integer id_historial_leche, Integer id_animal, Integer cantidad, String fecha_obtencao, String nombre) {
        this.id_historial_leche = id_historial_leche;
        this.id_animal = id_animal;
        this.cantidad = cantidad;
        this.fecha_obtencao = fecha_obtencao;
        this.nombre = nombre;
    }

    public Integer getId_historial_leche() {
        return id_historial_leche;
    }

    public void setId_historial_leche(Integer id_historial_leche) {
        this.id_historial_leche = id_historial_leche;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha_obtencao() {
        return fecha_obtencao;
    }

    public void setFecha_obtencao(String fecha_obtencao) {
        this.fecha_obtencao = fecha_obtencao;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
