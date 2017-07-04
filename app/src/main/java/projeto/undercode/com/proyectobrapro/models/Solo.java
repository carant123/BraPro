package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 11/01/2017.
 */

public class Solo {

    private Integer id_consulta_solo;
    private Integer id_sector;
    private String N_Sector;
    private String fosforo_status;
    private Integer fosforo_value;
    private String potasio_status;
    private Integer potasio_value;
    private String calcio_status;
    private Integer calcio_value;
    private String magnesio_status;
    private Integer magnesio_value;
    private String alumninio_status;
    private Integer alumninio_value;
    private String material_organico_status;
    private Integer material_organico_value;
    private String hidrogeno_status;
    private Integer hidrogeno_value;
    private String potencial_hidrogenionico_status;
    private Integer potencial_hidrogenionico_value;
    private String data_consulta;

    public Solo(Integer id_consulta_solo, Integer id_sector, String n_Sector, String fosforo_status, Integer fosforo_value, String potasio_status, Integer potasio_value, String calcio_status, Integer calcio_value, String magnesio_status, Integer magnesio_value, String alumninio_status, Integer alumninio_value, String material_organico_status, Integer material_organico_value, String hidrogeno_status, Integer hidrogeno_value, String potencial_hidrogenionico_status, Integer potencial_hidrogenionico_value, String data_consulta) {
        this.id_consulta_solo = id_consulta_solo;
        this.id_sector = id_sector;
        N_Sector = n_Sector;
        this.fosforo_status = fosforo_status;
        this.fosforo_value = fosforo_value;
        this.potasio_status = potasio_status;
        this.potasio_value = potasio_value;
        this.calcio_status = calcio_status;
        this.calcio_value = calcio_value;
        this.magnesio_status = magnesio_status;
        this.magnesio_value = magnesio_value;
        this.alumninio_status = alumninio_status;
        this.alumninio_value = alumninio_value;
        this.material_organico_status = material_organico_status;
        this.material_organico_value = material_organico_value;
        this.hidrogeno_status = hidrogeno_status;
        this.hidrogeno_value = hidrogeno_value;
        this.potencial_hidrogenionico_status = potencial_hidrogenionico_status;
        this.potencial_hidrogenionico_value = potencial_hidrogenionico_value;
        this.data_consulta = data_consulta;
    }


    public Integer getId_consulta_solo() {
        return id_consulta_solo;
    }

    public void setId_consulta_solo(Integer id_consulta_solo) {
        this.id_consulta_solo = id_consulta_solo;
    }

    public Integer getId_sector() {
        return id_sector;
    }

    public void setId_sector(Integer id_sector) {
        this.id_sector = id_sector;
    }

    public String getN_Sector() {
        return N_Sector;
    }

    public void setN_Sector(String n_Sector) {
        N_Sector = n_Sector;
    }

    public String getFosforo_status() {
        return fosforo_status;
    }

    public void setFosforo_status(String fosforo_status) {
        this.fosforo_status = fosforo_status;
    }

    public Integer getFosforo_value() {
        return fosforo_value;
    }

    public void setFosforo_value(Integer fosforo_value) {
        this.fosforo_value = fosforo_value;
    }

    public String getPotasio_status() {
        return potasio_status;
    }

    public void setPotasio_status(String potasio_status) {
        this.potasio_status = potasio_status;
    }

    public Integer getPotasio_value() {
        return potasio_value;
    }

    public void setPotasio_value(Integer potasio_value) {
        this.potasio_value = potasio_value;
    }

    public String getCalcio_status() {
        return calcio_status;
    }

    public void setCalcio_status(String calcio_status) {
        this.calcio_status = calcio_status;
    }

    public Integer getCalcio_value() {
        return calcio_value;
    }

    public void setCalcio_value(Integer calcio_value) {
        this.calcio_value = calcio_value;
    }

    public String getMagnesio_status() {
        return magnesio_status;
    }

    public void setMagnesio_status(String magnesio_status) {
        this.magnesio_status = magnesio_status;
    }

    public Integer getMagnesio_value() {
        return magnesio_value;
    }

    public void setMagnesio_value(Integer magnesio_value) {
        this.magnesio_value = magnesio_value;
    }

    public String getAlumninio_status() {
        return alumninio_status;
    }

    public void setAlumninio_status(String alumninio_status) {
        this.alumninio_status = alumninio_status;
    }

    public Integer getAlumninio_value() {
        return alumninio_value;
    }

    public void setAlumninio_value(Integer alumninio_value) {
        this.alumninio_value = alumninio_value;
    }

    public String getMaterial_organico_status() {
        return material_organico_status;
    }

    public void setMaterial_organico_status(String material_organico_status) {
        this.material_organico_status = material_organico_status;
    }

    public Integer getMaterial_organico_value() {
        return material_organico_value;
    }

    public void setMaterial_organico_value(Integer material_organico_value) {
        this.material_organico_value = material_organico_value;
    }

    public String getHidrogeno_status() {
        return hidrogeno_status;
    }

    public void setHidrogeno_status(String hidrogeno_status) {
        this.hidrogeno_status = hidrogeno_status;
    }

    public Integer getHidrogeno_value() {
        return hidrogeno_value;
    }

    public void setHidrogeno_value(Integer hidrogeno_value) {
        this.hidrogeno_value = hidrogeno_value;
    }

    public String getPotencial_hidrogenionico_status() {
        return potencial_hidrogenionico_status;
    }

    public void setPotencial_hidrogenionico_status(String potencial_hidrogenionico_status) {
        this.potencial_hidrogenionico_status = potencial_hidrogenionico_status;
    }

    public Integer getPotencial_hidrogenionico_value() {
        return potencial_hidrogenionico_value;
    }

    public void setPotencial_hidrogenionico_value(Integer potencial_hidrogenionico_value) {
        this.potencial_hidrogenionico_value = potencial_hidrogenionico_value;
    }

    public String getData_consulta() {
        return data_consulta;
    }

    public void setData_consulta(String data_consulta) {
        this.data_consulta = data_consulta;
    }
}
