package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 11/10/2016.
 */

public class Tax {

    private Integer id_taxa;
    private Integer ano;
    private Integer mes;
    private Integer dia;
    private String mes_descricao;
    private Integer taxa;
    private String data_atualizacao;

    public Tax() {
    }

    public Tax(Integer id_taxa, Integer ano, Integer mes, Integer dia, String mes_descricao, Integer taxa, String data_atualizacao) {
        this.id_taxa = id_taxa;
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.mes_descricao = mes_descricao;
        this.taxa = taxa;
        this.data_atualizacao = data_atualizacao;
    }

    public Integer getId_taxa() {
        return id_taxa;
    }

    public void setId_taxa(Integer id_taxa) {
        this.id_taxa = id_taxa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getMes_descricao() {
        return mes_descricao;
    }

    public void setMes_descricao(String mes_descricao) {
        this.mes_descricao = mes_descricao;
    }

    public Integer getTaxa() {
        return taxa;
    }

    public void setTaxa(Integer taxa) {
        this.taxa = taxa;
    }

    public String getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(String data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}
