package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 14/10/2016.
 */

public class Preco {

    private Integer id_precos;
    private String produto;
    private String ano;
    private String mes;
    private String dia;
    private String estado;
    private Integer regiao;
    private Integer preco_dolar;
    private Integer preco_real;
    private Integer taxa;
    private String mes_descricao;
    private String data_atualizacao;

    public Preco() {
    }

    public Preco(Integer id_precos, String produto, String ano, String mes, String dia, String estado, Integer regiao, Integer preco_dolar, Integer preco_real, Integer taxa, String mes_descricao, String data_atualizacao) {
        this.id_precos = id_precos;
        this.produto = produto;
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.estado = estado;
        this.regiao = regiao;
        this.preco_dolar = preco_dolar;
        this.preco_real = preco_real;
        this.taxa = taxa;
        this.mes_descricao = mes_descricao;
        this.data_atualizacao = data_atualizacao;
    }

    public Integer getId_precos() {
        return id_precos;
    }

    public void setId_precos(Integer id_precos) {
        this.id_precos = id_precos;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getRegiao() {
        return regiao;
    }

    public void setRegiao(Integer regiao) {
        this.regiao = regiao;
    }

    public Integer getPreco_dolar() {
        return preco_dolar;
    }

    public void setPreco_dolar(Integer preco_dolar) {
        this.preco_dolar = preco_dolar;
    }

    public Integer getPreco_real() {
        return preco_real;
    }

    public void setPreco_real(Integer preco_real) {
        this.preco_real = preco_real;
    }

    public Integer getTaxa() {
        return taxa;
    }

    public void setTaxa(Integer taxa) {
        this.taxa = taxa;
    }

    public String getMes_descricao() {
        return mes_descricao;
    }

    public void setMes_descricao(String mes_descricao) {
        this.mes_descricao = mes_descricao;
    }

    public String getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(String data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}
