package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 23/09/2016.
 */

public class Empregado {

    private Integer Id_empleado;
    private Integer Id_usuario;
    private String Nombre;
    private String Fecha_contratacion;
    private Integer Edad;
    private String Rol;
    private String Contacto;
    private Integer Salario;
    private String fin_de_contrato;
    private Integer tipo_contrato;
    private String N_tipo_contrato;
    private String estado;

    public Empregado() {
    }

    public Empregado(Integer id_empleado, Integer id_usuario, String nombre, String fecha_contratacion, Integer edad, String rol, String contacto, Integer salario, String fin_de_contrato, Integer tipo_contrato, String n_tipo_contrato) {
        Id_empleado = id_empleado;
        Id_usuario = id_usuario;
        Nombre = nombre;
        Fecha_contratacion = fecha_contratacion;
        Edad = edad;
        Rol = rol;
        Contacto = contacto;
        Salario = salario;
        this.fin_de_contrato = fin_de_contrato;
        this.tipo_contrato = tipo_contrato;
        N_tipo_contrato = n_tipo_contrato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_empleado() {
        return Id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        Id_empleado = id_empleado;
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

    public String getFecha_contratacion() {
        return Fecha_contratacion;
    }

    public void setFecha_contratacion(String fecha_contratacion) {
        Fecha_contratacion = fecha_contratacion;
    }

    public Integer getEdad() {
        return Edad;
    }

    public void setEdad(Integer edad) {
        Edad = edad;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public Integer getSalario() {
        return Salario;
    }

    public void setSalario(Integer salario) {
        Salario = salario;
    }

    public String getFin_de_contrato() {
        return fin_de_contrato;
    }

    public void setFin_de_contrato(String fin_de_contrato) {
        this.fin_de_contrato = fin_de_contrato;
    }

    public Integer getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(Integer tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public String getN_tipo_contrato() {
        return N_tipo_contrato;
    }

    public void setN_tipo_contrato(String n_tipo_contrato) {
        N_tipo_contrato = n_tipo_contrato;
    }
}
