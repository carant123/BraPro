package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 03/01/2017.
 */

public class LoteGado {

    private Integer id_lote_gado;
    private Integer id_usuario;
    private String nombre;
    private String descripcao;
    private String cantidad;
    private String estado;

    public LoteGado() {
    }

    public LoteGado(Integer id_lote_gado, Integer id_usuario, String nombre, String descripcao, String cantidad) {
        this.id_lote_gado = id_lote_gado;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.descripcao = descripcao;
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_lote_gado() {
        return id_lote_gado;
    }

    public void setId_lote_gado(Integer id_lote_gado) {
        this.id_lote_gado = id_lote_gado;
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

    public String getDescripcao() {
        return descripcao;
    }

    public void setDescripcao(String descripcao) {
        this.descripcao = descripcao;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
