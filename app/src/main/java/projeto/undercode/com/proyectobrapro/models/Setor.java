package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 22/08/2016.
 */
public class Setor {

    private String id_sector;
    private String id_usuario;
    private String id_cultivo;
    private String Status;
    private String Nombre;
    private String Hectares;
    private String estado;

    public Setor() {

    }

    public Setor(String id_sector, String id_usuario, String id_cultivo, String status, String nombre, String hectares) {
        this.id_sector = id_sector;
        this.id_usuario = id_usuario;
        this.id_cultivo = id_cultivo;
        this.Status = status;
        this.Nombre = nombre;
        this.Hectares = hectares;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_sector() {
        return id_sector;
    }

    public void setId_sector(String id_sector) {
        this.id_sector = id_sector;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_cultivo() {
        return id_cultivo;
    }

    public void setId_cultivo(String id_cultivo) {
        this.id_cultivo = id_cultivo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getHectares() {
        return Hectares;
    }

    public void setHectares(String hectares) {
        Hectares = hectares;
    }
}
