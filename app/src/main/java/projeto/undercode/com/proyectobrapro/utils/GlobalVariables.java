package projeto.undercode.com.proyectobrapro.utils;

import android.app.Application;

/**
 * Created by Level on 06/01/2017.
 */

public class GlobalVariables extends Application {

    private int id_usuario;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
