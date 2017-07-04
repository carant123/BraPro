package projeto.undercode.com.proyectobrapro.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import projeto.undercode.com.proyectobrapro.database.HelperDB;

import static projeto.undercode.com.proyectobrapro.database.HelperDB.DATABASE_NAME;

/**
 * Created by Level on 07/03/2017.
 */

public class controllerDB {

    protected static final String SELECT = "SELECT ";
    protected static final String FROM = " FROM ";
    protected static final String WHERE = " WHERE ";
    protected static final String AND = " AND ";

    protected SQLiteDatabase db;
    protected HelperDB helperDB;

    protected Context mContext;

    public controllerDB(Context context) {
        //this.mContext = context;
        Log.d("this.db","create1");
        //this.helperDB = new HelperDB(context);
        Log.d("this.db","create2");
        //this.db = helperDB.getWritableDatabase();
        Log.d("this.db","create3");

    }

    public HelperDB getHelperDB() {
        return this.helperDB;
    }

    public void CloseDb() {
        try {
            this.db.close();
        } catch (Exception ex) {
        }
        try {
            this.db.releaseReference();
        } catch (Exception ex) {
        }
    }

    protected String encripta(String valor) {
        String valor2 = valor;
        if (valor2 == null) {
            valor2 = "";
        }
        if (valor2.equals("")) {
            return valor2;
        }
        String retorno = "";
        try {
            while (true) {
                String letra = valor2.substring(0, 1);
                valor2 = valor2.substring(1);
                int letra2 = letra.hashCode();
                letra2 += 166;
                String letra3 = Integer.toString(letra2);
                while (true) {
                    if (letra3.length() == 3) {
                        break;
                    }
                    letra3 = "0" + letra3;
                }
                retorno += letra3;
                if (valor2 == "") {
                    break;
                }
            }
        } catch (Exception ex) {
        }
        return retorno;
    }

    protected String decripta(String texto) {
        String retorno = "";
        String stexto = texto;
        if (stexto == "") {
            return stexto;
        }
        try {
            while (true) {
                String letra = stexto.substring(0, 3);
                int snumero = Integer.parseInt(letra);
                snumero -= 166;
                retorno += (char) snumero;
                stexto = stexto.substring(3);
                if (stexto == "") {
                    break;
                }
            }
        } catch (Exception ex) {
        }
        return retorno;
    }
}

