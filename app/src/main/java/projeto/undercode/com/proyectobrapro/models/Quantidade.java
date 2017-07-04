package projeto.undercode.com.proyectobrapro.models;

/**
 * Created by Level on 15/03/2017.
 */

public class Quantidade {

    private String Modulo;
    private String Quantidade;

    public Quantidade() {
    }

    public Quantidade(String modulo, String quantidade) {
        Modulo = modulo;
        Quantidade = quantidade;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String quantidade) {
        Quantidade = quantidade;
    }

    public String getModulo() {
        return Modulo;
    }

    public void setModulo(String modulo) {
        Modulo = modulo;
    }
}
