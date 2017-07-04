package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 24/10/2016.
 */

public class PontoInteresse {

    private Integer id_mapear;
    private Integer usuario;
    private String pim;
    private String imei;
    private String latitude;
    private String longitude;
    private String versao;
    private String endereco;
    private String tipo;
    private String obs;
    private String data_cadastro;
    private String estado;


    public PontoInteresse() {
    }

    public PontoInteresse(Integer id_mapear, Integer usuario, String pim, String imei, String latitude, String longitude, String versao, String endereco, String tipo, String obs, String data_cadastro) {
        this.id_mapear = id_mapear;
        this.usuario = usuario;
        this.pim = pim;
        this.imei = imei;
        this.latitude = latitude;
        this.longitude = longitude;
        this.versao = versao;
        this.endereco = endereco;
        this.tipo = tipo;
        this.obs = obs;
        this.data_cadastro = data_cadastro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_mapear() {
        return id_mapear;
    }

    public void setId_mapear(Integer id_mapear) {
        this.id_mapear = id_mapear;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}
