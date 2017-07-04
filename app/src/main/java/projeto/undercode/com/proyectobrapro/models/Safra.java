package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 22/09/2016.
 */
public class Safra {

    private String Id_sector;
    private String Status;
    private String N_Sector;
    private String Hectareas;
    private String Inicio;
    private String Fim;
    private String N_Cosecha;
    private String Id_cosecha;
    private String estado;

    public Safra() {
    }

    public Safra(String id_sector, String status, String n_Sector, String hectareas, String inicio, String fim, String n_Cosecha, String id_cosecha) {
        Id_sector = id_sector;
        Status = status;
        N_Sector = n_Sector;
        Hectareas = hectareas;
        Inicio = inicio;
        Fim = fim;
        N_Cosecha = n_Cosecha;
        Id_cosecha = id_cosecha;
    }

    public String getId_sector() {
        return Id_sector;
    }

    public void setId_sector(String id_sector) {
        Id_sector = id_sector;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getN_Sector() {
        return N_Sector;
    }

    public void setN_Sector(String n_Sector) {
        N_Sector = n_Sector;
    }

    public String getHectareas() {
        return Hectareas;
    }

    public void setHectareas(String hectareas) {
        Hectareas = hectareas;
    }

    public String getInicio() {
        return Inicio;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public String getFim() {
        return Fim;
    }

    public void setFim(String fim) {
        Fim = fim;
    }

    public String getN_Cosecha() {
        return N_Cosecha;
    }

    public void setN_Cosecha(String n_Cosecha) {
        N_Cosecha = n_Cosecha;
    }

    public String getId_cosecha() {
        return Id_cosecha;
    }

    public void setId_cosecha(String id_cosecha) {
        Id_cosecha = id_cosecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}