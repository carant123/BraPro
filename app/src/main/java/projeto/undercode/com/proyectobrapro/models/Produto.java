package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 11/10/2016.
 */

public class Produto {

    private Integer Id_producto;
    private Integer Id_tipo_producto;
    private String Nombre;
    private String N_TipoProducto;
    private String Fecha_registro;
    private String Fecha_expiracion;
    private String Funcion;
    private String Descipcion;
    private String Composicion;
    private String Objeto;
    private String Imagen;
    private String Lote;
    private String Custo;
    private Integer id_usuario;
    private String Kilos;
    private String estado;


    public Produto() {
    }

    public Produto(Integer id_producto, Integer id_tipo_producto, String nombre, String n_TipoProducto, String fecha_registro, String fecha_expiracion, String funcion, String descipcion, String composicion, String objeto, String imagen, String lote, String custo, Integer id_usuario, String kilos) {
        Id_producto = id_producto;
        Id_tipo_producto = id_tipo_producto;
        Nombre = nombre;
        N_TipoProducto = n_TipoProducto;
        Fecha_registro = fecha_registro;
        Fecha_expiracion = fecha_expiracion;
        Funcion = funcion;
        Descipcion = descipcion;
        Composicion = composicion;
        Objeto = objeto;
        Imagen = imagen;
        Lote = lote;
        Custo = custo;
        this.id_usuario = id_usuario;
        Kilos = kilos;
    }

    public Integer getId_producto() {
        return Id_producto;
    }

    public void setId_producto(Integer id_producto) {
        Id_producto = id_producto;
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

    public String getN_TipoProducto() {
        return N_TipoProducto;
    }

    public void setN_TipoProducto(String n_TipoProducto) {
        N_TipoProducto = n_TipoProducto;
    }

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public String getFecha_expiracion() {
        return Fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        Fecha_expiracion = fecha_expiracion;
    }

    public String getFuncion() {
        return Funcion;
    }

    public void setFuncion(String funcion) {
        Funcion = funcion;
    }

    public String getDescipcion() {
        return Descipcion;
    }

    public void setDescipcion(String descipcion) {
        Descipcion = descipcion;
    }

    public String getComposicion() {
        return Composicion;
    }

    public void setComposicion(String composicion) {
        Composicion = composicion;
    }

    public String getObjeto() {
        return Objeto;
    }

    public void setObjeto(String objeto) {
        Objeto = objeto;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getLote() {
        return Lote;
    }

    public void setLote(String lote) {
        Lote = lote;
    }

    public String getCusto() {
        return Custo;
    }

    public void setCusto(String custo) {
        Custo = custo;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getKilos() {
        return Kilos;
    }

    public void setKilos(String kilos) {
        Kilos = kilos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
