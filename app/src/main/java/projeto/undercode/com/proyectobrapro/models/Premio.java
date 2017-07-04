package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 17/10/2016.
 */

public class Premio {

    private Integer id_premio;
    private Integer empleado;
    private String N_Empleado;
    private Integer premio;
    private Integer ano;
    private Integer mes;
    private String mes_descricao;
    private String data_atualizacao;
    private Integer id_usuario;
    private String estado;

    public Premio() {
    }

    public Premio(Integer id_premio, Integer empleado, String n_Empleado, Integer premio, Integer ano, Integer mes, String mes_descricao, String data_atualizacao, Integer id_usuario) {
        this.id_premio = id_premio;
        this.empleado = empleado;
        N_Empleado = n_Empleado;
        this.premio = premio;
        this.ano = ano;
        this.mes = mes;
        this.mes_descricao = mes_descricao;
        this.data_atualizacao = data_atualizacao;
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_premio() {
        return id_premio;
    }

    public void setId_premio(Integer id_premio) {
        this.id_premio = id_premio;
    }

    public Integer getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Integer empleado) {
        this.empleado = empleado;
    }

    public String getN_Empleado() {
        return N_Empleado;
    }

    public void setN_Empleado(String n_Empleado) {
        N_Empleado = n_Empleado;
    }

    public Integer getPremio() {
        return premio;
    }

    public void setPremio(Integer premio) {
        this.premio = premio;
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

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
}
