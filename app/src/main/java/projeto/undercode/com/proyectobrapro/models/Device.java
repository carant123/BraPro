package projeto.undercode.com.proyectobrapro.models;

import java.util.ArrayList;

/**
 * Created by Level on 14/07/2016.
 */
public class Device {

    private String pim;
    private String imei;

    private ArrayList<Usuario> usuarios;

    public Device(String pim ,String imei) {
        this.pim = pim;
        this.imei = imei;
    }

    // GET'S
    public String getPim() {
        return this.pim;
    }

    public String getImei() {
        return this.imei;
    }

    public ArrayList<Usuario> getUsuarios() {
        return this.usuarios;
    }

    // SET'S
    public void setPim(String pim) {
        this.pim = pim;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
