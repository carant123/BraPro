package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 21/09/2016.
 */
public class Usuario {
    private String codigo;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String status;
    private String perfil;


    public Usuario() {
    }

    public Usuario(String codigo, String nome, String login, String senha, String email, String status, String perfil) {
        this.codigo = codigo;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.status = status;
        this.perfil = perfil;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
