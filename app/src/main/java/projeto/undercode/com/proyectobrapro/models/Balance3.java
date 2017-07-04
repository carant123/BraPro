package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 22/12/2016.
 */

public class Balance3 {

    private String total;
    private String fecha;

    public Balance3(String total, String fecha) {
        this.total = total;
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
