package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 23/09/2016.
 */

public class Maquinaria {

    private Integer Id_maquinaria;
    private Integer Id_usuario;
    private String Nombre;
    private String Registro;
    private String Fecha_Adquisicion;
    private Integer Precio;
    private String Tipo;
    private String Descripcion;
    private String Modelo;
    private Integer costo_mantenimiento;
    private Integer vida_util_horas;
    private Integer vida_util_ano;
    private Integer potencia_maquinaria;
    private String tipo_adquisicion;
    private String estado;

    public Maquinaria() {
    }

    public Maquinaria(Integer id_maquinaria, Integer id_usuario, String nombre, String registro, String fecha_Adquisicion, Integer precio, String tipo, String descripcion, String modelo, Integer costo_mantenimiento, Integer vida_util_horas, Integer vida_util_ano, Integer potencia_maquinaria, String tipo_adquisicion) {
        Id_maquinaria = id_maquinaria;
        Id_usuario = id_usuario;
        Nombre = nombre;
        Registro = registro;
        Fecha_Adquisicion = fecha_Adquisicion;
        Precio = precio;
        Tipo = tipo;
        Descripcion = descripcion;
        Modelo = modelo;
        this.costo_mantenimiento = costo_mantenimiento;
        this.vida_util_horas = vida_util_horas;
        this.vida_util_ano = vida_util_ano;
        this.potencia_maquinaria = potencia_maquinaria;
        this.tipo_adquisicion = tipo_adquisicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_maquinaria() {
        return Id_maquinaria;
    }

    public void setId_maquinaria(Integer id_maquinaria) {
        Id_maquinaria = id_maquinaria;
    }

    public Integer getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        Id_usuario = id_usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRegistro() {
        return Registro;
    }

    public void setRegistro(String registro) {
        Registro = registro;
    }

    public String getFecha_Adquisicion() {
        return Fecha_Adquisicion;
    }

    public void setFecha_Adquisicion(String fecha_Adquisicion) {
        Fecha_Adquisicion = fecha_Adquisicion;
    }

    public Integer getPrecio() {
        return Precio;
    }

    public void setPrecio(Integer precio) {
        Precio = precio;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public Integer getCosto_mantenimiento() {
        return costo_mantenimiento;
    }

    public void setCosto_mantenimiento(Integer costo_mantenimiento) {
        this.costo_mantenimiento = costo_mantenimiento;
    }

    public Integer getVida_util_horas() {
        return vida_util_horas;
    }

    public void setVida_util_horas(Integer vida_util_horas) {
        this.vida_util_horas = vida_util_horas;
    }

    public Integer getVida_util_ano() {
        return vida_util_ano;
    }

    public void setVida_util_ano(Integer vida_util_ano) {
        this.vida_util_ano = vida_util_ano;
    }

    public Integer getPotencia_maquinaria() {
        return potencia_maquinaria;
    }

    public void setPotencia_maquinaria(Integer potencia_maquinaria) {
        this.potencia_maquinaria = potencia_maquinaria;
    }

    public String getTipo_adquisicion() {
        return tipo_adquisicion;
    }

    public void setTipo_adquisicion(String tipo_adquisicion) {
        this.tipo_adquisicion = tipo_adquisicion;
    }
}
