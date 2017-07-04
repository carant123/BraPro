package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 17/10/2016.
 */

public class TipoProducto {

    private Integer Id_tipo_producto;
    private String Nombre;

    public TipoProducto() {
    }

    public TipoProducto(Integer id_tipo_producto, String nombre) {
        Id_tipo_producto = id_tipo_producto;
        Nombre = nombre;
    }

    public Integer getId_tipo_producto() {
        return Id_tipo_producto;
    }

    public void setId_tipo_producto(Integer id_tipo_producto) {
        Id_tipo_producto = id_tipo_producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
