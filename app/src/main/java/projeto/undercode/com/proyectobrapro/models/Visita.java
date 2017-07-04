package projeto.undercode.com.proyectobrapro.models;

import java.net.Inet4Address;

/**
 * Created by Level on 04/10/2016.
 */

public class Visita {

    private Integer id_visita;
    private Integer usuario;
    private String latitude;
    private String longitude;
    private String pim;
    private String imei;
    private String versao;
    private Integer cliente;
    private String N_Cliente;
    private String motivo;
    private String data_agenda;
    private String data_visita;
    private String resultado;
    private Integer deslocamento;
    private String situacao;
    private String obs;
    private Integer cadastrante;
    private String estado;

    public Visita() {
    }

    public Visita(Integer id_visita, Integer usuario, String latitude, String longitude, String pim, String imei, String versao, Integer cliente, String n_Cliente, String motivo, String data_agenda, String data_visita, String resultado, Integer deslocamento, String situacao, String obs, Integer cadastrante) {
        this.id_visita = id_visita;
        this.usuario = usuario;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pim = pim;
        this.imei = imei;
        this.versao = versao;
        this.cliente = cliente;
        N_Cliente = n_Cliente;
        this.motivo = motivo;
        this.data_agenda = data_agenda;
        this.data_visita = data_visita;
        this.resultado = resultado;
        this.deslocamento = deslocamento;
        this.situacao = situacao;
        this.obs = obs;
        this.cadastrante = cadastrante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_visita() {
        return id_visita;
    }

    public void setId_visita(Integer id_visita) {
        this.id_visita = id_visita;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPim() {
        return pim;
    }

    public void setPim(String pim) {
        this.pim = pim;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public String getN_Cliente() {
        return N_Cliente;
    }

    public void setN_Cliente(String n_Cliente) {
        N_Cliente = n_Cliente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getData_agenda() {
        return data_agenda;
    }

    public void setData_agenda(String data_agenda) {
        this.data_agenda = data_agenda;
    }

    public String getData_visita() {
        return data_visita;
    }

    public void setData_visita(String data_visita) {
        this.data_visita = data_visita;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(Integer deslocamento) {
        this.deslocamento = deslocamento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getCadastrante() {
        return cadastrante;
    }

    public void setCadastrante(Integer cadastrante) {
        this.cadastrante = cadastrante;
    }
}
