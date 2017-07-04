package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialConsumo {

    private Integer id_historial_consumo;
    private Integer id_animal;
    private Integer id_producto;
    private Integer cantidad_consumida;
    private String fecha_consumo;
    private String nombre;
    private String N_producto;

    public HistorialConsumo() {
    }

    public HistorialConsumo(Integer id_historial_consumo, Integer id_animal, Integer id_producto, Integer cantidad_consumida, String fecha_consumo, String nombre, String n_producto) {
        this.id_historial_consumo = id_historial_consumo;
        this.id_animal = id_animal;
        this.id_producto = id_producto;
        this.cantidad_consumida = cantidad_consumida;
        this.fecha_consumo = fecha_consumo;
        this.nombre = nombre;
        N_producto = n_producto;
    }

    public Integer getId_historial_consumo() {
        return id_historial_consumo;
    }

    public void setId_historial_consumo(Integer id_historial_consumo) {
        this.id_historial_consumo = id_historial_consumo;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getCantidad_consumida() {
        return cantidad_consumida;
    }

    public void setCantidad_consumida(Integer cantidad_consumida) {
        this.cantidad_consumida = cantidad_consumida;
    }

    public String getFecha_consumo() {
        return fecha_consumo;
    }

    public void setFecha_consumo(String fecha_consumo) {
        this.fecha_consumo = fecha_consumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getN_producto() {
        return N_producto;
    }

    public void setN_producto(String n_producto) {
        N_producto = n_producto;
    }
}
