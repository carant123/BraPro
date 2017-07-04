package projeto.undercode.com.proyectobrapro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Level on 07/03/2017.
 */

public class HelperDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "braprodb.db";

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("HelperDB","created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("HelperDB onCreate","created");
        Log.v("INFO1","creating db");

        try {
            Log.d("HelperDB onCreate","created");
            db.execSQL(ScriptDB.CREATE_ALERTA_PLAGA);
            db.execSQL(ScriptDB.CREATE_ANIMAL);
            db.execSQL(ScriptDB.CREATE_CLIENTE);
            db.execSQL(ScriptDB.CREATE_COMPRA);
            db.execSQL(ScriptDB.CREATE_USUARIOS);
            db.execSQL(ScriptDB.CREATE_CONSULTA_SOLO);
            db.execSQL(ScriptDB.CREATE_COORDENADAS);
            db.execSQL(ScriptDB.CREATE_COSECHA);
            db.execSQL(ScriptDB.CREATE_COTACOES);
            db.execSQL(ScriptDB.CREATE_CULTIVO);
            db.execSQL(ScriptDB.CREATE_DESPESAS);
            db.execSQL(ScriptDB.CREATE_EMPLEADO);
            db.execSQL(ScriptDB.CREATE_HISTORIAL_CONSUMO);
            db.execSQL(ScriptDB.CREATE_HISTORIAL_ENGORDE);
            db.execSQL(ScriptDB.CREATE_HISTORIAL_LECHE);
            db.execSQL(ScriptDB.CREATE_LOTE_GADO);
            db.execSQL(ScriptDB.CREATE_MAPEAR);
            db.execSQL(ScriptDB.CREATE_MAQUINARIA);
            db.execSQL(ScriptDB.CREATE_MENU);
            db.execSQL(ScriptDB.CREATE_NEGOCIACOES);
            db.execSQL(ScriptDB.CREATE_PARTO);
            db.execSQL(ScriptDB.CREATE_PLAGA);
            db.execSQL(ScriptDB.CREATE_PRECOS);
            db.execSQL(ScriptDB.CREATE_PREMIOS);
            db.execSQL(ScriptDB.CREATE_PRODUCTO);
            db.execSQL(ScriptDB.CREATE_SECTOR);
            db.execSQL(ScriptDB.CREATE_TAREA);
            db.execSQL(ScriptDB.CREATE_TAXAS);
            db.execSQL(ScriptDB.CREATE_TIPO_CONTRATO);
            db.execSQL(ScriptDB.CREATE_TIPO_CUSTO_BALANCE);
            db.execSQL(ScriptDB.CREATE_TIPO_DESPESA);
            db.execSQL(ScriptDB.CREATE_TIPO_DESPESA_TEMPO);
            db.execSQL(ScriptDB.CREATE_TIPO_PRODUCTO);
            db.execSQL(ScriptDB.CREATE_TIPO_TAREA);
            db.execSQL(ScriptDB.CREATE_VENTA_GADO);
            db.execSQL(ScriptDB.CREATE_VENTA_GADO_DETALLE);
            db.execSQL(ScriptDB.CREATE_VISITAS);

            Log.d("HelperDB onCreate1","created");

        }catch (Exception e){
            Log.d("PROBLEM IN ", "TABLE'S CREATION");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Run when database is upgraded / changed, like drop tables, add tables etc.
        Log.d("onUpgrade","onUpgrade");
        //db.execSQL(ScriptDB.DELETE_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS alerta_plaga");
        db.execSQL("DROP TABLE IF EXISTS animal");
        db.execSQL("DROP TABLE IF EXISTS cliente");
        db.execSQL("DROP TABLE IF EXISTS compra");
        db.execSQL("DROP TABLE IF EXISTS consulta_solo");
        db.execSQL("DROP TABLE IF EXISTS coordenadas");
        db.execSQL("DROP TABLE IF EXISTS cosecha");
        db.execSQL("DROP TABLE IF EXISTS cotacoes");
        db.execSQL("DROP TABLE IF EXISTS cultivo");
        db.execSQL("DROP TABLE IF EXISTS despesas");
        db.execSQL("DROP TABLE IF EXISTS empleado");
        db.execSQL("DROP TABLE IF EXISTS historial_consumo");
        db.execSQL("DROP TABLE IF EXISTS historial_engorde");
        db.execSQL("DROP TABLE IF EXISTS historial_leche");
        db.execSQL("DROP TABLE IF EXISTS lote_gado");
        db.execSQL("DROP TABLE IF EXISTS mapear");
        db.execSQL("DROP TABLE IF EXISTS maquinaria");
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS negociacoes");
        db.execSQL("DROP TABLE IF EXISTS parto");
        db.execSQL("DROP TABLE IF EXISTS plaga");
        db.execSQL("DROP TABLE IF EXISTS precos");
        db.execSQL("DROP TABLE IF EXISTS premios");
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS sector");
        db.execSQL("DROP TABLE IF EXISTS tarea");
        db.execSQL("DROP TABLE IF EXISTS taxas");
        db.execSQL("DROP TABLE IF EXISTS tipo_contrato");
        db.execSQL("DROP TABLE IF EXISTS tipo_custo_balance");
        db.execSQL("DROP TABLE IF EXISTS tipo_despesa");
        db.execSQL("DROP TABLE IF EXISTS tipo_despesa_tempo");
        db.execSQL("DROP TABLE IF EXISTS tipo_producto");
        db.execSQL("DROP TABLE IF EXISTS tipo_tarea");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS venta_gado");
        db.execSQL("DROP TABLE IF EXISTS venta_gado_detalle");
        db.execSQL("DROP TABLE IF EXISTS visitas");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }





}
