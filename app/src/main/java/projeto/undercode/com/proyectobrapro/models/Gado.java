package projeto.undercode.com.proyectobrapro.models;

import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 03/01/2017.
 */

public class Gado {

    private Integer id_animal;
    private Integer id_lote_gado;
    private Integer id_usuario;
    private String nombre;
    private Integer peso_inicial;
    private Integer cod_adquisicao;
    private String tipo_adquisicao;
    private Integer precio;
    private String fecha;
    private String id_parto;
    private String estado;

    public Gado() {
    }

    public Gado(Integer id_animal, Integer id_lote_gado, Integer id_usuario, String nombre, Integer peso_inicial, Integer cod_adquisicao, String tipo_adquisicao, Integer precio, String fecha, String id_parto) {
        this.id_animal = id_animal;
        this.id_lote_gado = id_lote_gado;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.peso_inicial = peso_inicial;
        this.cod_adquisicao = cod_adquisicao;
        this.tipo_adquisicao = tipo_adquisicao;
        this.precio = precio;
        this.fecha = fecha;
        this.id_parto = id_parto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
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

    public Integer getPeso_inicial() {
        return peso_inicial;
    }

    public void setPeso_inicial(Integer peso_inicial) {
        this.peso_inicial = peso_inicial;
    }

    public Integer getCod_adquisicao() {
        return cod_adquisicao;
    }

    public void setCod_adquisicao(Integer cod_adquisicao) {
        this.cod_adquisicao = cod_adquisicao;
    }

    public String getTipo_adquisicao() {
        return tipo_adquisicao;
    }

    public void setTipo_adquisicao(String tipo_adquisicao) {
        this.tipo_adquisicao = tipo_adquisicao;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_parto() {
        return id_parto;
    }

    public void setId_parto(String id_parto) {
        this.id_parto = id_parto;
    }
}
