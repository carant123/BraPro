package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 08/02/2017.
 */

public class Coordenadas {

    private int id_coordenada;
    private String latitude;
    private String longitude;
    private Setor setor;

    public Coordenadas() {
    }

    public Coordenadas(int id_coordenada, String latitude, String longitude, Setor setor) {
        this.id_coordenada = id_coordenada;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setor = setor;
    }

    public int getId_coordenada() {
        return id_coordenada;
    }

    public void setId_coordenada(int id_coordenada) {
        this.id_coordenada = id_coordenada;
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

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
