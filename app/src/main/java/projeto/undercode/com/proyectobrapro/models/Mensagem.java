package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 21/10/2016.
 */

public class Mensagem {

    private Integer id_mensagens;
    private Integer usuario;
    private String assunto;
    private String mensagem;
    private String data_mensagem;

    public Mensagem(Integer id_mensagens, Integer usuario, String assunto, String mensagem, String data_mensagem) {
        this.id_mensagens = id_mensagens;
        this.usuario = usuario;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.data_mensagem = data_mensagem;
    }

    public Integer getId_mensagens() {
        return id_mensagens;
    }

    public void setId_mensagens(Integer id_mensagens) {
        this.id_mensagens = id_mensagens;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData_mensagem() {
        return data_mensagem;
    }

    public void setData_mensagem(String data_mensagem) {
        this.data_mensagem = data_mensagem;
    }
}
