package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 30/09/2016.
 */

public class AlertaPraga {

    private Integer Id_alerta_plaga;
    private Integer Id_sector;
    private String N_Sector;
    private Integer Id_plaga;
    private String N_plaga;
    private String N_AlertaPlaga;
    private String Fecha_registro;
    private String Descripcion;
    private String Status;
    private Integer id_usuario;
    private String estado;

    public AlertaPraga() {
    }

    public AlertaPraga(Integer id_alerta_plaga, Integer id_sector, String n_Sector, Integer id_plaga, String n_plaga, String n_AlertaPlaga, String fecha_registro, String descripcion, String status, Integer id_usuario) {
        Id_alerta_plaga = id_alerta_plaga;
        Id_sector = id_sector;
        N_Sector = n_Sector;
        Id_plaga = id_plaga;
        N_plaga = n_plaga;
        N_AlertaPlaga = n_AlertaPlaga;
        Fecha_registro = fecha_registro;
        Descripcion = descripcion;
        Status = status;
        this.id_usuario = id_usuario;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_alerta_plaga() {
        return Id_alerta_plaga;
    }

    public void setId_alerta_plaga(Integer id_alerta_plaga) {
        Id_alerta_plaga = id_alerta_plaga;
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

    public Integer getId_plaga() {
        return Id_plaga;
    }

    public void setId_plaga(Integer id_plaga) {
        Id_plaga = id_plaga;
    }

    public String getN_plaga() {
        return N_plaga;
    }

    public void setN_plaga(String n_plaga) {
        N_plaga = n_plaga;
    }

    public String getN_AlertaPlaga() {
        return N_AlertaPlaga;
    }

    public void setN_AlertaPlaga(String n_AlertaPlaga) {
        N_AlertaPlaga = n_AlertaPlaga;
    }

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
