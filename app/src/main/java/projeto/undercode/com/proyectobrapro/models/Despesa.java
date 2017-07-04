package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 17/10/2016.
 */

public class Despesa {

    private Integer id_despesa;
    private Integer usuario;
    private String pim;
    private String imei;
    private String latitude;
    private String longitude;
    private String versao;
    private String valor;
    private String data_despesa;
    private Integer tipo_despesa_tempo;
    private String N_tipo_despesa_tempo;
    private Integer tipo_despesa;
    private String N_tipo_despesa;
    private String estado;

    public Despesa() {
    }

    public Despesa(Integer id_despesa, Integer usuario, String pim, String imei, String latitude, String longitude, String versao, String valor, String data_despesa, Integer tipo_despesa_tempo, String n_tipo_despesa_tempo, Integer tipo_despesa, String n_tipo_despesa) {
        this.id_despesa = id_despesa;
        this.usuario = usuario;
        this.pim = pim;
        this.imei = imei;
        this.latitude = latitude;
        this.longitude = longitude;
        this.versao = versao;
        this.valor = valor;
        this.data_despesa = data_despesa;
        this.tipo_despesa_tempo = tipo_despesa_tempo;
        N_tipo_despesa_tempo = n_tipo_despesa_tempo;
        this.tipo_despesa = tipo_despesa;
        N_tipo_despesa = n_tipo_despesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_despesa() {
        return id_despesa;
    }

    public void setId_despesa(Integer id_despesa) {
        this.id_despesa = id_despesa;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
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

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData_despesa() {
        return data_despesa;
    }

    public void setData_despesa(String data_despesa) {
        this.data_despesa = data_despesa;
    }

    public Integer getTipo_despesa_tempo() {
        return tipo_despesa_tempo;
    }

    public void setTipo_despesa_tempo(Integer tipo_despesa_tempo) {
        this.tipo_despesa_tempo = tipo_despesa_tempo;
    }

    public String getN_tipo_despesa_tempo() {
        return N_tipo_despesa_tempo;
    }

    public void setN_tipo_despesa_tempo(String n_tipo_despesa_tempo) {
        N_tipo_despesa_tempo = n_tipo_despesa_tempo;
    }

    public Integer getTipo_despesa() {
        return tipo_despesa;
    }

    public void setTipo_despesa(Integer tipo_despesa) {
        this.tipo_despesa = tipo_despesa;
    }

    public String getN_tipo_despesa() {
        return N_tipo_despesa;
    }

    public void setN_tipo_despesa(String n_tipo_despesa) {
        N_tipo_despesa = n_tipo_despesa;
    }
}
