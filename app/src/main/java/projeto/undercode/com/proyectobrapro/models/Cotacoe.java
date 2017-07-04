package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 23/09/2016.
 */

public class Cotacoe {

    private Integer id_cotacoes;
    private String cosecha;
    private String periodo;
    private String cotacao;
    private String diferenca;
    private String fechamento;
    private String data_atualizacao;

    public Cotacoe() {
    }

    public Cotacoe(Integer id_cotacoes, String cosecha, String periodo, String cotacao, String diferenca, String fechamento, String data_atualizacao) {
        this.id_cotacoes = id_cotacoes;
        this.cosecha = cosecha;
        this.periodo = periodo;
        this.cotacao = cotacao;
        this.diferenca = diferenca;
        this.fechamento = fechamento;
        this.data_atualizacao = data_atualizacao;
    }

    public Integer getId_cotacoes() {
        return id_cotacoes;
    }

    public void setId_cotacoes(Integer id_cotacoes) {
        this.id_cotacoes = id_cotacoes;
    }

    public String getCosecha() {
        return cosecha;
    }

    public void setCosecha(String cosecha) {
        this.cosecha = cosecha;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCotacao() {
        return cotacao;
    }

    public void setCotacao(String cotacao) {
        this.cotacao = cotacao;
    }

    public String getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(String diferenca) {
        this.diferenca = diferenca;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public String getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(String data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}
