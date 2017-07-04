package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 13/07/2016.
 */
public class MenuModel {
    private int id_menu;
    private String nombre;
    private String image;

    public MenuModel() {
    }

    public MenuModel(int id_menu, String nombre, String image) {
        this.id_menu = id_menu;
        this.nombre = nombre;
        this.image = image;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
