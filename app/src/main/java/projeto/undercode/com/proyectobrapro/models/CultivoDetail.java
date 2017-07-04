package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 25/09/2016.
 */

public class CultivoDetail {

    private Integer Id_sector;
    private String N_Sector;
    private Integer Id_cultivo;
    private String Inicio;
    private String Fim;
    private Integer Id_cosecha;
    private String N_Cosecha;

    public CultivoDetail(Integer id_sector, String n_Sector, Integer id_cultivo, String inicio, String fim, Integer id_cosecha, String n_Cosecha) {
        Id_sector = id_sector;
        N_Sector = n_Sector;
        Id_cultivo = id_cultivo;
        Inicio = inicio;
        Fim = fim;
        Id_cosecha = id_cosecha;
        N_Cosecha = n_Cosecha;
    }

    public Integer getId_sector() {
        return Id_sector;
    }

    public void setId_sector(Integer id_sector) {
        Id_sector = id_sector;
    }

    public String getN_Sector() {
        return N_Sector;
    }

    public void setN_Sector(String n_Sector) {
        N_Sector = n_Sector;
    }

    public Integer getId_cultivo() {
        return Id_cultivo;
    }

    public void setId_cultivo(Integer id_cultivo) {
        Id_cultivo = id_cultivo;
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

    public Integer getId_cosecha() {
        return Id_cosecha;
    }

    public void setId_cosecha(Integer id_cosecha) {
        Id_cosecha = id_cosecha;
    }

    public String getN_Cosecha() {
        return N_Cosecha;
    }

    public void setN_Cosecha(String n_Cosecha) {
        N_Cosecha = n_Cosecha;
    }
}
