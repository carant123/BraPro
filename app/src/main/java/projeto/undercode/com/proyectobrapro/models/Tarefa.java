package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 07/12/2016.
 */

public class Tarefa {

    private Integer Id_tarea;
    private Integer Id_usuario;
    private Integer Id_producto;
    private String Nombre_Producto;
    private Integer Id_empleado;
    private String Nombre_Empleado;
    private String Contacto_Empleado;
    private Integer Id_tipo_producto;
    private String Nombre_Tipo_Producto;
    private Integer Id_maquinaria;
    private String Nombre_Maquinaria;
    private Integer Id_tipo_tarea;
    private String Nombre_Tipo_Tarea;
    private Integer Id_sector;
    private String Nombre_Sector;
    private String Nombre_Tarea;
    private String Descripcion;
    private String Fecha_trabajo;
    private Integer horas_trabajadas;
    private Integer hectareas_trabajadas;
    private Integer cantidad_producto;
    private String estado;

    public Tarefa() {
    }

    public Tarefa(Integer id_tarea, Integer id_usuario, Integer id_producto, String nombre_Producto, Integer id_empleado, String nombre_Empleado, String contacto_Empleado, Integer id_tipo_producto, String nombre_Tipo_Producto, Integer id_maquinaria, String nombre_Maquinaria, Integer id_tipo_tarea, String nombre_Tipo_Tarea, Integer id_sector, String nombre_Sector, String nombre_Tarea, String descripcion, String fecha_trabajo, Integer horas_trabajadas, Integer hectareas_trabajadas, Integer cantidad_producto) {
        Id_tarea = id_tarea;
        Id_usuario = id_usuario;
        Id_producto = id_producto;
        Nombre_Producto = nombre_Producto;
        Id_empleado = id_empleado;
        Nombre_Empleado = nombre_Empleado;
        Contacto_Empleado = contacto_Empleado;
        Id_tipo_producto = id_tipo_producto;
        Nombre_Tipo_Producto = nombre_Tipo_Producto;
        Id_maquinaria = id_maquinaria;
        Nombre_Maquinaria = nombre_Maquinaria;
        Id_tipo_tarea = id_tipo_tarea;
        Nombre_Tipo_Tarea = nombre_Tipo_Tarea;
        Id_sector = id_sector;
        Nombre_Sector = nombre_Sector;
        Nombre_Tarea = nombre_Tarea;
        Descripcion = descripcion;
        Fecha_trabajo = fecha_trabajo;
        this.horas_trabajadas = horas_trabajadas;
        this.hectareas_trabajadas = hectareas_trabajadas;
        this.cantidad_producto = cantidad_producto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_tarea() {
        return Id_tarea;
    }

    public void setId_tarea(Integer id_tarea) {
        Id_tarea = id_tarea;
    }

    public Integer getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        Id_usuario = id_usuario;
    }

    public Integer getId_producto() {
        return Id_producto;
    }

    public void setId_producto(Integer id_producto) {
        Id_producto = id_producto;
    }

    public String getNombre_Producto() {
        return Nombre_Producto;
    }

    public void setNombre_Producto(String nombre_Producto) {
        Nombre_Producto = nombre_Producto;
    }

    public Integer getId_empleado() {
        return Id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        Id_empleado = id_empleado;
    }

    public String getNombre_Empleado() {
        return Nombre_Empleado;
    }

    public void setNombre_Empleado(String nombre_Empleado) {
        Nombre_Empleado = nombre_Empleado;
    }

    public String getContacto_Empleado() {
        return Contacto_Empleado;
    }

    public void setContacto_Empleado(String contacto_Empleado) {
        Contacto_Empleado = contacto_Empleado;
    }

    public Integer getId_tipo_producto() {
        return Id_tipo_producto;
    }

    public void setId_tipo_producto(Integer id_tipo_producto) {
        Id_tipo_producto = id_tipo_producto;
    }

    public String getNombre_Tipo_Producto() {
        return Nombre_Tipo_Producto;
    }

    public void setNombre_Tipo_Producto(String nombre_Tipo_Producto) {
        Nombre_Tipo_Producto = nombre_Tipo_Producto;
    }

    public int getId_maquinaria() {
        return Id_maquinaria;
    }

    public void setId_maquinaria(Integer id_maquinaria) {
        Id_maquinaria = id_maquinaria;
    }

    public String getNombre_Maquinaria() {
        return Nombre_Maquinaria;
    }

    public void setNombre_Maquinaria(String nombre_Maquinaria) {
        Nombre_Maquinaria = nombre_Maquinaria;
    }

    public Integer getId_tipo_tarea() {
        return Id_tipo_tarea;
    }

    public void setId_tipo_tarea(Integer id_tipo_tarea) {
        Id_tipo_tarea = id_tipo_tarea;
    }

    public String getNombre_Tipo_Tarea() {
        return Nombre_Tipo_Tarea;
    }

    public void setNombre_Tipo_Tarea(String nombre_Tipo_Tarea) {
        Nombre_Tipo_Tarea = nombre_Tipo_Tarea;
    }

    public Integer getId_sector() {
        return Id_sector;
    }

    public void setId_sector(Integer id_sector) {
        Id_sector = id_sector;
    }

    public String getNombre_Sector() {
        return Nombre_Sector;
    }

    public void setNombre_Sector(String nombre_Sector) {
        Nombre_Sector = nombre_Sector;
    }

    public String getNombre_Tarea() {
        return Nombre_Tarea;
    }

    public void setNombre_Tarea(String nombre_Tarea) {
        Nombre_Tarea = nombre_Tarea;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha_trabajo() {
        return Fecha_trabajo;
    }

    public void setFecha_trabajo(String fecha_trabajo) {
        Fecha_trabajo = fecha_trabajo;
    }

    public Integer getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(Integer horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    public Integer getHectareas_trabajadas() {
        return hectareas_trabajadas;
    }

    public void setHectareas_trabajadas(Integer hectareas_trabajadas) {
        this.hectareas_trabajadas = hectareas_trabajadas;
    }

    public Integer getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(Integer cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }
}
