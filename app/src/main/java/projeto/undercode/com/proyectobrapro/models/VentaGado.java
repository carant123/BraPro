package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 05/01/2017.
 */

public class VentaGado {

    private Integer id_venta_gado;
    private Integer id_animal;
    private String nombre;
    private Integer precio;
    private Integer id_venta_gado_detalle;
    private String fecha_venta;
    private Integer id_usuario;
    private String estado;

    public VentaGado() {
    }

    public VentaGado(Integer id_venta_gado, Integer id_animal, String nombre, Integer precio, Integer id_venta_gado_detalle, String fecha_venta, Integer id_usuario) {
        this.id_venta_gado = id_venta_gado;
        this.id_animal = id_animal;
        this.nombre = nombre;
        this.precio = precio;
        this.id_venta_gado_detalle = id_venta_gado_detalle;
        this.fecha_venta = fecha_venta;
        this.id_usuario = id_usuario;
    }

    public Integer getId_venta_gado() {
        return id_venta_gado;
    }

    public void setId_venta_gado(Integer id_venta_gado) {
        this.id_venta_gado = id_venta_gado;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getId_venta_gado_detalle() {
        return id_venta_gado_detalle;
    }

    public void setId_venta_gado_detalle(Integer id_venta_gado_detalle) {
        this.id_venta_gado_detalle = id_venta_gado_detalle;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
