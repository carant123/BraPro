package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 22/09/2016.
 */
public class Colheita {

    private int imagen;
    private int Id_cosecha;
    private String Nombre;
    private int id_usuario;
    private String estado;

    public Colheita() {
    }

    public Colheita(int id_cosecha, String nombre, int id_usuario) {
        this.Id_cosecha = id_cosecha;
        this.Nombre = nombre;
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_cosecha() {
        return Id_cosecha;
    }

    public void setId_cosecha(int id_cosecha) {
        Id_cosecha = id_cosecha;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
