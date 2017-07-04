package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 10/10/2016.
 */

public class Negociacoe {

    private Integer id_negociacoes;
    private Integer usuario;
    private String pim;
    private String imei;
    private String latitude;
    private String longitude;
    private String versao;
    private String cpf;
    private String nome;
    private String tipo_local;
    private String local;
    private Integer produto;
    private Integer taxa;
    private Integer valor_negociado;
    private String data_pagamento;
    private String data_cadastro;
    private String N_produto;
    private String estado;

    public Negociacoe() {
    }

    public Negociacoe(Integer id_negociacoes, Integer usuario, String pim, String imei, String latitude, String longitude, String versao, String cpf, String nome, String tipo_local, String local, Integer produto, Integer taxa, Integer valor_negociado, String data_pagamento, String data_cadastro, String N_produto) {
        this.id_negociacoes = id_negociacoes;
        this.usuario = usuario;
        this.pim = pim;
        this.imei = imei;
        this.latitude = latitude;
        this.longitude = longitude;
        this.versao = versao;
        this.cpf = cpf;
        this.nome = nome;
        this.tipo_local = tipo_local;
        this.local = local;
        this.produto = produto;
        this.taxa = taxa;
        this.valor_negociado = valor_negociado;
        this.data_pagamento = data_pagamento;
        this.data_cadastro = data_cadastro;
        this.N_produto = N_produto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_negociacoes() {
        return id_negociacoes;
    }

    public void setId_negociacoes(Integer id_negociacoes) {
        this.id_negociacoes = id_negociacoes;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_local() {
        return tipo_local;
    }

    public void setTipo_local(String tipo_local) {
        this.tipo_local = tipo_local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public Integer getTaxa() {
        return taxa;
    }

    public void setTaxa(Integer taxa) {
        this.taxa = taxa;
    }

    public Integer getValor_negociado() {
        return valor_negociado;
    }

    public void setValor_negociado(Integer valor_negociado) {
        this.valor_negociado = valor_negociado;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public String getN_produto() {
        return N_produto;
    }

    public void setN_produto(String n_produto) {
        N_produto = n_produto;
    }
}
