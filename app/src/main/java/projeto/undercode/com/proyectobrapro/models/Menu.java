package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 24/12/2016.
 */

public class Menu {

    private int id_menu;
    private String nombre;

    public Menu(int id_menu, String nombre) {
        this.id_menu = id_menu;
        this.nombre = nombre;
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
}
