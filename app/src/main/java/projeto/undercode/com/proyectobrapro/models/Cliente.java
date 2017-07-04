package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 23/09/2016.
 */

public class Cliente {

    private Integer id_cliente;
    private Integer id_usuario;
    private String nombre;
    private String organizacion;
    private String numero;
    private String direccion;
    private String area;
    private String cpf;
    private String data_insercao;
    private String estado;

    public Cliente() {
    }

    public Cliente(Integer id_cliente, Integer id_usuario, String nombre, String organizacion, String numero, String direccion, String area, String cpf, String data_insercao) {
        this.id_cliente = id_cliente;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.numero = numero;
        this.direccion = direccion;
        this.area = area;
        this.cpf = cpf;
        this.data_insercao = data_insercao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getData_insercao() {
        return data_insercao;
    }

    public void setData_insercao(String data_insercao) {
        this.data_insercao = data_insercao;
    }
}
