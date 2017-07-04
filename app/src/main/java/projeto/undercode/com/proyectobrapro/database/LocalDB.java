package projeto.undercode.com.proyectobrapro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import projeto.undercode.com.proyectobrapro.base.controllerDB;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.Coordenadas;
import projeto.undercode.com.proyectobrapro.models.Despesa;
import projeto.undercode.com.proyectobrapro.models.Empregado;
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;
import projeto.undercode.com.proyectobrapro.models.HistorialEngorde;
import projeto.undercode.com.proyectobrapro.models.HistorialLeche;
import projeto.undercode.com.proyectobrapro.models.LoteGado;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.models.Menu;
import projeto.undercode.com.proyectobrapro.models.MenuModel;
import projeto.undercode.com.proyectobrapro.models.Negociacoe;
import projeto.undercode.com.proyectobrapro.models.PontoInteresse;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.models.Preco;
import projeto.undercode.com.proyectobrapro.models.Premio;
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.models.Quantidade;
import projeto.undercode.com.proyectobrapro.models.Safra;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.models.Solo;
import projeto.undercode.com.proyectobrapro.models.Solo2;
import projeto.undercode.com.proyectobrapro.models.Tarefa;
import projeto.undercode.com.proyectobrapro.models.Tax;
import projeto.undercode.com.proyectobrapro.models.TipoContrato;
import projeto.undercode.com.proyectobrapro.models.TipoDespesa;
import projeto.undercode.com.proyectobrapro.models.TipoDespesaTempo;
import projeto.undercode.com.proyectobrapro.models.TipoProducto;
import projeto.undercode.com.proyectobrapro.models.TipoTarefa;
import projeto.undercode.com.proyectobrapro.models.Usuario;
import projeto.undercode.com.proyectobrapro.models.VentaGado;
import projeto.undercode.com.proyectobrapro.models.Visita;

import static projeto.undercode.com.proyectobrapro.database.HelperDB.DATABASE_NAME;

/**
 * Created by Level on 07/03/2017.
 */

public class LocalDB extends controllerDB {

    private Context appContext;
    private HelperDB helperDB;

    public LocalDB(Context context) {
        super(context);

        appContext = context;
        helperDB = new HelperDB(appContext);
        //db = helperDB.getWritableDatabase();
        //openDatabase();
    }

    public HelperDB getHelperDB() {
        return this.helperDB;
    }

    public SQLiteDatabase openDatabase() {

        Log.d("openDatabase","openDatabase");
        File dbFile = appContext.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                Log.d("openDatabase1","openDatabase1");
                copyDatabase(dbFile);

            } catch (IOException e) {

                Log.d("openDatabase2","openDatabase2");
                throw new RuntimeException("Error creating source database", e);

            }

        }

        Log.d("openDatabase3","openDatabase3");
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);

    }


    private void copyDatabase(File dbFile) throws IOException {

        InputStream is = appContext.getAssets().open(DATABASE_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {

            os.write(buffer);

        }

        os.flush();
        os.close();
        is.close();

    }


    public ArrayList<AlertaPraga> ConsultaAlertaPlaga(int id_usuario, String estado) {

        db = helperDB.getWritableDatabase();

        ArrayList<AlertaPraga> alertaplaga = new ArrayList<AlertaPraga>();

        String selectQuery = SELECT + "t1.Id_alerta_plaga, t1.Id_sector, t3.Nombre as N_Sector, t1.Id_plaga, "
                + " t2.Nombre as N_Plaga, t1.Nombre as N_AlertaPlaga, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.Fecha_registro ,120),'') as Fecha_registro, "
                + " t1.Fecha_registro, "
                + " t1.Descripcion, t1.Status, t1.id_usuario "
                + FROM + ScriptDB.Alerta_plagaEntry.TABLE_NAME + " t1, "
                + ScriptDB.PlagaEntry.TABLE_NAME + " t2, " + ScriptDB.SectorEntry.TABLE_NAME + " t3 "
                + WHERE + " t1.id_usuario = " + id_usuario + " "
                + AND + " t1.Id_plaga = t2.Id_plaga" + AND + "t1.Id_sector = t3.Id_sector ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

                selectQuery += " order by t1.Fecha_registro desc";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("selectQuery",selectQuery);

        Log.d("cursor alertaplara",cursor.toString());
        Log.d("cursor size",String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                AlertaPraga conf = new AlertaPraga();
                conf.setId_alerta_plaga(Integer.parseInt(cursor.getString(0)));
                conf.setId_sector(Integer.parseInt(cursor.getString(1)));
                conf.setN_Sector(cursor.getString(2));
                conf.setId_plaga(Integer.parseInt(cursor.getString(3)));
                conf.setN_plaga(cursor.getString(4));
                conf.setN_AlertaPlaga(cursor.getString(5));
                conf.setFecha_registro(cursor.getString(6));
                conf.setDescripcion(cursor.getString(7));
                conf.setStatus(cursor.getString(8));
                conf.setId_usuario(Integer.parseInt(cursor.getString(9)));

                Log.d("setId_alerta_plaga",cursor.getString(0));
                Log.d("setId_sector",cursor.getString(1));
                Log.d("setN_Sector",cursor.getString(2));
                Log.d("setId_plaga",cursor.getString(3));
                Log.d("setN_plaga",cursor.getString(4));
                Log.d("setN_AlertaPlaga",cursor.getString(5));
                Log.d("setFecha_registro",cursor.getString(6));
                Log.d("setDescripcion",cursor.getString(7));
                Log.d("setStatus",cursor.getString(8));


            alertaplaga.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return alertaplaga;
    }




    public ArrayList<Gado> getAnimal(int id_usuario, int id_lote_gado) {

        db = helperDB.getWritableDatabase();

        ArrayList<Gado> confList = new ArrayList<Gado>();
        String selectQuery = SELECT + "id_animal, "
                + " id_lote_gado, "
                + " id_usuario, "
                + " nombre, "
                + " peso_inicial, "
                + " cod_adquisicao, "
                + " tipo_adquisicao " + FROM + ScriptDB.AnimalEntry.TABLE_NAME
                + WHERE + " id_usuario = " + id_usuario + "" + AND
                + " id_lote_gado = case when " + id_lote_gado + " is null then id_lote_gado else " + id_lote_gado + " end ";


        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

            Gado conf = new Gado();
            conf.setId_animal(Integer.parseInt(cursor.getString(0)));
            conf.setId_lote_gado(Integer.parseInt(cursor.getString(1)));
            conf.setId_usuario(Integer.parseInt(cursor.getString(2)));
            conf.setNombre(cursor.getString(3));
            conf.setPeso_inicial(Integer.parseInt(cursor.getString(4)));
            conf.setCod_adquisicao(Integer.parseInt(cursor.getString(5)));
            conf.setTipo_adquisicao(cursor.getString(6));
            confList.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return confList;
    }



    public ArrayList<Usuario> getLogin(String login, String senha) {

        db = helperDB.getWritableDatabase();

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String selectQuery = SELECT + "codigo, "
                + " nome, "
                + " login, "
                + " senha, "
                + " email, "
                + " status " + FROM + ScriptDB.UsuariosEntry.TABLE_NAME
                + WHERE + " login = '" + login + "'" + AND + " senha = '" + senha + "'";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

            Usuario conf = new Usuario();
            conf.setCodigo(cursor.getString(0));
            conf.setNome(cursor.getString(1));
            conf.setLogin(cursor.getString(2));
            conf.setLogin(cursor.getString(3));
            conf.setEmail(cursor.getString(4));
            conf.setStatus(cursor.getString(5));
            usuarios.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarios;
    }



    public ArrayList<Usuario> ConsultaUsers(String login) {

        db = helperDB.getWritableDatabase();

        ArrayList<Usuario> users = null;

        if (db != null){

            users = new ArrayList<Usuario>();

            String selectQuery = SELECT + " login, senha " + FROM + ScriptDB.UsuariosEntry.TABLE_NAME
                    + WHERE + " login = '" + encripta(login) + "'";

            SQLiteDatabase db = helperDB.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                Usuario conf = new Usuario();
                conf.setLogin(decripta(cursor.getString(0)));
                    conf.setSenha(decripta(cursor.getString(1)));
                users.add(conf);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return users;

        } else {
            Log.d("log","no creado");
            return users;
        }


    }


    public ArrayList<Cliente> ConsultaClientes(String id_usuario, String estado) {

        db = helperDB.getWritableDatabase();

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        String selectQuery = SELECT + "id_cliente, "
                + " id_usuario, "
                + " nombre, "
                + " organizacion, "
                + " numero, "
                + " direccion, "
                + " area, "
                + " cpf, "
                + " data_insercao "
                //+ " strftime('%d-%m-%Y', data_insercao) as data_insercao "
                + FROM + ScriptDB.ClienteEntry.TABLE_NAME
                + WHERE + " id_usuario = " + id_usuario + "";
                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

            Cliente conf = new Cliente();
            conf.setId_cliente(Integer.parseInt(cursor.getString(0)));
            conf.setId_usuario(Integer.parseInt(cursor.getString(1)));
            conf.setNombre(cursor.getString(2));
            conf.setOrganizacion(cursor.getString(3));
            conf.setNumero(cursor.getString(4));
            conf.setDireccion(cursor.getString(5));
            conf.setArea(cursor.getString(6));
            conf.setCpf(cursor.getString(7));
            conf.setData_insercao(cursor.getString(8));
            clientes.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return clientes;
    }


    public ArrayList<Colheita> ConsultaColheita(String id_usuario, String estado) {

        db = helperDB.getWritableDatabase();

        ArrayList<Colheita> colheitas = new ArrayList<Colheita>();
        String selectQuery = SELECT + "Id_cosecha, "
                + " Nombre "
                + FROM + ScriptDB.CosechaEntry.TABLE_NAME
                + WHERE + " id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }


        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


/*        if (cursor.moveToFirst()) {
            do {

                Colheita conf = new Colheita();
                conf.setId_cosecha(Integer.parseInt(cursor.getString(0)));
                conf.setNombre(cursor.getString(1));
                colheitas.add(conf);

            } while (cursor.moveToNext());
        }*/


        if(cursor.moveToNext()){
            do {

                Colheita conf = new Colheita();
                conf.setId_cosecha(Integer.parseInt(cursor.getString(0)));
                conf.setNombre(cursor.getString(1));
                colheitas.add(conf);

                Log.d("setId_cosecha",cursor.getString(0));
                Log.d("setNombre",cursor.getString(1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return colheitas;
    }

    public ArrayList<Safra> ConsultaCultivos(int id_usuario, String estado) {

        db = helperDB.getWritableDatabase();

        ArrayList<Safra> safras = new ArrayList<Safra>();
        String selectQuery = SELECT + "t1.Id_sector, "
                + " t1.Status as Status, "
                + " t1.Nombre as N_Sector, "
                + " t1.Hectareas as Hectareas, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t2.Inicio ,120),'') as Inicio, "
                + " t2.Inicio as Inicio, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t2.Fim ,120),'') as Fim, "
                + " t2.Fim as Fim, "
                //+ " ISNULL(t3.Nombre, '') as N_Cosecha "
                + " t3.Nombre as N_Cosecha, "
                + " t3.Id_cosecha "
                + FROM + ScriptDB.SectorEntry.TABLE_NAME + " t1 LEFT JOIN "
                + ScriptDB.CultivoEntry.TABLE_NAME + " t2 ON t1.Id_cultivo = t2.Id_cultivo LEFT JOIN "
                + ScriptDB.CosechaEntry.TABLE_NAME + " t3 ON t2.Id_cosecha = t3.Id_cosecha "
                + WHERE + " t1.Id_usuario = " + id_usuario + "";
        if(estado.equals("Criado")){
            selectQuery += " and t2.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t2.estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("selectQuery", selectQuery);
        Log.d("cursor size", String.valueOf(cursor.getCount()));
        Log.d("cursor",cursor.toString());

        if(cursor.moveToFirst()) {
            do {

            Safra conf = new Safra();
                conf.setId_sector(cursor.getString(0));
                conf.setStatus(cursor.getString(1));
                conf.setN_Sector(cursor.getString(2));
                conf.setHectareas(cursor.getString(3));
                conf.setInicio(cursor.getString(4));
                conf.setFim(cursor.getString(5));
                conf.setN_Cosecha(cursor.getString(6));
                conf.setId_cosecha(cursor.getString(7));

                Log.d("setId_sector",String.valueOf(cursor.getString(0)));
                Log.d("setStatus",String.valueOf(cursor.getString(1)));
                Log.d("setN_Sector",String.valueOf(cursor.getString(2)));
                Log.d("setHectareas",String.valueOf(cursor.getString(3)));
                Log.d("setInicio",String.valueOf(cursor.getString(4)));
                Log.d("setFim",String.valueOf(cursor.getString(5)));
                Log.d("setN_Cosecha",String.valueOf(cursor.getString(6)));


            safras.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return safras;
    }



    public ArrayList<Despesa> ConsultaDespesas(String id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Despesa> despesas = new ArrayList<Despesa>();
        String selectQuery = SELECT + "t1.id_despesa, "
                + " t1.usuario, "
                + " t1.pim, "
                + " t1.imei, "
                + " t1.latitude, "
                + " t1.longitude, "
                + " t1.versao, "
                + " t1.valor, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_despesa ,120),'') as data_despesa, "
                + " t1.data_despesa as data_despesa, "
                + " t1.tipo_despesa_tempo, "
                + " t2.Nombre as N_tipo_despesa_tempo, "
                + " t1.tipo_despesa, "
                + " t3.Nombre as N_tipo_despesa "
                + FROM + ScriptDB.DespesasEntry.TABLE_NAME + " t1, "
                + ScriptDB.TipoDespesaTempoEntry.TABLE_NAME + " t2, "
                + ScriptDB.TipoDespesaEntry.TABLE_NAME + " t3 "
                + WHERE + " t1.tipo_despesa_tempo = t2.Id_tipo_despesa_tempo and "
                + " t1.tipo_despesa = t3.Id_tipo_despesa and "
                + " t1.usuario = " + id_usuario + " ";

                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

            Despesa conf = new Despesa();
            conf.setId_despesa(Integer.parseInt(cursor.getString(0)));
            conf.setUsuario(Integer.parseInt(cursor.getString(1)));
            conf.setPim(cursor.getString(2));
            conf.setImei(cursor.getString(3));
            conf.setLatitude(cursor.getString(4));
            conf.setLongitude(cursor.getString(5));
            conf.setVersao(cursor.getString(6));
            conf.setValor(cursor.getString(7));
            conf.setData_despesa(cursor.getString(8));
            conf.setTipo_despesa_tempo(Integer.parseInt(cursor.getString(9)));
            conf.setN_tipo_despesa_tempo(cursor.getString(10));
            conf.setTipo_despesa(Integer.parseInt(cursor.getString(11)));
            conf.setN_tipo_despesa(cursor.getString(12));
            despesas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return despesas;
    }



    public ArrayList<Empregado> ConsultaEmpleados(String id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Empregado> empregados = new ArrayList<Empregado>();
        String selectQuery = SELECT + " t1.Id_empleado, "
                + " t1.Id_usuario, "
                + " t1.Nombre, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.Fecha_contratacion ,120),'') as Fecha_contratacion, "
                + " t1.Fecha_contratacion as Fecha_contratacion, "
                + " t1.Edad, "
                + " t1.Rol, "
                + " t1.contacto, "
                + " t1.salario, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.fin_de_contrato ,120),'') as fin_de_contrato, "
                + " t1.fin_de_contrato as fin_de_contrato, "
                + " t1.tipo_contrato, "
                + " t2.Nombre as N_tipo_contrato "
                + FROM + ScriptDB.EmpleadoEntry.TABLE_NAME + " t1, "
                + ScriptDB.TipoContratoEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.tipo_contrato = t2.id_tipo_contrato and "
                + " t1.Id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }

                selectQuery += " ORDER BY t1.Fecha_contratacion desc";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

            Empregado conf = new Empregado();
            conf.setId_empleado(Integer.parseInt(cursor.getString(0)));
            conf.setId_usuario(Integer.parseInt(cursor.getString(1)));
            conf.setNombre(cursor.getString(2));
            conf.setFecha_contratacion(cursor.getString(3));
            conf.setEdad(Integer.parseInt(cursor.getString(4)));
            conf.setRol(cursor.getString(5));
            conf.setContacto(cursor.getString(6));
            conf.setSalario(Integer.parseInt(cursor.getString(7)));
            conf.setFin_de_contrato(cursor.getString(8));
            conf.setTipo_contrato(Integer.parseInt(cursor.getString(9)));
            conf.setN_tipo_contrato(cursor.getString(10));
            empregados.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return empregados;
    }


    public ArrayList<Gado> ConsultaGado(String id_usuario, String id_lote_gado, String estado) {

        Log.d("id_usuario",id_usuario);
        Log.d("id_lote_gado",id_lote_gado);

        ArrayList<Gado> gados = new ArrayList<Gado>();

/*        String selectQuery2 = SELECT + " id_animal, "
                + " id_lote_gado, "
                + " id_usuario, "
                + " nombre, "
                + " peso_inicial, "
                + " cod_adquisicao, "
                + " tipo_adquisicao "
                + FROM + ScriptDB.AnimalEntry.TABLE_NAME + " "
                + WHERE + " id_usuario = " + id_usuario + "";
        if (!id_lote_gado.equals("")){
            selectQuery2 += " and id_lote_gado = " + id_lote_gado + "";
        }*/

        String selectQuery2 = SELECT + " t1.id_animal, "
                + " t1.id_lote_gado, "
                + " t1.id_usuario, "
                + " t1.nombre, "
                + " t1.peso_inicial, "
                + " t1.cod_adquisicao, "
                + " t1.tipo_adquisicao, "
                + " t2.fecha, t2.precio, 0 as id_parto"
                + FROM + ScriptDB.AnimalEntry.TABLE_NAME + " t1, "
                + ScriptDB.CompraEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.tipo_adquisicao = 'Compra' and t1.cod_adquisicao = t2.id_compra "
                //+ WHERE + " t1.cod_adquisicao = t2.id_compra and t1.id_lote_gado = " + id_lote_gado + " "
                + " and t1.id_usuario = " + id_usuario + " ";
                //+ WHERE + " t1.cod_adquisicao = t2.id_compra ";

                if (!id_lote_gado.equals("")){
                    selectQuery2 += " and t1.id_lote_gado = " + id_lote_gado + " ";
                }

                if(estado.equals("Criado")){
                    selectQuery2 += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery2 += " and t1.estado = 'Cargado' ";
                }



        selectQuery2 += " UNION ALL "
                //+ " UNION ALL "
                + SELECT + " t1.id_animal, "
                + " t1.id_lote_gado, "
                + " t1.id_usuario, "
                + " t1.nombre, "
                + " t1.peso_inicial, "
                + " t1.cod_adquisicao, "
                + " t1.tipo_adquisicao, "
                + " t2.fecha, 0 as precio, t2.id_animal as id_parto"
                + FROM + ScriptDB.AnimalEntry.TABLE_NAME + " t1, "
                + ScriptDB.PartoEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.tipo_adquisicao = 'Parto' and t1.cod_adquisicao = t2.id_parto and "
                //+ WHERE + " t1.cod_adquisicao = t2.id_parto and "
                + " t1.id_usuario = " + id_usuario + " ";
        if (!id_lote_gado.equals("")){
            selectQuery2 += " and t1.id_lote_gado = " + id_lote_gado + " ";
        }

        if(estado.equals("Criado")){
            selectQuery2 += " and t1.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery2 += " and t1.estado = 'Cargado' ";
        }


        Log.d("selectQuery gado", selectQuery2);


        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery2, null);

        Log.d("cursor gado", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                Gado conf = new Gado();
                    conf.setId_animal(Integer.parseInt(cursor.getString(0)));
                    conf.setId_lote_gado(Integer.parseInt(cursor.getString(1)));
                    conf.setId_usuario(Integer.parseInt(cursor.getString(2)));
                    conf.setNombre(cursor.getString(3));
                    conf.setPeso_inicial(Integer.parseInt(cursor.getString(4)));
                    conf.setCod_adquisicao(Integer.parseInt(cursor.getString(5)));
                    conf.setTipo_adquisicao(cursor.getString(6));
                    conf.setFecha(cursor.getString(7));
                    conf.setPrecio(Integer.parseInt(cursor.getString(8)));
                    conf.setId_parto(cursor.getString(9));

                    Log.d("setId_animal",cursor.getString(0));
                Log.d("setId_lote_gado",cursor.getString(1));
                Log.d("setId_usuario",cursor.getString(2));
                Log.d("setNombre",cursor.getString(3));
                Log.d("setPeso_inicial",cursor.getString(4));
                Log.d("setCod_adquisicao",cursor.getString(5));
                Log.d("setTipo_adquisicao",cursor.getString(6));


                gados.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gados;
    }



    public ArrayList<HistorialConsumo> ConsultaHistorialConsumo(String id_animal,String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<HistorialConsumo> historialConsumos = new ArrayList<HistorialConsumo>();
        String selectQuery = SELECT + " t1.id_historial_consumo, "
                + " t1.id_animal, "
                + " t1.id_producto, "
                + " t1.cantidad_consumida, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.fecha_consumo ,120),'') as fecha_consumo, "
                + " t1.fecha_consumo as fecha_consumo, "
                + " t2.nombre, "
                + " t3.Nombre as N_producto "
                + FROM + ScriptDB.Historial_ConsumoEntry.TABLE_NAME + " t1, "
                + ScriptDB.AnimalEntry.TABLE_NAME + " t2, "
                + ScriptDB.ProductoEntry.TABLE_NAME + " t3 "
                //+ WHERE + " t1.id_animal = " + id_animal + " and "
                + WHERE
                + " t1.id_animal = t2.id_animal and t3.Id_producto = t1.id_producto ";

        if(!id_animal.equals("")){
            selectQuery += " and t1.id_animal = " + id_animal + "  ";
        }


        if(estado.equals("Criado")){
            selectQuery += " and t1.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t1.estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
            HistorialConsumo conf = new HistorialConsumo();
            conf.setId_historial_consumo(Integer.parseInt(cursor.getString(0)));
            conf.setId_animal(Integer.parseInt(cursor.getString(1)));
            conf.setId_producto(Integer.parseInt(cursor.getString(2)));
            conf.setCantidad_consumida(Integer.parseInt(cursor.getString(3)));
            conf.setFecha_consumo(cursor.getString(4));
            conf.setNombre(cursor.getString(5));
            conf.setN_producto(cursor.getString(6));
            historialConsumos.add(conf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return historialConsumos;
    }




    public ArrayList<HistorialEngorde> ConsultaHistorialEngorde(String id_animal, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<HistorialEngorde> historialEngordes = new ArrayList<HistorialEngorde>();
        String selectQuery = SELECT + " t1.id_historial_engorde, "
                + " t1.id_animal, "
                + " t1.peso, " //peso
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.fecha_medicion ,120),'') as fecha_medicion, "
                + " t1.fecha_medicion, "
                + " t2.nombre "
                + FROM + ScriptDB.Historial_EngordeEntry.TABLE_NAME + " t1, "
                + ScriptDB.AnimalEntry.TABLE_NAME + " t2 "
                //+ WHERE + " t1.id_animal = " + id_animal + " and "
                + WHERE
                + " t1.id_animal = t2.id_animal ";

        if(!id_animal.equals("")){
            selectQuery += " and t1.id_animal = " + id_animal + "  ";
        }

        if(estado.equals("Criado")){
            selectQuery += " and t1.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t1.estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

            HistorialEngorde conf = new HistorialEngorde();
            conf.setId_historial_engorde(Integer.parseInt(cursor.getString(0)));
            conf.setId_animal(Integer.parseInt(cursor.getString(1)));
            conf.setPeso(Integer.parseInt(cursor.getString(2)));
            conf.setFecha_medicion(cursor.getString(3));
            conf.setNombre(cursor.getString(4));
            historialEngordes.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return historialEngordes;
    }



    public ArrayList<HistorialLeche> ConsultaHistorialLeche(String id_animal, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<HistorialLeche> historialLeites = new ArrayList<HistorialLeche>();
        String selectQuery = SELECT + " t1.id_historial_leche, "
                + " t1.id_animal, "
                + " t1.cantidad, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.fecha_obtencao ,120),'') as fecha_obtencao, "
                + " t1.fecha_obtencao as fecha_obtencao, "
                + " t2.nombre "
                + FROM + ScriptDB.Historial_LecheEntry.TABLE_NAME + " t1, "
                + ScriptDB.AnimalEntry.TABLE_NAME + " t2 "
                //+ WHERE + " t1.id_animal = " + id_animal + " and "
                + WHERE
                + " t1.id_animal = t2.id_animal ";

        if(!id_animal.equals("")){
            selectQuery += " and t1.id_animal = " + id_animal + "  ";
        }

        if(estado.equals("Criado")){
            selectQuery += " and t1.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t1.estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("ConsultaHistorialLeche",String.valueOf(cursor.getCount()));


        if (cursor.moveToFirst()) {

            do{
            HistorialLeche conf = new HistorialLeche();
            conf.setId_historial_leche(Integer.parseInt(cursor.getString(0)));
            conf.setId_animal(Integer.parseInt(cursor.getString(1)));
            conf.setCantidad(Integer.parseInt(cursor.getString(2)));
            conf.setFecha_obtencao(cursor.getString(3));
            conf.setNombre(cursor.getString(4));
            historialLeites.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return historialLeites;
    }




    public ArrayList<LoteGado> ConsultaLotegado(String id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<LoteGado> lotegados = new ArrayList<LoteGado>();
        String selectQuery = SELECT + " t1.id_lote_gado, "
                + " t1.id_usuario, "
                + " t1.nombre, "
                + " t1.descripcao, "
                + " ( SELECT count(*) as cantidad FROM animal where id_lote_gado = t1.id_lote_gado) as cantidad "
                + FROM + ScriptDB.Lote_gadoEntry.TABLE_NAME + " t1 "
                + WHERE + " t1.id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            LoteGado conf = new LoteGado();
            conf.setId_lote_gado(Integer.parseInt(cursor.getString(0)));
            conf.setId_usuario(Integer.parseInt(cursor.getString(1)));
            conf.setNombre(cursor.getString(2));
            conf.setDescripcao(cursor.getString(3));
            conf.setCantidad(cursor.getString(4));
            lotegados.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return lotegados;
    }


    public ArrayList<Maquinaria> ConsultaMaquinarias(String id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Maquinaria> maquinarias = new ArrayList<Maquinaria>();
        String selectQuery = SELECT + " Id_maquinaria, "
                + " Id_usuario, "
                + " Nombre, "
                + " Registro, "
                //+ " ISNULL(CONVERT( VARCHAR(10), Fecha_Adquisicion ,120),'') as Fecha_Adquisicion, "
                 //+ "strftime('%d-%m-%Y', Fecha_Adquisicion) as Fecha_Adquisicion, "
                + " Fecha_Adquisicion, "
                + " Precio, "
                + " Tipo, "
                + " Descripcion, "
                + " Modelo, "
                + " costo_mantenimiento, "
                + " vida_util_horas, "
                + " vida_util_ano, "
                + " potencia_maquinaria, "
                + " tipo_adquisicion "
                + FROM + ScriptDB.MaquinariaEntry.TABLE_NAME
                + WHERE + " Id_usuario = " + id_usuario + " ";

                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }

                selectQuery += " ORDER BY Fecha_Adquisicion desc";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
            Maquinaria conf = new Maquinaria();
            conf.setId_maquinaria(Integer.parseInt(cursor.getString(0)));
            conf.setId_usuario(Integer.parseInt(cursor.getString(1)));
            conf.setNombre(cursor.getString(2));
            conf.setRegistro(cursor.getString(3));
            conf.setFecha_Adquisicion(cursor.getString(4));
            conf.setPrecio(Integer.parseInt(cursor.getString(5)));
            conf.setTipo(cursor.getString(6));
            conf.setDescripcion(cursor.getString(7));
            conf.setModelo(cursor.getString(8));
            conf.setCosto_mantenimiento(Integer.parseInt(cursor.getString(9)));
            conf.setVida_util_horas(Integer.parseInt(cursor.getString(10)));
            conf.setVida_util_ano(Integer.parseInt(cursor.getString(11)));
            conf.setPotencia_maquinaria(Integer.parseInt(cursor.getString(12)));
            conf.setTipo_adquisicion(cursor.getString(13));
            maquinarias.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return maquinarias;
    }


    public ArrayList<MenuModel> ConsultaMenu(int id_usuario) {
        db = helperDB.getWritableDatabase();
        ArrayList<MenuModel> menumodel = new ArrayList<MenuModel>();
        String perfil = "";
        String selectQuery1 = SELECT + " perfil "
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME
                + WHERE + " codigo = " + encripta(String.valueOf(id_usuario)) + "";
        String selectQuery2 = "";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        if (cursor.moveToFirst()) {
            perfil = decripta(cursor.getString(0));
        }
        cursor.close();

        Log.d("perfil", perfil);

        if (perfil.equals("1")){
            selectQuery2 = SELECT + " id_menu, "
                    + " nombre, "
                    + " imagen "
                    + FROM + ScriptDB.MenuEntry.TABLE_NAME
                    + WHERE + " id_menu NOT IN (17,4,5,12,13)";
        }

        if (perfil.equals("2")){
            selectQuery2 = SELECT + " id_menu, "
                    + " nombre, "
                    + " imagen "
                    + FROM + ScriptDB.MenuEntry.TABLE_NAME
                    + WHERE + " id_menu NOT IN (17,4,5,12,13,26)";
            //+ WHERE + " id_menu = 1";
        }

        Log.d("selectQuery2", selectQuery2);

        SQLiteDatabase db1 = helperDB.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery(selectQuery2, null);

/*            if (cursor1.moveToFirst()) {
                try {
                    do {
                        MenuModel conf = new MenuModel();
                        conf.setId_menu(Integer.parseInt(cursor1.getString(0)));
                        conf.setNombre(cursor1.getString(1));
                        conf.setImage(cursor1.getString(2));
                        menumodel.add(conf);
                        Log.d("menumodelsize:  ",String.valueOf(menumodel.size()));
                    } while (cursor.moveToNext());
                } catch (Exception ex) {
                }
            }*/

        try {
            while (cursor1.moveToNext()) {
                MenuModel conf = new MenuModel();
                conf.setId_menu(Integer.parseInt(cursor1.getString(0)));
                conf.setNombre(cursor1.getString(1));
                conf.setImage(cursor1.getString(2));
                menumodel.add(conf);
                Log.d("menumodelsize:  ",String.valueOf(menumodel.size()));
                Log.d("setId_menu:  ",String.valueOf(cursor1.getString(0)));
                Log.d("setNombre:  ",String.valueOf(cursor1.getString(1)));
            }
        } finally {
            cursor1.close();
            db.close();
        }


        Log.d("menumodel",menumodel.toArray().toString());
        Log.d("size",String.valueOf(menumodel.size()));

        return menumodel;
    }


    public ArrayList<Negociacoe> ConsultaNegociacoes(String id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Negociacoe> negociacoes = new ArrayList<Negociacoe>();
        String selectQuery = SELECT + " t1.id_negociacoes, "
                + " t1.usuario, "
                + " t1.pim, "
                + " t1.imei, "
                + " t1.latitude, "
                + " t1.longitude, "
                + " t1.versao, "
                + " t1.cpf, "
                + " t1.nome, "
                + " t1.tipo_local, "
                + " t1.local, "
                + " t1.produto, "
                + " t1.taxa, "
                + " t1.valor_negociado, "
                //+ " t1.ISNULL(CONVERT( VARCHAR(10), t1.data_pagamento ,120),'') as data_pagamento, "
                + " t1.data_pagamento as data_pagamento, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_cadastro ,120),'') as data_cadastro, "
                + " t1.data_cadastro as data_cadastro, "
                + " t2.Nombre as N_producto "
                + FROM + ScriptDB.NegociacoesEntry.TABLE_NAME + " t1, "
                + ScriptDB.ProductoEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.produto = t2.Id_producto and "
                + " t1.usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

                selectQuery += " ORDER BY t1.data_pagamento desc ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Negociacoe conf = new Negociacoe();
            conf.setId_negociacoes(Integer.parseInt(cursor.getString(0)));
            conf.setUsuario(Integer.parseInt(cursor.getString(1)));
            conf.setPim(cursor.getString(2));
            conf.setImei(cursor.getString(3));
            conf.setLatitude(cursor.getString(4));
            conf.setLongitude(cursor.getString(5));
            conf.setVersao(cursor.getString(6));
            conf.setCpf(cursor.getString(7));
            conf.setNome(cursor.getString(8));
            conf.setTipo_local(cursor.getString(9));
            conf.setLocal(cursor.getString(10));
            conf.setProduto(Integer.parseInt(cursor.getString(11)));
            conf.setTaxa(Integer.parseInt(cursor.getString(12)));
            conf.setValor_negociado(Integer.parseInt(cursor.getString(13)));
            conf.setData_pagamento(cursor.getString(14));
            conf.setData_cadastro(cursor.getString(15));
            conf.setN_produto(cursor.getString(16));
            negociacoes.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return negociacoes;
    }




    public ArrayList<Praga> ConsultaPlagas(String plaga) {
        db = helperDB.getWritableDatabase();
        ArrayList<Praga> pragas = new ArrayList<Praga>();
        String selectQuery = SELECT + " Id_plaga, "
                + " Nombre, "
                + " Caracteristicas, "
                + " Sintomas, "
                + " Tratamiento, "
                + " Clase, "
                + " Descripcion, "
                + " Prevencion "
                + FROM + ScriptDB.PlagaEntry.TABLE_NAME ;
                //+ WHERE + " Id_plaga = (CASE WHEN " + plaga + " IS NULL then Id_plaga else " + plaga + " end) ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("pragas count: ", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {

            do{
            Praga conf = new Praga();
            conf.setId_plaga(Integer.parseInt(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));
            conf.setCaracteristicas(cursor.getString(2));
            conf.setSintomas(cursor.getString(3));
            conf.setTratamiento(cursor.getString(4));
            conf.setClase(cursor.getString(5));
            conf.setDescripcion(cursor.getString(6));
            conf.setPrevencion(cursor.getString(7));
            pragas.add(conf);

                Log.d("setId_plaga",cursor.getString(0));
                Log.d("setNombre",cursor.getString(1));
                Log.d("setCaracteristicas",cursor.getString(2));
                Log.d("setSintomas",cursor.getString(3));
                Log.d("setTratamiento",cursor.getString(4));
                Log.d("setClase",cursor.getString(5));
                Log.d("setDescripcion",cursor.getString(6));
                Log.d("setPrevencion",cursor.getString(7));


            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return pragas;
    }


    public ArrayList<PontoInteresse> ConsultaPontosInteresse(int usuario, String estado) {

        db = helperDB.getWritableDatabase();
        ArrayList<PontoInteresse> pontointeressess = new ArrayList<PontoInteresse>();
        String selectQuery = SELECT + " id_mapear, "
                + " usuario, "
                + " pim, "
                + " imei, "
                + " latitude, "
                + " longitude, "
                + " versao, "
                + " endereco, "
                + " tipo, "
                + " obs, "
                //+ " ISNULL(CONVERT( VARCHAR(10), data_cadastro ,120),'') as data_cadastro "
                + " data_cadastro as data_cadastro "
                + FROM + ScriptDB.MapearEntry.TABLE_NAME + " "
                + WHERE + " usuario = " + usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }

                selectQuery += " ORDER BY data_cadastro desc ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
            PontoInteresse conf = new PontoInteresse();
            conf.setId_mapear(Integer.parseInt(cursor.getString(0)));
            conf.setUsuario(Integer.parseInt(cursor.getString(1)));
            conf.setPim(cursor.getString(2));
            conf.setImei(cursor.getString(3));
            conf.setLatitude(cursor.getString(4));
            conf.setLongitude(cursor.getString(5));
            conf.setVersao(cursor.getString(6));
            conf.setEndereco(cursor.getString(7));
            conf.setTipo(cursor.getString(8));
            conf.setObs(cursor.getString(9));
            conf.setData_cadastro(cursor.getString(10));
            pontointeressess.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return pontointeressess;
    }



    public ArrayList<Preco> ConsultaPrecos() {
        db = helperDB.getWritableDatabase();
        ArrayList<Preco> precos = new ArrayList<Preco>();
        String selectQuery = SELECT + " t1.id_precos, "
                + " t1.produto, "
                + " t1.ano, "
                + " t1.mes, "
                + " t1.dia, "
                + " t1.estado, "
                + " t1.regiao, "
                + " t1.preco_dolar, "
                + " t1.preco_real, "
                + " t1.taxa, "
                + " t1.mes_descricao, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_atualizacao ,120),'') as data_atualizacao "
                + " t1.data_atualizacao as data_atualizacao "
                + FROM + ScriptDB.PrecosEntry.TABLE_NAME + " t1 ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Preco conf = new Preco();
            conf.setId_precos(Integer.parseInt(cursor.getString(0)));
            conf.setProduto(cursor.getString(1));
            conf.setAno(cursor.getString(2));
            conf.setMes(cursor.getString(3));
            conf.setDia(cursor.getString(4));
            conf.setEstado(cursor.getString(5));
            conf.setRegiao(Integer.parseInt(cursor.getString(6)));
            conf.setPreco_dolar(Integer.parseInt(cursor.getString(7)));
            conf.setPreco_real(Integer.parseInt(cursor.getString(8)));
            conf.setTaxa(Integer.parseInt(cursor.getString(9)));
            conf.setMes_descricao(cursor.getString(10));
            conf.setData_atualizacao(cursor.getString(11));
            precos.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return precos;
    }


    public ArrayList<Premio> ConsultaPremios(int id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Premio> premios = new ArrayList<Premio>();
        String selectQuery = SELECT + " t1.id_premio, "
                + " t1.empleado, "
                + " t2.Nombre as N_Empleado, "
                + " t1.premio, "
                + " t1.ano, "
                + " t1.mes, "
                + " t1.mes_descricao, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_atualizacao  ,120),'') as data_atualizacao, "
                + " t1.data_atualizacao as data_atualizacao, "
                + " t1.id_usuario "
                + FROM + ScriptDB.PremiosEntry.TABLE_NAME + " t1, "
                + ScriptDB.EmpleadoEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.empleado = t2.Id_empleado and t1.id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{
            Premio conf = new Premio();
            conf.setId_premio(Integer.parseInt(cursor.getString(0)));
            conf.setEmpleado(Integer.parseInt(cursor.getString(1)));
            conf.setN_Empleado(cursor.getString(2));
            conf.setPremio(Integer.parseInt(cursor.getString(3)));
            conf.setAno(Integer.parseInt(cursor.getString(4)));
            conf.setMes(Integer.parseInt(cursor.getString(5)));
            conf.setMes_descricao(cursor.getString(6));
            conf.setData_atualizacao(cursor.getString(7));
            conf.setId_usuario(Integer.parseInt(cursor.getString(8)));
            premios.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return premios;
    }



    public ArrayList<Produto> ConsultaProducto(int id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        String selectQuery = SELECT + " t1.Id_producto, "
                + " t1.Id_tipo_producto, "
                + " t1.Nombre, "
                + " t2.Nombre as N_TipoProducto, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.Fecha_registro ,120),'') as Fecha_registro, "
                + " t1.Fecha_registro as Fecha_registro, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.Fecha_expiracion ,120),'') as Fecha_expiracion, "
                + " t1.Fecha_expiracion as Fecha_expiracion, "
                + " t1.Funcion, "
                + " t1.Descripcion, "
                + " t1.Composicion, "
                + " t1.Objeto, "
                + " t1.Imagen, "
                + " t1.lote, "
                + " t1.custo, "
                + " t1.kilos "
                + FROM + ScriptDB.ProductoEntry.TABLE_NAME + " t1, "
                + ScriptDB.TipoProductoEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.Id_tipo_producto = t2.Id_tipo_producto and t1.id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and estado = 'Cargado' ";
                }
                selectQuery += " ORDER BY t1.Fecha_registro desc ";


        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Produto conf = new Produto();
            conf.setId_producto(Integer.parseInt(cursor.getString(0)));
            conf.setId_tipo_producto(Integer.parseInt(cursor.getString(1)));
            conf.setNombre(cursor.getString(2));
            conf.setN_TipoProducto(cursor.getString(3));
            conf.setFecha_registro(cursor.getString(4));
            conf.setFecha_expiracion(cursor.getString(5));
            conf.setFuncion(cursor.getString(6));
            conf.setDescipcion(cursor.getString(7));
            conf.setComposicion(cursor.getString(8));
            conf.setObjeto(cursor.getString(9));
            conf.setImagen(cursor.getString(10));
            conf.setLote(cursor.getString(11));
            conf.setCusto(cursor.getString(12));
            conf.setKilos(cursor.getString(13));
            produtos.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return produtos;
    }



    public ArrayList<Setor> ConsultaSectores(int id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Setor> setores = new ArrayList<Setor>();
        String selectQuery = SELECT + " id_sector, "
                + " id_usuario, "
                + " Id_cultivo, "
                + " status, "
                + " Nombre, "
                + " Hectareas "
                + FROM + ScriptDB.SectorEntry.TABLE_NAME + " "
                + WHERE + " id_usuario = " + id_usuario + " ";
        if(estado.equals("Criado")){
            selectQuery += " and estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("cursor", cursor.toString());

        if (cursor.moveToFirst()) {

            do{
            Setor conf = new Setor();
            conf.setId_sector(cursor.getString(0));
            conf.setId_usuario(cursor.getString(1));
            conf.setId_cultivo(cursor.getString(2));
            conf.setStatus(cursor.getString(3));
            conf.setNombre(cursor.getString(4));
            conf.setHectares(cursor.getString(5));

                Log.d("setId_sector",cursor.getString(0));
                Log.d("setId_usuario",cursor.getString(1));
                Log.d("setId_cultivo",cursor.getString(2));
                Log.d("setStatus",cursor.getString(3));
                Log.d("setNombre",cursor.getString(4));
                Log.d("setHectares",cursor.getString(5));

            setores.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return setores;
    }




    public ArrayList<Coordenadas> ConsultaSectores_Coordenadas(int id_usuario, String estado) {

        db = helperDB.getWritableDatabase();
        ArrayList<Setor> setores = new ArrayList<Setor>();
        ArrayList<Coordenadas> coordenadas = new ArrayList<Coordenadas>();

        String selectQuery = SELECT + " t1.Id_sector, "
                + " t1.Id_usuario, "
                + " t1.Id_cultivo, "
                + " t1.Status, "
                + " t1.Nombre, "
                + " t1.Hectareas, "
                + " t2.id_coordenada, "
                + " t2.latitude, "
                + " t2.longitude "
                + FROM + ScriptDB.SectorEntry.TABLE_NAME + " t1, "
                + ScriptDB.CoordenadasEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.Id_sector = t2.id_sector and t1.Id_usuario = " + id_usuario + " ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Setor conf = new Setor();
            conf.setId_sector(cursor.getString(0));
            conf.setId_usuario(cursor.getString(1));
            conf.setId_cultivo(cursor.getString(2));
            conf.setStatus(cursor.getString(3));
            conf.setNombre(cursor.getString(4));
            conf.setHectares(cursor.getString(5));

            Coordenadas conf2 = new Coordenadas();
            conf2.setId_coordenada(Integer.valueOf(cursor.getString(6)));
            conf2.setLatitude(cursor.getString(7));
            conf2.setLongitude(cursor.getString(8));
            conf2.setSetor(conf);

            coordenadas.add(conf2);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return coordenadas;
    }




    public ArrayList<Usuario> ConsultaSenha(String login) {

        db = helperDB.getWritableDatabase();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String selectQuery = SELECT + " senha "
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME + " "
                + WHERE + " login = '" + login + "'";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Usuario conf = new Usuario();
            conf.setLogin(cursor.getString(0));
            usuarios.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return usuarios;
    }

    public ArrayList<Solo2> ConsultaSolo(int id_usuario,String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Solo2> solos = new ArrayList<Solo2>();
        String selectQuery = SELECT + " t1.id_consulta_solo, "
                + " t2.id_sector, "
                + " t2.Nombre as N_Sector, "
                + " t1.fosforo_status, "
                + " t1.fosforo_value, "
                + " t1.potasio_status, "
                + " t1.potasio_value, "
                + " t1.calcio_status, "
                + " t1.calcio_value, "
                + " t1.magnesio_status, "
                + " t1.magnesio_value, "
                + " t1.aluminio_status, "
                + " t1.aluminio_value, "
                + " t1.material_organico_status, "
                + " t1.material_organico_value, "
                + " t1.hidrogeno_status, "
                + " t1.hidrogeno_value, "
                + " t1.potencial_hidrogenionico_status, "
                + " t1.potencial_hidrogenionico_value, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_consulta  ,120),'') as data_consulta "
                + " t1.data_consulta as data_consulta, "
                + " t1.id_usuario "
                + FROM + ScriptDB.Consulta_SoloEntry.TABLE_NAME + " t1, "
                + ScriptDB.SectorEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.id_usuario = " + id_usuario + " and t1.id_sector = t2.Id_sector ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }
        selectQuery += " ORDER BY data_consulta desc ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
                Solo2 conf = new Solo2();
                conf.setId_consulta_solo(Integer.valueOf(cursor.getString(0)));
                conf.setId_sector(Integer.valueOf(cursor.getString(1)));
                conf.setN_Sector(cursor.getString(2));
                conf.setFosforo_status(cursor.getString(3));
                conf.setFosforo_value(Double.valueOf(cursor.getString(4)));
                conf.setPotasio_status(cursor.getString(5));
                conf.setPotasio_value(Double.valueOf(cursor.getString(6)));
                conf.setCalcio_status(cursor.getString(7));
                conf.setCalcio_value(Double.valueOf(cursor.getString(8)));
                conf.setMagnesio_status(cursor.getString(9));
                conf.setMagnesio_value(Double.valueOf(cursor.getString(10)));
                conf.setAlumninio_status(cursor.getString(11));
                conf.setAlumninio_value(Double.valueOf(cursor.getString(12)));
                conf.setMaterial_organico_status(cursor.getString(13));
                conf.setMaterial_organico_value(Double.valueOf(cursor.getString(14)));
                conf.setHidrogeno_status(cursor.getString(15));
                conf.setHidrogeno_value(Double.valueOf(cursor.getString(16)));
                conf.setPotencial_hidrogenionico_status(cursor.getString(17));
                conf.setPotencial_hidrogenionico_value(Double.valueOf(cursor.getString(18)));
                conf.setData_consulta(cursor.getString(19));
                conf.setId_usuario(Integer.valueOf(cursor.getString(20)));

            solos.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return solos;
    }





    public ArrayList<Tarefa> ConsultaTarefas(int id_usuario,String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
        String selectQuery = SELECT + " t1.Id_tarea, "
                + " t1.Id_usuario, t1.Id_producto, t2.Nombre as Nombre_Producto, t1.Id_empleado, "
                + " t3.Nombre as Nombre_Empleado, t3.contacto as Contacto_Empleado, "
                + " t1.Id_tipo_producto, t4.Nombre as Nombre_Tipo_Producto, "
                + " t1.Id_maquinaria, t5.Nombre as Nombre_Maquinaria, "
                + " t1.Id_tipo_tarea, t6.Nombre as Nombre_Tipo_Tarea,"
                + " t1.Id_sector, t7.Nombre as Nombre_Sector,"
                + " t1.Nombre as Nombre_Tarea, t1.Descripcion,"
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.Fecha_trabajo ,120),'') as Fecha_trabajo, "
                + " t1.Fecha_trabajo as Fecha_trabajo, "
                + " t1.horas_trabajadas, t1.hectareas_trabajadas, t1.cantidad_producto"
                + FROM + ScriptDB.TareaEntry.TABLE_NAME + " t1, "
                + ScriptDB.ProductoEntry.TABLE_NAME + " t2, "
                + ScriptDB.EmpleadoEntry.TABLE_NAME + " t3, "
                + ScriptDB.TipoProductoEntry.TABLE_NAME + " t4, "
                + ScriptDB.MaquinariaEntry.TABLE_NAME + " t5, "
                + ScriptDB.TipoTareaEntry.TABLE_NAME + " t6, "
                + ScriptDB.SectorEntry.TABLE_NAME + " t7 "
                + WHERE + "t1.Id_producto = t2.Id_producto AND "
                + " t1.Id_empleado = t3.Id_empleado AND "
                + " t1.Id_tipo_producto = t4.Id_tipo_producto AND "
                + " t1.Id_maquinaria = t5.Id_maquinaria AND "
                + " t1.Id_tipo_tarea = t6.Id_tipo_tarea AND "
                + " t1.Id_sector = t7.Id_sector AND "
                + " t1.Id_usuario = " + id_usuario + "  ";
                if(estado.equals("Criado")){
                    selectQuery += " and t1.estado = 'Criado' ";
                }
                if(estado.equals("Cargado")){
                    selectQuery += " and t1.estado = 'Cargado' ";
                }

                selectQuery += " ORDER BY t1.Fecha_trabajo desc ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Tarefa conf = new Tarefa();
            conf.setId_tarea(Integer.valueOf(cursor.getString(0)));
            conf.setId_usuario(Integer.valueOf(cursor.getString(1)));
            conf.setId_producto(Integer.valueOf(cursor.getString(2)));
            conf.setNombre_Producto(cursor.getString(3));
            conf.setId_empleado(Integer.valueOf(cursor.getString(4)));
            conf.setNombre_Empleado(cursor.getString(5));
            conf.setContacto_Empleado(cursor.getString(6));
            conf.setId_tipo_producto(Integer.valueOf(cursor.getString(7)));
            conf.setNombre_Producto(cursor.getString(8));
            conf.setId_maquinaria(Integer.valueOf(cursor.getString(9)));
            conf.setNombre_Maquinaria(cursor.getString(10));
            conf.setId_tipo_tarea(Integer.valueOf(cursor.getString(11)));
            conf.setNombre_Tipo_Tarea(cursor.getString(12));
            conf.setId_sector(Integer.valueOf(cursor.getString(13)));
            conf.setNombre_Sector(cursor.getString(14));
            conf.setNombre_Tarea(cursor.getString(15));
            conf.setDescripcion(cursor.getString(16));
            conf.setFecha_trabajo(cursor.getString(17));
            conf.setHoras_trabajadas(Integer.valueOf(cursor.getString(18)));
            conf.setHectareas_trabajadas(Integer.valueOf(cursor.getString(19)));
            conf.setCantidad_producto(Integer.valueOf(cursor.getString(20)));

            tarefas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tarefas;
    }


    public ArrayList<Tax> ConsultaTaxas() {
        db = helperDB.getWritableDatabase();
        ArrayList<Tax> taxas = new ArrayList<Tax>();
        String selectQuery = SELECT + " id_taxa, "
                + " ano, mes, dia, mes_descricao, taxa, "
                //+ " ISNULL(CONVERT( VARCHAR(10), data_atualizacao ,120),'') as data_atualizacao "
                + " data_atualizacao as data_atualizacao "
                + FROM + ScriptDB.TaxasEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Tax conf = new Tax();
            conf.setId_taxa(Integer.valueOf(cursor.getString(0)));
            conf.setAno(Integer.valueOf(cursor.getString(1)));
            conf.setMes(Integer.valueOf(cursor.getString(2)));
            conf.setDia(Integer.valueOf(cursor.getString(3)));
            conf.setMes_descricao(cursor.getString(4));
            conf.setTaxa(Integer.valueOf(cursor.getString(5)));
            conf.setData_atualizacao(cursor.getString(6));

            taxas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return taxas;
    }


    public ArrayList<TipoContrato> ConsultaTipoContrato() {
        db = helperDB.getWritableDatabase();
        ArrayList<TipoContrato> tipocontratos = new ArrayList<TipoContrato>();
        String selectQuery = SELECT + " Id_tipo_contrato, "
                + " Nombre "
                + FROM + ScriptDB.TipoContratoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            TipoContrato conf = new TipoContrato();
            conf.setId_tipo_contrato(Integer.valueOf(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));

            tipocontratos.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tipocontratos;
    }


    public ArrayList<TipoDespesa> ConsultaTipoDespesa() {
        db = helperDB.getWritableDatabase();
        ArrayList<TipoDespesa> tipodespesas = new ArrayList<TipoDespesa>();
        String selectQuery = SELECT + " Id_tipo_despesa, "
                + " Nombre "
                + FROM + ScriptDB.TipoDespesaEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            TipoDespesa conf = new TipoDespesa();
            conf.setId_tipo_despesa(Integer.valueOf(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));

            tipodespesas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tipodespesas;
    }



    public ArrayList<TipoDespesaTempo> ConsultaTipoDespesaTempo() {
        db = helperDB.getWritableDatabase();

        ArrayList<TipoDespesaTempo> tipodespesastempo = new ArrayList<TipoDespesaTempo>();
        String selectQuery = SELECT + " Id_tipo_despesa_tempo, "
                + " Nombre "
                + FROM + ScriptDB.TipoDespesaTempoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            TipoDespesaTempo conf = new TipoDespesaTempo();
            conf.setId_tipo_despesa_tempo(Integer.valueOf(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));

            tipodespesastempo.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tipodespesastempo;
    }



    public ArrayList<TipoProducto> ConsultaTipoProducto() {
        db = helperDB.getWritableDatabase();
        ArrayList<TipoProducto> tipoprodutos = new ArrayList<TipoProducto>();
        String selectQuery = SELECT + " Id_tipo_producto, "
                + " Nombre "
                + FROM + ScriptDB.TipoProductoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            TipoProducto conf = new TipoProducto();
            conf.setId_tipo_producto(Integer.valueOf(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));

            tipoprodutos.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tipoprodutos;
    }


    public ArrayList<TipoTarefa> ConsultaTipoTarea() {

        db = helperDB.getWritableDatabase();
        ArrayList<TipoTarefa> tipotarefas = new ArrayList<TipoTarefa>();
        String selectQuery = SELECT + " Id_tipo_tarea, "
                + " Nombre "
                + FROM + ScriptDB.TipoTareaEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            TipoTarefa conf = new TipoTarefa();
            conf.setId_tipo_tarea(Integer.valueOf(cursor.getString(0)));
            conf.setNombre(cursor.getString(1));

            tipotarefas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return tipotarefas;
    }


    public ArrayList<Usuario> ConsultaUsuario(String login, String senha) {

        db = helperDB.getWritableDatabase();

        Log.d("encripta(login)",encripta(login));
        Log.d("encripta(senha)",encripta(senha));

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String selectQuery = SELECT + " codigo, "
                + " nome, login, senha, email, status, perfil"
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME
                + WHERE + " login = '" + encripta(login) + "' AND senha = '" + encripta(senha) + "'";

        String selectQuery2 = SELECT + " codigo, "
                + " nome, login, senha, email, status, perfil"
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME;

        Cursor cursor1 = db.rawQuery(selectQuery2, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("cursor1",String.valueOf(cursor1.getCount()));
        Log.d("cursor",String.valueOf(cursor.getCount()));


        if (cursor1.moveToFirst()) {

            do{
                Log.d("setCodigo",String.valueOf(cursor1.getInt(0)));
                Log.d("setNome",String.valueOf(cursor1.getString(1)));
                Log.d("setLogin",String.valueOf(cursor1.getString(2)));
                Log.d("setSenha",String.valueOf(cursor1.getString(3)));
                Log.d("setEmail",String.valueOf(cursor1.getString(4)));
                Log.d("setStatus",String.valueOf(cursor1.getString(5)));
                Log.d("setStatus",String.valueOf(cursor1.getString(6)));

                Log.d("setCodigo",decripta(String.valueOf(cursor1.getInt(0))));
                Log.d("setNome",String.valueOf(decripta(cursor1.getString(1))));
                Log.d("setLogin",String.valueOf(decripta(cursor1.getString(2))));
                Log.d("setSenha",String.valueOf(decripta(cursor1.getString(3))));
                Log.d("setEmail",String.valueOf(decripta(cursor1.getString(4))));
                Log.d("setStatus",String.valueOf(decripta(cursor1.getString(5))));
                Log.d("setStatus",String.valueOf(decripta(cursor1.getString(6))));

            } while (cursor1.moveToNext());

        }

        if (cursor.moveToFirst()) {

            do{

                Log.d("setCodigo",String.valueOf(cursor.getInt(0)));
                Log.d("setNome",String.valueOf(cursor.getString(1)));
                Log.d("setLogin",String.valueOf(cursor.getString(2)));
                Log.d("setSenha",String.valueOf(cursor.getString(3)));
                Log.d("setEmail",String.valueOf(cursor.getString(4)));
                Log.d("setStatus",String.valueOf(cursor.getString(5)));
                Log.d("setStatus",String.valueOf(cursor.getString(6)));

                Usuario conf = new Usuario();
                conf.setCodigo(decripta(String.valueOf(cursor.getInt(0))));
                conf.setNome(decripta(cursor.getString(1)));
                conf.setLogin(decripta(cursor.getString(2)));
                conf.setSenha(decripta(cursor.getString(3)));
                conf.setEmail(decripta(cursor.getString(4)));
                conf.setStatus(decripta(cursor.getString(5)));
                conf.setPerfil(decripta(cursor.getString(6)));

                Log.d("setCodigo",decripta(String.valueOf(cursor.getInt(0))));
                Log.d("setNome",String.valueOf(decripta(cursor.getString(1))));
                Log.d("setLogin",String.valueOf(decripta(cursor.getString(2))));
                Log.d("setSenha",String.valueOf(decripta(cursor.getString(3))));
                Log.d("setEmail",String.valueOf(decripta(cursor.getString(4))));
                Log.d("setStatus",String.valueOf(decripta(cursor.getString(5))));
                Log.d("setStatus",String.valueOf(decripta(cursor.getString(6))));


            usuarios.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        cursor1.close();
        db.close();
        return usuarios;
    }



    public ArrayList<Usuario> ConsultaUsuarioAtivar() {
        db = helperDB.getWritableDatabase();

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String selectQuery = SELECT + " codigo, "
                + " nome, login, '' as senha, email, status"
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
            Usuario conf = new Usuario();
            conf.setCodigo(decripta(cursor.getString(0)));
            conf.setNome(decripta(cursor.getString(1)));
            conf.setLogin(decripta(cursor.getString(2)));
            conf.setSenha(decripta(cursor.getString(3)));
            conf.setEmail(decripta(cursor.getString(4)));
            conf.setStatus(decripta(cursor.getString(5)));

            usuarios.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return usuarios;
    }


    public ArrayList<VentaGado> ConsultaVentaGado(int id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<VentaGado> ventagados = new ArrayList<VentaGado>();
        String selectQuery = SELECT + " t2.id_venta_gado, "
                + " t3.id_animal, "
                + " t3.nome_gado as nombre, "
                + " t3.precio, "
                + " t3.id_venta_gado_detalle, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t2.fecha_venta ,120),'') as fecha_venta "
                + " t2.fecha_venta as fecha_venta, "
                + " t2.id_venta_gado as t2id_venta_gado, "
                + " t3.id_venta_gado as t3id_venta_gado, "
                + " t3.id_usuario "
                + FROM + ScriptDB.VentaGadoEntry.TABLE_NAME + " t2, "
                + ScriptDB.VentaGadoDetalleEntry.TABLE_NAME + " t3 "
                + WHERE + " t3.id_venta_gado = t2.id_venta_gado "
                + "and t3.id_usuario = " + id_usuario + "";
        if(estado.equals("Criado")){
            selectQuery += " and t2.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t2.estado = 'Cargado' ";
        }

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {

            do{
                VentaGado conf = new VentaGado();
                conf.setId_venta_gado(Integer.valueOf(cursor.getString(0)));
                conf.setId_animal(Integer.valueOf(cursor.getString(1)));
                conf.setNombre(cursor.getString(2));
                conf.setPrecio(Integer.valueOf(cursor.getString(3)));
                conf.setId_venta_gado_detalle(Integer.valueOf(cursor.getString(4)));
                conf.setFecha_venta(cursor.getString(5));
                conf.setId_venta_gado(Integer.valueOf(cursor.getString(6)));
                conf.setId_usuario(Integer.valueOf(cursor.getString(8)));


            Log.d("Id_venta_gado",cursor.getString(0));
                Log.d("Id_animal",cursor.getString(1));
                Log.d("Nombre",cursor.getString(2));
                Log.d("Precio",cursor.getString(3));
                Log.d("Id_venta_gado_detalle",cursor.getString(4));
                Log.d("Fecha_venta",cursor.getString(5));
                Log.d("id_venta_gadot2",cursor.getString(6));
                Log.d("id_venta_gadot3",cursor.getString(7));
                Log.d("setId_usuario",cursor.getString(8));

            ventagados.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return ventagados;
    }



    public ArrayList<Visita> ConsultaVisitas(int id_usuario, String estado) {
        db = helperDB.getWritableDatabase();
        ArrayList<Visita> visitas = new ArrayList<Visita>();
        String selectQuery = SELECT + " t1.id_visitas,t1.usuario,t1.latitude,t1.longitude, t1.pim, t1.imei, t1.versao, "
                + " t1.cliente,t2.nombre as N_Cliente,t1.motivo, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_agenda ,120),'') as data_agenda, "
                + " t1.data_agenda as data_agenda, "
                //+ " ISNULL(CONVERT( VARCHAR(10), t1.data_visita ,120),'') as data_visita, "
                + " t1.data_visita as data_visita, "
                + " t1.resultado,t1.deslocamento,t1.situacao,t1.obs,t1.cadastrante "
                + FROM + ScriptDB.VisitasEntry.TABLE_NAME + " t1, "
                + ScriptDB.ClienteEntry.TABLE_NAME + " t2 "
                + WHERE + " t1.cliente = t2.id_cliente and t1.usuario = " + id_usuario + " ";
        if(estado.equals("Criado")){
            selectQuery += " and t1.estado = 'Criado' ";
        }
        if(estado.equals("Cargado")){
            selectQuery += " and t1.estado = 'Cargado' ";
        }

        selectQuery += " order by t1.data_visita desc ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{

                Visita conf = new Visita();
                conf.setId_visita(Integer.valueOf(cursor.getString(0)));
                conf.setUsuario(Integer.valueOf(cursor.getString(1)));
                conf.setLatitude(cursor.getString(2));
                conf.setLongitude(cursor.getString(3));
                conf.setPim(cursor.getString(4));
                conf.setImei(cursor.getString(5));
                conf.setVersao(cursor.getString(6));
                conf.setCliente(Integer.valueOf(cursor.getString(7)));
                conf.setN_Cliente(cursor.getString(8));
                conf.setMotivo(cursor.getString(9));
                conf.setData_agenda(cursor.getString(10));
                conf.setData_visita(cursor.getString(11));
                conf.setResultado(cursor.getString(12));
                conf.setDeslocamento(Integer.valueOf(cursor.getString(13)));
                conf.setSituacao(cursor.getString(14));
                conf.setObs(cursor.getString(15));
                conf.setCadastrante(Integer.valueOf(cursor.getString(16)));

            visitas.add(conf);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return visitas;
    }


    public void inserirUsuario(Usuario usuario) {
        db = helperDB.getWritableDatabase();

        int codigo_usuario = 0;

        String selectQuery1 = SELECT + " ifnull(max(codigo),0) "
                + FROM + ScriptDB.UsuariosEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery1, null);

        if (cursor.moveToFirst()) {
            codigo_usuario = cursor.getInt(0);
        }

        cursor.close();


        ContentValues values = new ContentValues();
        values.put("codigo", codigo_usuario+1);
        values.put("nome", usuario.getNome());
        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        values.put("status", "ativo");
        values.put("perfil", "2");

        db.insert(ScriptDB.UsuariosEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void insesirUsuarioOffline(Usuario usuario) {

        db = helperDB.getWritableDatabase();

        String query = "select codigo from usuarios where login='" + encripta(usuario.getLogin()) + "' and senha='" + usuario.getSenha() + "'";
        Cursor cursor = this.db.rawQuery(query,null);

        try{

            Log.d("update","update");
            cursor.moveToFirst();
            Log.d("update actualizado","update actualizado");
            String scod = cursor.getString(0);

            ContentValues values = new ContentValues();
            values.put("nome", encripta(usuario.getNome()));
            values.put("login", encripta(usuario.getLogin()));
            values.put("senha", usuario.getSenha());
            values.put("email", encripta(usuario.getEmail()));
            values.put("status", encripta(usuario.getStatus()));
            values.put("perfil", encripta(usuario.getPerfil()));

            String[] args = new String[]{String.valueOf(scod)};
            db.update("usuarios", values, " codigo=? ", args);

        }catch(Exception ex){

            Log.d("insert","insert");
            Log.d("offline_codigo",String.valueOf(usuario.getCodigo()));
            Log.d("offline_nome",usuario.getNome());
            Log.d("offline_login",usuario.getLogin());
            Log.d("offline_senha",usuario.getSenha());
            Log.d("offline_email",usuario.getEmail());
            Log.d("offline_status",usuario.getStatus());
            Log.d("offline_perfil",usuario.getPerfil());

            ContentValues values = new ContentValues();
            values.put("codigo", encripta(usuario.getCodigo()));
            values.put("nome", encripta(usuario.getNome()));
            values.put("login", encripta(usuario.getLogin()));
            values.put("senha", usuario.getSenha());
            values.put("email", encripta(usuario.getEmail()));
            values.put("status", encripta(usuario.getStatus()));
            values.put("perfil", encripta(usuario.getPerfil()));
            db.insert(ScriptDB.UsuariosEntry.TABLE_NAME, null, values);

            Log.d("offline_codigo",encripta(String.valueOf(usuario.getCodigo())));
            Log.d("offline_nome",encripta(usuario.getNome()));
            Log.d("offline_login",encripta(usuario.getLogin()));
            Log.d("offline_senha",encripta(usuario.getSenha()));
            Log.d("offline_email",encripta(usuario.getEmail()));
            Log.d("offline_status",encripta(usuario.getStatus()));
            Log.d("offline_perfil",encripta(usuario.getPerfil()));


        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

    }

    public void inserirColheita(Colheita colheita) {
        db = helperDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("Id_cosecha", colheita.getId_cosecha());
        values.put("Nombre", colheita.getNombre());
        values.put("id_usuario", colheita.getId_usuario());

        db.insert(ScriptDB.CosechaEntry.TABLE_NAME, null, values);
        db.close();

    }


    public void inserirMenuModel(MenuModel menu) {

        db = helperDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", menu.getNombre());
            values.put("imagen", menu.getImage());

            db.insert(ScriptDB.MenuEntry.TABLE_NAME, null, values);
        db.close();


    }

    public List<MenuModel> Menulist(){


            List<MenuModel> menu = new ArrayList<MenuModel>();
            menu.add(new MenuModel(1, "Setores", "field"));
            menu.add(new MenuModel(2, "Safras", "cultivo"));
            menu.add(new MenuModel(3, "Colheitas", "sprout_cosecha"));
            menu.add(new MenuModel(4, "Pragas", "plague"));
            menu.add(new MenuModel(5, "Cotacoes", "cotacoes"));
            menu.add(new MenuModel(6, "Clientes", "cliente"));
            menu.add(new MenuModel(7, "Maquinarias", "tractor"));
            menu.add(new MenuModel(8, "Empregados", "farmer"));
            menu.add(new MenuModel(9, "AlertaPragas", "no-mosquito"));
            menu.add(new MenuModel(10, "Visitas", "visita"));
            menu.add(new MenuModel(11, "Negociacoes", "money-bag"));
            menu.add(new MenuModel(12, "Taxas", "tax"));
            menu.add(new MenuModel(13, "Preco", "price"));
            menu.add(new MenuModel(14, "Produtos", "productos"));
            menu.add(new MenuModel(15, "Premios", "premios"));
            menu.add(new MenuModel(16, "Despesas", "despesa"));
            menu.add(new MenuModel(17, "Mensagems", "message"));
            menu.add(new MenuModel(18, "PontoInteresse", "map-location"));
            menu.add(new MenuModel(19, "Tarefas", "trolley"));
            menu.add(new MenuModel(20, "Balance", "balance"));
            menu.add(new MenuModel(21, "AlterarSenha", "password"));
            menu.add(new MenuModel(22, "Gado", "gado"));
            menu.add(new MenuModel(23, "VentaGado", "gado"));
            menu.add(new MenuModel(24, "Solo", "sprout"));
            menu.add(new MenuModel(25, "Consulta", "map-location"));
            menu.add(new MenuModel(26, "AtivarUsuarios", "cliente"));


        return menu;
/*        for (int i = 0; i<menu.size(); i++ ) {

            ContentValues values = new ContentValues();
            values.put("nombre", menu.get(i).getNombre());
            values.put("imagen", menu.get(i).getImage());

            db.insert(ScriptDB.MenuEntry.TABLE_NAME, null, values);

        }*/


    }

    public void MenulistAdd(List<MenuModel> menu){

        db = helperDB.getWritableDatabase();

        db.delete(ScriptDB.MenuEntry.TABLE_NAME, null, null);

//        String selectQuery1 = SELECT + " * "
//                + FROM + ScriptDB.MenuEntry.TABLE_NAME;
//
//        SQLiteDatabase db = helperDB.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery1, null);
//
//        Log.d("cursor",String.valueOf(cursor.getCount()));
//
//        if ( cursor.getCount() == 0) {
//
//            List<MenuModel> menu = new ArrayList<MenuModel>();
//            menu.add(new MenuModel(1, "Setores", "field"));
//            menu.add(new MenuModel(2, "Safras", "cultivo"));
//            menu.add(new MenuModel(3, "Colheitas", "sprout_cosecha"));
//            menu.add(new MenuModel(4, "Pragas", "plague"));
//            menu.add(new MenuModel(5, "Cotacoes", "cotacoes"));
//            menu.add(new MenuModel(6, "Clientes", "cliente"));
//            menu.add(new MenuModel(7, "Maquinarias", "tractor"));
//            menu.add(new MenuModel(8, "Empregados", "farmer"));
//            menu.add(new MenuModel(9, "AlertaPragas", "no-mosquito"));
//            menu.add(new MenuModel(10, "Visitas", "visita"));
//            menu.add(new MenuModel(11, "Negociacoes", "money-bag"));
//            menu.add(new MenuModel(12, "Taxas", "tax"));
//            menu.add(new MenuModel(13, "Preco", "price"));
//            menu.add(new MenuModel(14, "Produtos", "productos"));
//            menu.add(new MenuModel(15, "Premios", "premios"));
//            menu.add(new MenuModel(16, "Despesas", "despesa"));
//            menu.add(new MenuModel(17, "Mensagems", "message"));
//            menu.add(new MenuModel(18, "PontoInteresse", "map-location"));
//            menu.add(new MenuModel(19, "Tarefas", "trolley"));
//            menu.add(new MenuModel(20, "Balance", "balance"));
//            menu.add(new MenuModel(21, "AlterarSenha", "password"));
//            menu.add(new MenuModel(22, "Gado", "gado"));
//            menu.add(new MenuModel(23, "VentaGado", "gado"));
//            menu.add(new MenuModel(24, "Solo", "sprout"));
//            menu.add(new MenuModel(25, "Consulta", "map-location"));
//            menu.add(new MenuModel(26, "AtivarUsuarios", "cliente"));
//            menu.add(new MenuModel(27, "Sincronizacao", "map-location"));

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("id_menu", menu.get(i).getId_menu());
                values.put("nombre", menu.get(i).getNombre());
                values.put("imagen", menu.get(i).getImage());


                db.insert(ScriptDB.MenuEntry.TABLE_NAME, null, values);

            }

        db.close();
    }


    public void TipoContratolistAdd(List<TipoContrato> menu){

        db = helperDB.getWritableDatabase();
        db.delete(ScriptDB.TipoContratoEntry.TABLE_NAME,null,null);

        String selectQuery1 = SELECT + " * "
                + FROM + ScriptDB.TipoContratoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if ( cursor.getCount() == 0) {

//            List<TipoContrato> menu = new ArrayList<TipoContrato>();
//            menu.add(new TipoContrato(1, "Mensal"));
//            menu.add(new TipoContrato(2, "Horas"));

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("Id_tipo_contrato", menu.get(i).getId_tipo_contrato());
                values.put("Nombre", menu.get(i).getNombre());

                db.insert(ScriptDB.TipoContratoEntry.TABLE_NAME, null, values);
            }
        }

        cursor.close();
        db.close();

    }


    public void TipoDespesalistAdd(List<TipoDespesa> menu){

        db = helperDB.getWritableDatabase();

        db.delete(ScriptDB.TipoDespesaEntry.TABLE_NAME,null,null);

        String selectQuery1 = SELECT + " * "
                + FROM + ScriptDB.TipoDespesaEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if ( cursor.getCount() == 0) {


//            List<TipoDespesa> menu = new ArrayList<TipoDespesa>();
//            menu.add(new TipoDespesa(1, "Tierra"));
//            menu.add(new TipoDespesa(2, "Seguro"));
//            menu.add(new TipoDespesa(3, "Despesa"));
//            menu.add(new TipoDespesa(4, "Manutencao"));
//            menu.add(new TipoDespesa(5, "Contrato"));
//            menu.add(new TipoDespesa(6, "Aluguel"));

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("Id_tipo_despesa", menu.get(i).getId_tipo_despesa());
                values.put("Nombre", menu.get(i).getNombre());

                db.insert(ScriptDB.TipoDespesaEntry.TABLE_NAME, null, values);
            }
        }

        cursor.close();
        db.close();

    }


    public void TipoDespesaTempolistAdd(List<TipoDespesaTempo> menu){

        db = helperDB.getWritableDatabase();

        db.delete(ScriptDB.TipoDespesaTempoEntry.TABLE_NAME,null,null);

        String selectQuery1 = SELECT + " * "
                + FROM + ScriptDB.TipoDespesaTempoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if ( cursor.getCount() == 0) {

//            List<TipoDespesaTempo> menu = new ArrayList<TipoDespesaTempo>();
//            menu.add(new TipoDespesaTempo(1, "Unitario"));
//            menu.add(new TipoDespesaTempo(2, "Mensal"));
//            menu.add(new TipoDespesaTempo(3, "Anual"));

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("Id_tipo_despesa_tempo", menu.get(i).getId_tipo_despesa_tempo());
                values.put("Nombre", menu.get(i).getNombre());

                db.insert(ScriptDB.TipoDespesaTempoEntry.TABLE_NAME, null, values);
            }
        }

        cursor.close();
        db.close();

    }


    public void TipoProductolistAdd(List<TipoProducto> menu){

        db = helperDB.getWritableDatabase();

        db.delete(ScriptDB.TipoProductoEntry.TABLE_NAME,null,null);

        String selectQuery1 = SELECT + " * "
                + FROM + ScriptDB.TipoProductoEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if ( cursor.getCount() == 0) {

/*            List<TipoProducto> menu = new ArrayList<TipoProducto>();
            menu.add(new TipoProducto(1, "Consumo"));
            menu.add(new TipoProducto(2, "Fertilizante"));
            menu.add(new TipoProducto(3, "Semeadura"));*/

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("Id_tipo_producto", menu.get(i).getId_tipo_producto());
                values.put("Nombre", menu.get(i).getNombre());

                db.insert(ScriptDB.TipoProductoEntry.TABLE_NAME, null, values);
            }
        }

        cursor.close();
        db.close();

    }


    public void TipoTarealistAdd(List<TipoTarefa> menu){

        db = helperDB.getWritableDatabase();

        db.delete(ScriptDB.TipoTareaEntry.TABLE_NAME,null,null);

        String selectQuery1 = SELECT + " * "
                + FROM + ScriptDB.TipoTareaEntry.TABLE_NAME;

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        Log.d("cursor",String.valueOf(cursor.getCount()));

        if ( cursor.getCount() == 0) {

//            List<TipoTarefa> menu = new ArrayList<TipoTarefa>();
//            menu.add(new TipoTarefa(1, "Fertilização"));
//            menu.add(new TipoTarefa(2, "Inspeção"));

            for (int i = 0; i < menu.size(); i++) {

                ContentValues values = new ContentValues();
                values.put("Id_tipo_tarea", menu.get(i).getId_tipo_tarea());
                values.put("Nombre", menu.get(i).getNombre());

                db.insert(ScriptDB.TipoTareaEntry.TABLE_NAME, null, values);
            }
        }

        cursor.close();
        db.close();

    }



    public void ManutencaoAlertaPlaga(JSONObject alertapraga) {
        db = helperDB.getWritableDatabase();

        try {

            switch (alertapraga.getString("acao")) {
                case "I":

                    int codigo_id = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_alerta_plaga),0) "
                            + FROM + ScriptDB.Alerta_plagaEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_id = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_sector", alertapraga.getInt("Id_sector"));
                    values.put("Id_plaga", alertapraga.getInt("Id_plaga"));
                    values.put("Nombre", alertapraga.getString("Nombre"));
                    values.put("Fecha_registro", alertapraga.getString("Fecha_registro"));
                    values.put("Descripcion", alertapraga.getString("Descripcion"));
                    values.put("Status", alertapraga.getString("Status"));
                    values.put("Id_alerta_plaga", String.valueOf(codigo_id+1));
                    values.put("id_usuario", alertapraga.getInt("id_usuario"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Alerta_plagaEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    db.delete("alerta_plaga","Id_alerta_plaga=?",new String[]{String.valueOf(alertapraga.getInt("Id_alerta_plaga"))});
                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    values1.put("Id_sector", alertapraga.getInt("Id_sector"));
                    values1.put("Id_plaga", alertapraga.getInt("Id_plaga"));
                    values1.put("Nombre", alertapraga.getString("Nombre"));
                    values1.put("Fecha_registro", alertapraga.getString("Fecha_registro"));
                    values1.put("Descripcion", alertapraga.getString("Descripcion"));
                    values1.put("Status", alertapraga.getString("Status"));
                    //values1.put("id_usuario", alertapraga.getInt("id_usuario"));

                    db.update("alerta_plaga",values1,"Id_alerta_plaga=?",new String[]{String.valueOf(alertapraga.getInt("Id_alerta_plaga"))});
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void ManutencaoAlterarSenha(JSONObject altersenha) {
        db = helperDB.getWritableDatabase();

        try {

            switch (altersenha.getString("acao")) {
                case "I":

                    ContentValues values = new ContentValues();
                    values.put("senha", altersenha.getString("nova_senha"));

                    String[] args = new String[]{String.valueOf(altersenha.getInt("login")), String.valueOf(altersenha.getInt("codigo"))};

                    db.update("usuarios", values, "login=? AND codigo=?", args);
                    db.close();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void ManutencaoClientes(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

//                    Calendar c = Calendar.getInstance();
//                    String Agora = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
//                    Agora += "/" + Integer.toString(c.get(Calendar.MONTH) + 1);
//                    Agora += "/" + Integer.toString(c.get(Calendar.YEAR));
//                    Agora += " " + Integer.toString(c.get(Calendar.HOUR_OF_DAY));
//                    Agora += ":" + Integer.toString(c.get(Calendar.MINUTE));
//                    Agora += ":" + Integer.toString(c.get(Calendar.SECOND));

                    int codigo_id = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_cliente),0) "
                            + FROM + ScriptDB.ClienteEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_id = cursor.getInt(0);
                    }

                    cursor.close();


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate2 = df2.format(c.getTime()).replace(" ","");

                    ContentValues values = new ContentValues();
                    values.put("id_usuario", obj.getInt("id_usuario"));
                    values.put("nombre", obj.getString("nombre"));
                    values.put("organizacion", obj.getString("organizacion"));
                    values.put("numero", obj.getString("numero"));
                    values.put("direccion", obj.getString("direccion"));
                    values.put("area", obj.getString("area"));
                    values.put("Id_cliente", String.valueOf(codigo_id+1));
                    values.put("cpf", obj.getString("cpf"));
                    values.put("data_insercao", formattedDate2);
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.ClienteEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_cliente"))};
                    db.delete(ScriptDB.ClienteEntry.TABLE_NAME,"Id_cliente=?", args);
                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    values1.put("id_usuario", obj.getInt("id_usuario"));
                    values1.put("nombre", obj.getString("nombre"));
                    values1.put("organizacion", obj.getString("organizacion"));
                    values1.put("numero", obj.getString("numero"));
                    values1.put("direccion", obj.getString("direccion"));
                    values1.put("area", obj.getString("area"));
                    //values1.put("Id_cliente", obj.getInt("Id_cliente"));
                    values1.put("cpf", obj.getString("cpf"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_cliente"))};

                    db.update(ScriptDB.ClienteEntry.TABLE_NAME,values1,"Id_cliente=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void DeleteInfoPontoInteresse(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.MapearEntry.TABLE_NAME," usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.MapearEntry.TABLE_NAME," usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.MapearEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoVentaGado(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.VentaGadoEntry.TABLE_NAME, null,null);
            db.delete(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoCultivo(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            //String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.CultivoEntry.TABLE_NAME,null,null);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.CultivoEntry.TABLE_NAME,null,null);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.CultivoEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoAlertaPraga(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.Alerta_plagaEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.Alerta_plagaEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Alerta_plagaEntry.TABLE_NAME, null,null);

        }
        db.close();
    }



    public void DeleteInfoSolo(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.Consulta_SoloEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.Consulta_SoloEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Consulta_SoloEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoTarefa(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.TareaEntry.TABLE_NAME," Id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.TareaEntry.TABLE_NAME," Id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.TareaEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoPremio(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.PremiosEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.PremiosEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.PremiosEntry.TABLE_NAME, null,null);

        }
        db.close();
    }

    public void DeleteInfoColeita(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.CosechaEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.CosechaEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.CosechaEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoProduto(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.ProductoEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.ProductoEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.ProductoEntry.TABLE_NAME, null,null);

        }
        db.close();
    }



    public void DeleteInfoEmpregado(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.EmpleadoEntry.TABLE_NAME," Id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.EmpleadoEntry.TABLE_NAME," Id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.EmpleadoEntry.TABLE_NAME, null,null);

        }
        db.close();


    }

    public void DeleteInfoMaquinaria(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.MaquinariaEntry.TABLE_NAME," Id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.MaquinariaEntry.TABLE_NAME," Id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.MaquinariaEntry.TABLE_NAME, null,null);

        }
        db.close();

    }

    public void DeleteInfoCliente(int id_usuario,String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.ClienteEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.ClienteEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.ClienteEntry.TABLE_NAME, null,null);

        }
        db.close();

    }

    public void DeleteInfoDespesa(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.DespesasEntry.TABLE_NAME," usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.DespesasEntry.TABLE_NAME," usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.DespesasEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoPraga(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.PlagaEntry.TABLE_NAME," usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.PlagaEntry.TABLE_NAME," usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.PlagaEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoNegociacoes(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.NegociacoesEntry.TABLE_NAME," usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.NegociacoesEntry.TABLE_NAME," usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.NegociacoesEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoVisita(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.VisitasEntry.TABLE_NAME," usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.VisitasEntry.TABLE_NAME," usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.VisitasEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoLote(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.Lote_gadoEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.Lote_gadoEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Lote_gadoEntry.TABLE_NAME, null,null);

        }
        db.close();
    }

    public void DeleteInfoGado(int id_usuario, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_usuario),estado};
            db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_usuario=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_usuario)};
            db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_usuario=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.AnimalEntry.TABLE_NAME, null,null);
            db.delete(ScriptDB.PartoEntry.TABLE_NAME, null,null);
            db.delete(ScriptDB.CompraEntry.TABLE_NAME, null,null);

        }
        db.close();
    }


    public void DeleteInfoHistorialEngorde(int id_animal, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_animal),estado};
            db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_animal=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_animal)};
            db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_animal=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME, null,null);

        }
        db.close();
    }

    public void DeleteInfoHistorialConsumo(int id_animal, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_animal),estado};
            db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_animal=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_animal)};
            db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_animal=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME, null,null);

        }
        db.close();
    }

    public void DeleteInfoHistorialLeite(int id_animal, String estado){

        db = helperDB.getWritableDatabase();
        if (estado.equals("Criado")){

            String[] args1 = new String[]{String.valueOf(id_animal),estado};
            db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_animal=? and estado=?",args1);

        } else if(estado.equals("Usuario")){

            String[] args1 = new String[]{String.valueOf(id_animal)};
            db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_animal=? ",args1);

        } else if(estado.equals("X")){

            db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME, null,null);

        }
        db.close();
    }

    public void DeleteInfoSectorCoordendas(int id_usuario,String estado){
        db = helperDB.getWritableDatabase();

        if (estado.equals("Criado")){

            String selectQuery = SELECT + " Id_sector "
                    + FROM + ScriptDB.SectorEntry.TABLE_NAME
                    + WHERE + " Id_usuario = " + id_usuario + " and estado='Criado' ";

            SQLiteDatabase db = helperDB.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                do{

                    String[] args1 = new String[]{String.valueOf(cursor.getInt(0))};
                    String[] args2 = new String[]{String.valueOf(cursor.getInt(0)),String.valueOf(id_usuario)};
                    db.delete(ScriptDB.CoordenadasEntry.TABLE_NAME," id_sector=? ", args1);
                    db.delete(ScriptDB.SectorEntry.TABLE_NAME," Id_sector=? AND Id_usuario=? ", args2);

                } while (cursor.moveToNext());

            }
            cursor.close();

        } else if(estado.equals("Usuario")){
            String selectQuery = SELECT + " Id_sector "
                    + FROM + ScriptDB.SectorEntry.TABLE_NAME
                    + WHERE + " Id_usuario = " + id_usuario + " ";

            SQLiteDatabase db = helperDB.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                do{

                    String[] args1 = new String[]{String.valueOf(cursor.getInt(0))};
                    String[] args2 = new String[]{String.valueOf(cursor.getInt(0)),String.valueOf(id_usuario)};
                    db.delete(ScriptDB.CoordenadasEntry.TABLE_NAME," id_sector=? ", args1);
                    db.delete(ScriptDB.SectorEntry.TABLE_NAME," Id_sector=? AND Id_usuario=? ", args2);

                } while (cursor.moveToNext());

            }
            cursor.close();
        } else if(estado.equals("X")){

            db.delete(ScriptDB.CoordenadasEntry.TABLE_NAME, null,null);
            db.delete(ScriptDB.SectorEntry.TABLE_NAME, null,null);

        }

        db.close();
    }



    public void DeleteInfoLoteGadoHistorial(int id_usuario, String estado){
        db = helperDB.getWritableDatabase();

        String selectQuery = SELECT + " id_lote_gado "
                + FROM + ScriptDB.Lote_gadoEntry.TABLE_NAME
                + WHERE + " id_usuario = " + id_usuario + " and "
                + " estado = '" + estado + "' ";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{

                String selectQuery2 = SELECT + " id_animal "
                        + FROM + ScriptDB.AnimalEntry.TABLE_NAME
                        + WHERE + " id_usuario = " + id_usuario + AND
                        + " id_lote_gado = " + cursor.getInt(0) + " and "
                        + " estado = '" + estado + "' ";

                SQLiteDatabase db2 = helperDB.getWritableDatabase();
                Cursor cursor2 = db2.rawQuery(selectQuery2, null);


                if (cursor2.moveToFirst()) {
                    do {

                        String selectQuery3 = SELECT + " id_animal "
                                + FROM + ScriptDB.Historial_EngordeEntry.TABLE_NAME
                                + WHERE + " id_animal = " + cursor2.getInt(0) + " and "
                                + " estado = '" + estado + "' ";

                        SQLiteDatabase db3 = helperDB.getWritableDatabase();
                        Cursor cursor3 = db3.rawQuery(selectQuery3, null);

                        if (cursor3.moveToFirst()) {
                            do {

                                String[] args3 = new String[]{String.valueOf(cursor3.getInt(0))};
                                db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME, " id_animal=? ", args3);

                            } while (cursor3.moveToNext());
                        }

                        String selectQuery4 = SELECT + " id_animal "
                                + FROM + ScriptDB.Historial_ConsumoEntry.TABLE_NAME
                                + WHERE + " id_animal = " + cursor2.getInt(0) + " and "
                                + " estado = '" + estado + "' ";

                        SQLiteDatabase db4 = helperDB.getWritableDatabase();
                        Cursor cursor4 = db4.rawQuery(selectQuery4, null);

                            if (cursor4.moveToFirst()) {
                                do {

                                    String[] args4 = new String[]{String.valueOf(cursor4.getInt(0))};
                                    db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME, " id_animal=? ", args4);

                                } while (cursor4.moveToNext());

                            }

                        String selectQuery5 = SELECT + " id_animal "
                                + FROM + ScriptDB.Historial_LecheEntry.TABLE_NAME
                                + WHERE + " id_animal = " + cursor2.getInt(0) + " and "
                                + " estado = '" + estado + "' ";

                        SQLiteDatabase db5 = helperDB.getWritableDatabase();
                        Cursor cursor5 = db5.rawQuery(selectQuery5, null);

                                if (cursor5.moveToFirst()) {
                                    do {

                                        String[] args5 = new String[]{String.valueOf(cursor5.getInt(0))};
                                        db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME, " id_animal=? ", args5);

                                    } while (cursor5.moveToNext());

                                }


                        String[] args2 = new String[]{String.valueOf(cursor2.getInt(0))};
                        db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_animal=? ", args2);

                    } while (cursor2.moveToNext());

                }

                String[] args1 = new String[]{String.valueOf(id_usuario)};
                db.delete(ScriptDB.Lote_gadoEntry.TABLE_NAME," Id_usuario=? ", args1);

            } while (cursor.moveToNext());

        }

        String[] args = new String[]{String.valueOf(estado)};
        db.delete(ScriptDB.PartoEntry.TABLE_NAME," estado=? ", args);
        db.delete(ScriptDB.CompraEntry.TABLE_NAME," estado=? ", args);

        cursor.close();
        db.close();
    }

    public void CargaColhieta(ArrayList<Colheita> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            Log.d("Nombre",String.valueOf(model.get(i).getNombre()));
            Log.d("id_usuario",String.valueOf(model.get(i).getId_usuario()));
            Log.d("Id_cosecha",String.valueOf(model.get(i).getId_cosecha()));

            ContentValues values = new ContentValues();
            values.put("Nombre", model.get(i).getNombre());
            values.put("Id_cosecha", String.valueOf(model.get(i).getId_cosecha()));
            values.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("estado", "Cargado");

            db.insert(ScriptDB.CosechaEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaLote(ArrayList<LoteGado> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_lote_gado", String.valueOf(model.get(i).getId_lote_gado()));
            values.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("nombre", model.get(i).getNombre());
            values.put("descripcao", model.get(i).getDescripcao());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.Lote_gadoEntry.TABLE_NAME, null, values);

        }

        db.close();

    }





    public void CargaPlaga(ArrayList<Praga> model){

        db = helperDB.getWritableDatabase();
        Log.d("CargaPlaga","CARGAPLAGA");

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_plaga", String.valueOf(model.get(i).getId_plaga()));
            values.put("Nombre", model.get(i).getNombre());
            values.put("Caracteristicas", model.get(i).getCaracteristicas());
            values.put("Sintomas", model.get(i).getSintomas());
            values.put("Tratamiento", model.get(i).getTratamiento());
            values.put("Clase", model.get(i).getClase());
            values.put("Descripcion", model.get(i).getDescripcion());
            values.put("Prevencion", model.get(i).getPrevencion());
            values.put("estado", "Cargado");

            Log.d("Id_plaga",String.valueOf(model.get(i).getId_plaga()));
            Log.d("Nombre",String.valueOf(model.get(i).getNombre()));
            Log.d("Caracteristicas",String.valueOf(model.get(i).getCaracteristicas()));
            Log.d("Sintomas",String.valueOf(model.get(i).getSintomas()));
            Log.d("Tratamiento",String.valueOf(model.get(i).getTratamiento()));
            Log.d("Clase",String.valueOf(model.get(i).getClase()));
            Log.d("Descripcion",String.valueOf(model.get(i).getDescripcion()));
            Log.d("Prevencion",String.valueOf(model.get(i).getPrevencion()));


            db.insert(ScriptDB.PlagaEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void CargaHistorialConsumo(ArrayList<HistorialConsumo> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_historial_consumo", String.valueOf(model.get(i).getId_historial_consumo()));
            values.put("id_animal", String.valueOf(model.get(i).getId_animal()));
            values.put("id_producto", String.valueOf(model.get(i).getId_producto()));
            values.put("cantidad_consumida", String.valueOf(model.get(i).getCantidad_consumida()));
            values.put("fecha_consumo", String.valueOf(model.get(i).getFecha_consumo()));
            values.put("estado", "Cargado");

            db.insert(ScriptDB.Historial_ConsumoEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void CargaHistorialEngorde(ArrayList<HistorialEngorde> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_historial_engorde", String.valueOf(model.get(i).getId_historial_engorde()));
            values.put("id_animal", String.valueOf(model.get(i).getId_animal()));
            values.put("peso", String.valueOf(model.get(i).getPeso()));
            values.put("fecha_medicion", model.get(i).getFecha_medicion());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.Historial_EngordeEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaHistorialLeche(ArrayList<HistorialLeche> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_historial_leche", String.valueOf(model.get(i).getId_historial_leche()));
            values.put("id_animal", String.valueOf(model.get(i).getId_animal()));
            values.put("cantidad", model.get(i).getCantidad());
            values.put("fecha_obtencao", model.get(i).getFecha_obtencao());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.Historial_LecheEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaGado(ArrayList<Gado> model) {

        db = helperDB.getWritableDatabase();

        for (int i = 0; i < model.size(); i++) {

                if (model.get(i).getTipo_adquisicao().equals("Parto")) {


                    ContentValues values1 = new ContentValues();
                    values1.put("id_parto", String.valueOf(model.get(i).getCod_adquisicao()));
                    values1.put("fecha", model.get(i).getFecha());
                    values1.put("id_animal", String.valueOf(model.get(i).getId_parto()));
                    values1.put("estado", "Cargado");

                    Log.d("id_parto parto", String.valueOf(model.get(i).getCod_adquisicao()));
                    Log.d("fecha parto", model.get(i).getFecha());
                    Log.d("id_animal parto", String.valueOf(model.get(i).getId_parto()));

                    db.insert(ScriptDB.PartoEntry.TABLE_NAME, null, values1);

                    ContentValues values2 = new ContentValues();

                    values2.put("id_animal", String.valueOf(model.get(i).getId_animal()));
                    values2.put("id_lote_gado", String.valueOf(model.get(i).getId_lote_gado()));
                    values2.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
                    values2.put("nombre", model.get(i).getNombre());
                    values2.put("peso_inicial", String.valueOf(model.get(i).getPeso_inicial()));
                    values2.put("cod_adquisicao", String.valueOf(model.get(i).getCod_adquisicao()));
                    values2.put("tipo_adquisicao", model.get(i).getTipo_adquisicao());
                    values2.put("estado", "Cargado");

                    Log.d("id_animal parto", String.valueOf(model.get(i).getId_animal()));
                    Log.d("id_lote_gado parto", String.valueOf(model.get(i).getId_lote_gado()));
                    Log.d("id_usuario parto", String.valueOf(model.get(i).getId_usuario()));
                    Log.d("nombre parto", model.get(i).getNombre());
                    Log.d("peso_inicial parto", String.valueOf(model.get(i).getPeso_inicial()));
                    Log.d("cod_adquisicao parto", String.valueOf(model.get(i).getCod_adquisicao()));
                    Log.d("tipo_adquisicao parto", model.get(i).getTipo_adquisicao());

                    db.insert(ScriptDB.AnimalEntry.TABLE_NAME, null, values2);

                }

                if (model.get(i).getTipo_adquisicao().equals("Compra")) {

                    ContentValues values3 = new ContentValues();
                    values3.put("id_compra", String.valueOf(model.get(i).getCod_adquisicao()));
                    values3.put("fecha", model.get(i).getFecha());
                    values3.put("precio", model.get(i).getPrecio());
                    values3.put("estado", "Cargado");

                    Log.d("id_compra compra", String.valueOf(model.get(i).getCod_adquisicao()));
                    Log.d("fecha compra", model.get(i).getFecha());
                    Log.d("precio compra", String.valueOf(model.get(i).getPrecio()));

                    db.insert(ScriptDB.CompraEntry.TABLE_NAME, null, values3);

                    ContentValues values4 = new ContentValues();

                    values4.put("id_animal", String.valueOf(model.get(i).getId_animal()));
                    values4.put("id_lote_gado", String.valueOf(model.get(i).getId_lote_gado()));
                    values4.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
                    values4.put("nombre", model.get(i).getNombre());
                    values4.put("peso_inicial", String.valueOf(model.get(i).getPeso_inicial()));
                    values4.put("cod_adquisicao", String.valueOf(model.get(i).getCod_adquisicao()));
                    values4.put("tipo_adquisicao", model.get(i).getTipo_adquisicao());
                    values4.put("estado", "Cargado");

                    Log.d("id_animal compra", String.valueOf(model.get(i).getId_animal()));
                    Log.d("id_lote_gado compra", String.valueOf(model.get(i).getId_lote_gado()));
                    Log.d("id_usuario compra", String.valueOf(model.get(i).getId_usuario()));
                    Log.d("nombre compra", model.get(i).getNombre());
                    Log.d("peso_inicial compra", String.valueOf(model.get(i).getPeso_inicial()));
                    Log.d("cod_adquisicao compra", String.valueOf(model.get(i).getCod_adquisicao()));
                    Log.d("tipo_adquisicao compra", model.get(i).getTipo_adquisicao());


                    db.insert(ScriptDB.AnimalEntry.TABLE_NAME, null, values4);
                }



        }

        db.close();

    }

    public void CargaSolo(ArrayList<Solo2> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_consulta_solo", String.valueOf(model.get(i).getId_consulta_solo()));
            values.put("id_usuario", model.get(i).getId_usuario());
            values.put("id_sector", model.get(i).getId_sector());
            values.put("fosforo_status", model.get(i).getFosforo_status());
            values.put("fosforo_value", model.get(i).getFosforo_value());
            values.put("potasio_status", model.get(i).getPotasio_status());
            values.put("potasio_value", model.get(i).getPotasio_value());
            values.put("calcio_status", model.get(i).getCalcio_status());
            values.put("calcio_value", model.get(i).getCalcio_value());
            values.put("magnesio_status", model.get(i).getMagnesio_status());
            values.put("magnesio_value", model.get(i).getMagnesio_value());
            values.put("aluminio_status", model.get(i).getAlumninio_status());
            values.put("aluminio_value", model.get(i).getAlumninio_value());
            values.put("material_organico_status", model.get(i).getMaterial_organico_status());
            values.put("material_organico_value", model.get(i).getMaterial_organico_value());
            values.put("hidrogeno_status", model.get(i).getHidrogeno_status());
            values.put("hidrogeno_value", model.get(i).getHidrogeno_value());
            values.put("potencial_hidrogenionico_status", model.get(i).getPotencial_hidrogenionico_status());
            values.put("potencial_hidrogenionico_value", model.get(i).getPotencial_hidrogenionico_value());
            values.put("data_consulta", model.get(i).getData_consulta());
            values.put("estado", "Cargado");


            db.insert(ScriptDB.Consulta_SoloEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void CargaTarefa(ArrayList<Tarefa> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_tarea", String.valueOf(model.get(i).getId_tarea()));
            values.put("Id_usuario", model.get(i).getId_usuario());
            values.put("Id_producto", model.get(i).getId_producto());
            values.put("Id_empleado", model.get(i).getId_empleado());
            values.put("Id_tipo_producto", model.get(i).getId_tipo_producto());
            values.put("Id_maquinaria", model.get(i).getId_maquinaria());
            values.put("Id_tipo_tarea", model.get(i).getId_tipo_tarea());
            values.put("Id_sector", model.get(i).getId_sector());
            values.put("Nombre", model.get(i).getNombre_Tarea());
            values.put("Descripcion", model.get(i).getDescripcion());
            values.put("Fecha_trabajo", model.get(i).getFecha_trabajo());
            values.put("horas_trabajadas", model.get(i).getHoras_trabajadas());
            values.put("hectareas_trabajadas", model.get(i).getHectareas_trabajadas());
            values.put("cantidad_producto", model.get(i).getCantidad_producto());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.TareaEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void CargaAlertaPraga(ArrayList<AlertaPraga> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_sector", String.valueOf(model.get(i).getId_sector()));
            values.put("Id_plaga", String.valueOf(model.get(i).getId_plaga()));
            values.put("Nombre", model.get(i).getN_AlertaPlaga());
            values.put("Fecha_registro", model.get(i).getFecha_registro());
            values.put("Descripcion", model.get(i).getDescripcion());
            values.put("Status", model.get(i).getStatus());
            values.put("Id_alerta_plaga", String.valueOf(model.get(i).getId_alerta_plaga()));
            values.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("estado", "Cargado");

            db.insert(ScriptDB.Alerta_plagaEntry.TABLE_NAME, null, values);

        }

        db.close();

    }




    public void CargaNegociacoes(ArrayList<Negociacoe> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_negociacoes", String.valueOf(model.get(i).getId_negociacoes()));
            values.put("usuario", model.get(i).getUsuario());
            values.put("pim", model.get(i).getPim());
            values.put("imei", model.get(i).getImei());
            values.put("latitude", model.get(i).getLatitude());
            values.put("longitude", model.get(i).getLongitude());
            values.put("versao", model.get(i).getVersao());
            values.put("cpf", model.get(i).getCpf());
            values.put("nome", model.get(i).getNome());
            values.put("tipo_local", model.get(i).getTipo_local());
            values.put("local", model.get(i).getLocal());
            values.put("produto", model.get(i).getProduto());
            values.put("taxa", model.get(i).getTaxa());
            values.put("valor_negociado", model.get(i).getValor_negociado());
            values.put("data_pagamento", model.get(i).getData_pagamento());
            values.put("data_cadastro", model.get(i).getData_cadastro());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.NegociacoesEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaVisita(ArrayList<Visita> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_visitas", String.valueOf(model.get(i).getId_visita()));
            values.put("usuario", String.valueOf(model.get(i).getUsuario()));
            values.put("latitude", model.get(i).getLatitude());
            values.put("longitude", model.get(i).getLongitude());
            values.put("pim", model.get(i).getPim());
            values.put("imei", model.get(i).getImei());
            values.put("versao", model.get(i).getVersao());
            values.put("cliente", model.get(i).getCliente());
            values.put("motivo", model.get(i).getMotivo());
            values.put("data_agenda", model.get(i).getData_agenda());
            values.put("data_visita", model.get(i).getData_visita());
            values.put("resultado", model.get(i).getResultado());
            values.put("deslocamento", model.get(i).getDeslocamento());
            values.put("situacao", model.get(i).getSituacao());
            values.put("obs", model.get(i).getObs());
            values.put("cadastrante", model.get(i).getCadastrante());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.VisitasEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaPremios(ArrayList<Premio> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_premio", String.valueOf(model.get(i).getId_premio()));
            values.put("empleado", String.valueOf(model.get(i).getEmpleado()));
            values.put("premio", model.get(i).getPremio());
            values.put("ano", model.get(i).getAno());
            values.put("mes", model.get(i).getMes());
            values.put("mes_descricao", model.get(i).getMes_descricao());
            values.put("data_atualizacao", model.get(i).getData_atualizacao());
            values.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("estado", "Cargado");

            db.insert(ScriptDB.PremiosEntry.TABLE_NAME, null, values);

        }

        db.close();

    }



    public void CargaSectoresCoordendas(ArrayList<Coordenadas> model){

        db = helperDB.getWritableDatabase();

        String nomesctor = "";
        ArrayList<String> latitude_strings;
        ArrayList<String> longitud_strings;

        for (int i = 0; i < model.size(); i++) {

            if(!(model.get(i).getSetor().getNombre().equals(nomesctor))){

                nomesctor = model.get(i).getSetor().getNombre();

                ContentValues values1 = new ContentValues();
                values1.put("Id_sector", String.valueOf(model.get(i).getSetor().getId_sector()));
                values1.put("Id_usuario", String.valueOf(model.get(i).getSetor().getId_usuario()));
                values1.put("Id_cultivo", String.valueOf(model.get(i).getSetor().getId_cultivo()));
                values1.put("Status", model.get(i).getSetor().getStatus());
                values1.put("Nombre", model.get(i).getSetor().getNombre());
                values1.put("Hectareas", model.get(i).getSetor().getHectares());
                values1.put("estado", "Cargado");
                db.insert(ScriptDB.SectorEntry.TABLE_NAME, null, values1);

                for (int j = 0; j < model.size(); j++) {

                    if (model.get(j).getSetor().getNombre().equals(nomesctor)) {

                        ContentValues values2 = new ContentValues();
                        values2.put("id_coordenada", String.valueOf(model.get(j).getId_coordenada()));
                        values2.put("latitude",  model.get(j).getLatitude());
                        values2.put("longitude", model.get(j).getLongitude());
                        values2.put("id_sector", model.get(j).getSetor().getId_sector());
                        values1.put("estado", "Cargado");
                        db.insert(ScriptDB.CoordenadasEntry.TABLE_NAME, null, values2);

                    }
                }

            }

        }




        db.close();

    }



    public void CargaPontoInteresse(ArrayList<PontoInteresse> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_mapear", String.valueOf(model.get(i).getId_mapear()));
            values.put("usuario", model.get(i).getUsuario());
            values.put("pim", model.get(i).getPim());
            values.put("imei", model.get(i).getImei());
            values.put("latitude", model.get(i).getLatitude());
            values.put("longitude", model.get(i).getLongitude());
            values.put("versao", model.get(i).getVersao());
            values.put("endereco", model.get(i).getEndereco());
            values.put("tipo", model.get(i).getTipo());
            values.put("obs", model.get(i).getObs());
            values.put("data_cadastro", model.get(i).getData_cadastro());
            values.put("estado", "Cargado");


            db.insert(ScriptDB.MapearEntry.TABLE_NAME, null, values);

        }

        db.close();

    }





    public void CargaDespesa(ArrayList<Despesa> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("usuario", String.valueOf(model.get(i).getUsuario()));
            values.put("pim", model.get(i).getPim());
            values.put("imei", model.get(i).getImei());
            values.put("latitude", model.get(i).getLatitude());
            values.put("longitude", model.get(i).getLongitude());
            values.put("versao", model.get(i).getVersao());
            values.put("valor", model.get(i).getValor());
            values.put("data_despesa", model.get(i).getData_despesa());
            values.put("id_despesa", model.get(i).getId_despesa());
            values.put("tipo_despesa_tempo", String.valueOf(model.get(i).getTipo_despesa_tempo()));
            values.put("tipo_despesa", String.valueOf(model.get(i).getTipo_despesa()));
            values.put("estado", "Cargado");

            db.insert(ScriptDB.DespesasEntry.TABLE_NAME, null, values);

        }

        db.close();

    }

    public void CargaProduto(ArrayList<Produto> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_producto", String.valueOf(model.get(i).getId_producto()));
            values.put("Id_tipo_producto", String.valueOf(model.get(i).getId_tipo_producto()));
            values.put("Nombre", model.get(i).getNombre());
            values.put("Fecha_registro", model.get(i).getFecha_registro());
            values.put("Fecha_expiracion", model.get(i).getFecha_expiracion());
            values.put("Funcion", model.get(i).getFuncion());
            values.put("Descripcion", model.get(i).getDescipcion());
            values.put("Composicion", model.get(i).getComposicion());
            values.put("Objeto", model.get(i).getObjeto());
            values.put("Imagen", model.get(i).getImagen());
            values.put("lote", model.get(i).getLote());
            values.put("custo", model.get(i).getCusto());
            values.put("id_usuario", model.get(i).getId_usuario());
            values.put("kilos",  model.get(i).getKilos());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.ProductoEntry.TABLE_NAME, null, values);

        }

        db.close();

    }

    public void CargaEmpregado(ArrayList<Empregado> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_empleado", String.valueOf(model.get(i).getId_empleado()));
            values.put("Id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("Nombre", model.get(i).getNombre());
            values.put("Fecha_contratacion", model.get(i).getFecha_contratacion());
            values.put("Edad", model.get(i).getEdad());
            values.put("Rol", model.get(i).getRol());
            values.put("contacto", model.get(i).getContacto());
            values.put("Photo", 1);
            values.put("salario", model.get(i).getSalario());
            values.put("fin_de_contrato", model.get(i).getFin_de_contrato());
            values.put("tipo_contrato", model.get(i).getTipo_contrato());
            values.put("estado", "Cargado");


            db.insert(ScriptDB.EmpleadoEntry.TABLE_NAME, null, values);

        }

        db.close();

    }




    public void CargaMaquinaria(ArrayList<Maquinaria> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("Id_maquinaria", String.valueOf(model.get(i).getId_maquinaria()));
            values.put("Id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("Nombre", String.valueOf(model.get(i).getNombre()));
            values.put("Registro", model.get(i).getRegistro());
            values.put("Fecha_Adquisicion", model.get(i).getFecha_Adquisicion());
            values.put("Precio", Integer.valueOf(model.get(i).getPrecio()));
            values.put("Tipo", model.get(i).getTipo());
            values.put("Descripcion", model.get(i).getDescripcion());
            values.put("Modelo", model.get(i).getModelo());
            values.put("costo_mantenimiento", Integer.valueOf(model.get(i).getCosto_mantenimiento()));
            values.put("vida_util_horas", Integer.valueOf(model.get(i).getVida_util_horas()));
            values.put("vida_util_ano", Integer.valueOf(model.get(i).getVida_util_ano()));
            values.put("potencia_maquinaria", Integer.valueOf(model.get(i).getPotencia_maquinaria()));
            values.put("tipo_adquisicion", model.get(i).getTipo_adquisicion());
            values.put("estado", "Cargado");

            db.insert(ScriptDB.MaquinariaEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void CargaCliente(ArrayList<Cliente> model){

        db = helperDB.getWritableDatabase();

        for(int i = 0; i < model.size(); i++){

            ContentValues values = new ContentValues();
            values.put("id_usuario", String.valueOf(model.get(i).getId_usuario()));
            values.put("nombre", model.get(i).getNombre());
            values.put("organizacion", model.get(i).getOrganizacion());
            values.put("numero", model.get(i).getNumero());
            values.put("direccion", model.get(i).getDireccion());
            values.put("area", model.get(i).getArea());
            values.put("Id_cliente", model.get(i).getId_cliente());
            values.put("cpf", model.get(i).getCpf());
            values.put("data_insercao", model.get(i).getData_insercao());
            values.put("estado", "Cargado");


            db.insert(ScriptDB.ClienteEntry.TABLE_NAME, null, values);

        }

        db.close();

    }


    public void ManutencaoColheita(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_id = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_cosecha),0) "
                            + FROM + ScriptDB.CosechaEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_id = cursor.getInt(0);
                    }

                    cursor.close();

                    Log.d("cosecha", obj.getString("cosecha"));
                    Log.d("id_usuario", obj.getString("id_usuario"));
                    Log.d("Id_cosecha", String.valueOf(codigo_id));
                    Log.d("Id_cosecha", String.valueOf(codigo_id+1));

                    ContentValues values = new ContentValues();

                    values.put("Nombre", obj.getString("cosecha"));
                    values.put("Id_cosecha", String.valueOf(codigo_id+1));
                    values.put("id_usuario", Integer.valueOf(obj.getString("id_usuario")));
                    values.put("estado", "Criado");


                    db.insert(ScriptDB.CosechaEntry.TABLE_NAME, null, values);
                    db.close();

                    break;

                case "D":

                    Log.d("id_usuario", String.valueOf(obj.getInt("Id_cosecha")));

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_cosecha"))};

                    db.delete(ScriptDB.CosechaEntry.TABLE_NAME," Id_cosecha=? ", args);
                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    values1.put("Nombre", obj.getString("cosecha"));
                    //values.put("Id_cosecha", obj.getString("nombre"));
                    //values1.put("id_usuario", Integer.valueOf(obj.getString("id_usuario")));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_cosecha"))};

                    db.update(ScriptDB.CosechaEntry.TABLE_NAME,values1," Id_cosecha=? ",args1);
                    db.close();

                    break;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void ManutencaoCultivos(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":


                        ArrayList<Safra> menumodel = new ArrayList<Safra>();
                    String safra_value = "";
                    String selectQuery1 = SELECT + " Id_cultivo "
                            + FROM + ScriptDB.CultivoEntry.TABLE_NAME
                            + WHERE + " Id_cultivo = " + String.valueOf(obj.getInt("sector")) + "";

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        safra_value = cursor.getString(0);
                    }
                    cursor.close();

                    if ( String.valueOf(obj.getInt("sector")).equals(safra_value)) {

                        ContentValues values = new ContentValues();
                        values.put("Id_cosecha", obj.getInt("cosecha"));
                        values.put("Inicio", obj.getString("inicio"));
                        values.put("Fim", obj.getString("fim"));

                        String[] args = new String[]{String.valueOf(obj.getInt("sector"))};

                        db.update(ScriptDB.CultivoEntry.TABLE_NAME, values, "Id_cultivo=?", args);


                        ContentValues values1 = new ContentValues();
                        values1.put("Id_cultivo", obj.getInt("sector"));

                        String[] args1 = new String[]{String.valueOf(obj.getInt("sector"))};

                        db.update(ScriptDB.SectorEntry.TABLE_NAME, values1, "Id_sector=?", args);
                        db.close();

                    } else {

                        ContentValues values2 = new ContentValues();
                        values2.put("Id_cultivo", obj.getString("sector"));
                        values2.put("Id_cosecha", obj.getString("cosecha"));
                        values2.put("Inicio", obj.getString("inicio"));
                        values2.put("Fim", obj.getString("fim"));
                        values2.put("estado", "Criado");

                        db.insert(ScriptDB.CultivoEntry.TABLE_NAME, null, values2);


                        ContentValues values3 = new ContentValues();
                        values3.put("Id_cultivo", obj.getInt("sector"));

                        String[] args2 = new String[]{String.valueOf(obj.getInt("sector"))};

                        db.update(ScriptDB.SectorEntry.TABLE_NAME, values3, "Id_sector=?", args2);
                        db.close();
                    }

                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoDespesas(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_id = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_despesa),0) "
                            + FROM + ScriptDB.DespesasEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_id = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("usuario", obj.getString("usuario"));
                    values.put("pim", obj.getString("pim"));
                    values.put("imei", obj.getString("imei"));
                    values.put("latitude", obj.getString("latitude"));
                    values.put("longitude", obj.getString("longitude"));
                    values.put("versao", obj.getString("versao"));
                    values.put("valor", obj.getString("valor"));
                    values.put("data_despesa", obj.getString("data_despesa"));
                    values.put("id_despesa", String.valueOf(codigo_id+1));
                    values.put("tipo_despesa_tempo", obj.getString("tipo_despesa_tempo"));
                    values.put("tipo_despesa", obj.getString("tipo_despesa"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.DespesasEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_despesa"))};
                    db.delete(ScriptDB.DespesasEntry.TABLE_NAME,"id_despesa=?", args);
                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    values1.put("usuario", obj.getString("usuario"));
                    values1.put("pim", obj.getString("pim"));
                    values1.put("imei", obj.getString("imei"));
                    values1.put("latitude", obj.getString("latitude"));
                    values1.put("longitude", obj.getString("longitude"));
                    values1.put("versao", obj.getString("versao"));
                    values1.put("valor", obj.getString("valor"));
                    values1.put("data_despesa", obj.getString("data_despesa"));
                    //values.put("id_despesa", obj.getString("id_despesa"));
                    values1.put("tipo_despesa_tempo", obj.getString("tipo_despesa_tempo"));
                    values1.put("tipo_despesa", obj.getString("tipo_despesa"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_despesa"))};

                    db.update(ScriptDB.DespesasEntry.TABLE_NAME,values1,"id_despesa=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void ManutencaoEmpleados(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_id = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_empleado),0) "
                            + FROM + ScriptDB.EmpleadoEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_id = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_empleado", String.valueOf(codigo_id+1));
                    values.put("Id_usuario", obj.getString("id_usuario"));
                    values.put("Nombre", obj.getString("nombre"));
                    values.put("Fecha_contratacion", obj.getString("fecha_contratacion"));
                    values.put("Edad", obj.getString("edad"));
                    values.put("Rol", obj.getString("rol"));
                    values.put("contacto", obj.getString("contacto"));
                    values.put("Photo", obj.getString("Photo"));
                    values.put("salario", obj.getString("salario"));
                    values.put("fin_de_contrato", obj.getString("fin_de_contrato"));
                    values.put("tipo_contrato", obj.getString("tipo_contrato"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.EmpleadoEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_empleado"))};
                    db.delete(ScriptDB.EmpleadoEntry.TABLE_NAME," Id_empleado=?", args);
                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    //values.put("Id_empleado", obj.getString("Id_empleado"));
                    values1.put("Id_usuario", obj.getString("id_usuario"));
                    values1.put("Nombre", obj.getString("nombre"));
                    values1.put("Fecha_contratacion", obj.getString("fecha_contratacion"));
                    values1.put("Edad", obj.getString("edad"));
                    values1.put("Rol", obj.getString("rol"));
                    values1.put("contacto", obj.getString("contacto"));
                    values1.put("Photo", obj.getString("Photo"));
                    values1.put("salario", obj.getString("salario"));
                    values1.put("fin_de_contrato", obj.getString("fin_de_contrato"));
                    values1.put("tipo_contrato", obj.getString("tipo_contrato"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_empleado"))};

                    db.update(ScriptDB.EmpleadoEntry.TABLE_NAME,values1,"Id_empleado=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void ManutencaoGado(JSONObject obj) {
        db = helperDB.getWritableDatabase();
        int cod_adquisicao_novo = 0;
        String tipo_adquisicao_novo = "";
        int codigo_adquisicao_novo  = 0;

        try {

            switch (obj.getString("acao")) {
                case "I":

 /*                   ContentValues values = new ContentValues();
                    //values.put("Id_empleado", obj.getString("Id_empleado"));
                    //values.put("id_animal", obj.getString("id_animal"));
                    values.put("id_lote_gado", obj.getString("id_lote_gado"));
                    values.put("id_usuario", obj.getString("id_usuario"));
                    values.put("nombre", obj.getString("nombre"));
                    values.put("peso_inicial", obj.getString("peso_inicial"));
                    values.put("cod_adquisicao", obj.getString("cod_adquisicao"));
                    values.put("tipo_adquisicao", obj.getString("tipo_adquisicao"));


                    db.insert(ScriptDB.AnimalEntry.TABLE_NAME, null, values);*/



                    if ( obj.getString("tipo_adquisicao").equals("Parto")) {

                        int codigo_usuario2 = 0;

                        String selectQuery2 = SELECT + " ifnull(max(id_parto),0) "
                                + FROM + ScriptDB.PartoEntry.TABLE_NAME;

                        Cursor cursor2 = db.rawQuery(selectQuery2, null);

                        if (cursor2.moveToFirst()) {
                            codigo_usuario2 = cursor2.getInt(0);
                        }

                        cursor2.close();

                        ContentValues values1 = new ContentValues();
                        values1.put("id_parto", String.valueOf(codigo_usuario2+1));
                        values1.put("fecha", obj.getString("fecha"));
                        values1.put("id_animal", obj.getString("id_animal_parto"));
                        values1.put("estado", "Criado");

                        db.insert(ScriptDB.PartoEntry.TABLE_NAME, null, values1);

                        String selectQuery1 = SELECT + " max(id_parto) "
                                + FROM + ScriptDB.PartoEntry.TABLE_NAME
                                + WHERE + " fecha = '" + String.valueOf(obj.getString("fecha")) + "' " + AND
                                + " id_animal = " + String.valueOf(obj.getString("id_animal_parto")) + " ";

                        Cursor cursor = db.rawQuery(selectQuery1, null);

                        if (cursor.moveToFirst()) {
                                cod_adquisicao_novo = cursor.getInt(0);
                        }

                        cursor.close();


                        int codigo_usuario3 = 0;

                        String selectQuery3 = SELECT + " ifnull(max(id_animal),0) "
                                + FROM + ScriptDB.AnimalEntry.TABLE_NAME;

                        Cursor cursor3 = db.rawQuery(selectQuery3, null);

                        if (cursor3.moveToFirst()) {
                            codigo_usuario3 = cursor3.getInt(0);
                        }

                        cursor2.close();

                        ContentValues values2 = new ContentValues();

                        values2.put("id_animal", String.valueOf(codigo_usuario3+1));
                        values2.put("id_lote_gado", obj.getString("id_lote_gado"));
                        values2.put("id_usuario", obj.getString("id_usuario"));
                        values2.put("nombre", obj.getString("nombre"));
                        values2.put("peso_inicial", obj.getString("peso_inicial"));
                        values2.put("cod_adquisicao", String.valueOf(cod_adquisicao_novo));
                        values2.put("tipo_adquisicao", obj.getString("tipo_adquisicao"));
                        values2.put("estado", "Criado");

                        db.insert(ScriptDB.AnimalEntry.TABLE_NAME, null, values2);
                        db.close();
                    }


                    if ( obj.getString("tipo_adquisicao").equals("Compra")) {


                        int codigo_usuario4 = 0;

                        String selectQuery4 = SELECT + " ifnull(max(id_compra),0) "
                                + FROM + ScriptDB.CompraEntry.TABLE_NAME;

                        Cursor cursor4 = db.rawQuery(selectQuery4, null);

                        if (cursor4.moveToFirst()) {
                            codigo_usuario4 = cursor4.getInt(0);
                        }

                        cursor4.close();

                        ContentValues values1 = new ContentValues();
                        values1.put("id_compra", String.valueOf(codigo_usuario4+1));
                        values1.put("fecha", obj.getString("fecha"));
                        values1.put("precio", obj.getString("precio"));
                        values1.put("estado", "Criado");

                        Log.d("fecha compra", obj.getString("fecha"));
                        Log.d("precio compra", obj.getString("precio"));

                        db.insert(ScriptDB.CompraEntry.TABLE_NAME, null, values1);

                        String selectQuery1 = SELECT + " max(id_compra) "
                                + FROM + ScriptDB.CompraEntry.TABLE_NAME
                                + WHERE + " fecha = '" + String.valueOf(obj.getString("fecha")) + "' " + AND
                                + " precio = " + String.valueOf(obj.getString("precio")) + " ";

                        Cursor cursor = db.rawQuery(selectQuery1, null);

                        if (cursor.moveToFirst()) {
                                cod_adquisicao_novo = cursor.getInt(0);
                        }


                        String selectQuery2 = SELECT + " id_compra, fecha, precio "
                                + FROM + ScriptDB.CompraEntry.TABLE_NAME;

                        Cursor cursor2 = db.rawQuery(selectQuery2, null);

                        Log.d("CompraEntry count ",String.valueOf(cursor2.getCount()));

                        if (cursor2.moveToFirst()) {
                            do {
                                Log.d("id_compra ",String.valueOf(cursor2.getInt(0)));
                                Log.d("fecha ",cursor2.getString(1));
                                Log.d("precio ",String.valueOf(cursor2.getInt(2)));
                            } while (cursor2.moveToNext());
                        }
                        cursor2.close();
                        cursor.close();


                        int codigo_usuario = 0;

                        String selectQuery3 = SELECT + " ifnull(max(id_animal),0) "
                                + FROM + ScriptDB.AnimalEntry.TABLE_NAME;

                        Cursor cursor3 = db.rawQuery(selectQuery3, null);

                        if (cursor3.moveToFirst()) {
                            codigo_usuario = cursor3.getInt(0);
                        }

                        cursor3.close();

                        Log.d("adquisicao_novo compra", String.valueOf(cod_adquisicao_novo));

                        ContentValues values2 = new ContentValues();

                        values2.put("id_animal", String.valueOf(codigo_usuario+1));
                        values2.put("id_lote_gado", obj.getString("id_lote_gado"));
                        values2.put("id_usuario", obj.getString("id_usuario"));
                        values2.put("nombre", obj.getString("nombre"));
                        values2.put("peso_inicial", obj.getString("peso_inicial"));
                        values2.put("cod_adquisicao", String.valueOf(cod_adquisicao_novo));
                        values2.put("tipo_adquisicao", obj.getString("tipo_adquisicao"));
                        values2.put("estado", "Criado");

                        db.insert(ScriptDB.AnimalEntry.TABLE_NAME, null, values2);
                        db.close();
                    }

                    break;

                case "D":



                    String selectQuery1 = SELECT + " tipo_adquisicao "
                            + FROM + ScriptDB.AnimalEntry.TABLE_NAME
                            + WHERE + " id_animal = " + String.valueOf(obj.getString("id_animal")) + " ";

                    Cursor cursor1 = db.rawQuery(selectQuery1, null);

                    if (cursor1.moveToFirst()) {
                        tipo_adquisicao_novo = cursor1.getString(0);
                    }

                    cursor1.close();

                    String selectQuery2 = SELECT + " cod_adquisicao "
                            + FROM + ScriptDB.AnimalEntry.TABLE_NAME
                            + WHERE + " id_animal = " + String.valueOf(obj.getString("id_animal")) + " ";

                    Cursor cursor2 = db.rawQuery(selectQuery2, null);

                    if (cursor2.moveToFirst()) {
                        codigo_adquisicao_novo = cursor2.getInt(0);
                    }

                    cursor2.close();

                    if ( obj.getString("tipo_adquisicao").equals("Parto")) {

                        String[] args1 = new String[]{String.valueOf(obj.getInt("id_animal"))};
                        String[] args2 = new String[]{String.valueOf(codigo_adquisicao_novo)};

                        db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.PartoEntry.TABLE_NAME," id_parto=?", args2);
                        db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_animal=?", args1);

                    }


                    if ( obj.getString("tipo_adquisicao").equals("Compra")) {

                        String[] args1 = new String[]{String.valueOf(obj.getInt("id_animal"))};
                        String[] args2 = new String[]{String.valueOf(codigo_adquisicao_novo)};

                        db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.CompraEntry.TABLE_NAME," id_compra=?", args2);
                        db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_animal=?", args1);
                        db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_animal=?", args1);

                    }


                    db.close();
                    break;

                case "E":

                    ContentValues values1 = new ContentValues();
                    values1.put("id_lote_gado", obj.getString("id_lote_gado"));
                    values1.put("id_usuario", obj.getString("id_usuario"));
                    values1.put("nombre", obj.getString("nombre"));
                    values1.put("peso_inicial", obj.getString("peso_inicial"));
                    values1.put("cod_adquisicao", String.valueOf(cod_adquisicao_novo));
                    values1.put("tipo_adquisicao", obj.getString("tipo_adquisicao"));


                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_animal"))};

                    db.update(ScriptDB.AnimalEntry.TABLE_NAME,values1,"id_animal=?",args1);
                    db.close();
                    break;
                case "X":

                    db.delete(ScriptDB.AnimalEntry.TABLE_NAME, null, null);
                    db.delete(ScriptDB.CompraEntry.TABLE_NAME, null, null);
                    db.delete(ScriptDB.PartoEntry.TABLE_NAME, null, null);
                    db.close();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void ManutencaoHistorialConsumo(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":


                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_historial_consumo),0) "
                            + FROM + ScriptDB.Historial_ConsumoEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();


                    ContentValues values = new ContentValues();
                    values.put("id_historial_consumo", String.valueOf(codigo_usuario+1));
                    values.put("id_animal", obj.getString("id_animal"));
                    values.put("id_producto", obj.getString("id_producto"));
                    values.put("cantidad_consumida", obj.getString("cantidad_consumida"));
                    values.put("fecha_consumo", obj.getString("fecha_consumo"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Historial_ConsumoEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_historial_consumo"))};
                    db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_historial_consumo=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_historial_consumo", obj.getString("id_historial_consumo"));
                    values1.put("id_animal", obj.getString("id_animal"));
                    values1.put("id_producto", obj.getString("id_producto"));
                    values1.put("cantidad_consumida", obj.getString("cantidad_consumida"));
                    values1.put("fecha_consumo", obj.getString("fecha_consumo"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_historial_consumo"))};

                    db.update(ScriptDB.EmpleadoEntry.TABLE_NAME,values1,"id_historial_consumo=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoHistorialEngorde(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_historial_engorde),0) "
                            + FROM + ScriptDB.Historial_EngordeEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("id_historial_engorde", String.valueOf(codigo_usuario+1));
                    values.put("id_animal", obj.getString("id_animal"));
                    values.put("peso", obj.getString("peso"));
                    values.put("fecha_medicion", obj.getString("fecha_medicion"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Historial_EngordeEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_historial_engorde"))};
                    db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_historial_engorde=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_historial_engorde", obj.getString("id_historial_engorde"));
                    values1.put("id_animal", obj.getString("id_animal"));
                    values1.put("id_producto", obj.getString("id_producto"));
                    values1.put("fecha_medicion", obj.getString("fecha_medicion"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_historial_engorde"))};
                    db.update(ScriptDB.Historial_EngordeEntry.TABLE_NAME,values1,"id_historial_engorde=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoHistorialLeche(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_historial_leche),0) "
                            + FROM + ScriptDB.Historial_LecheEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();


                    ContentValues values = new ContentValues();
                    values.put("id_historial_leche", String.valueOf(codigo_usuario+1));
                    values.put("id_animal", obj.getString("id_animal"));
                    values.put("cantidad", obj.getString("cantidad"));
                    values.put("fecha_obtencao", obj.getString("fecha_obtencao"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Historial_LecheEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_historial_leche"))};
                    db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_historial_leche=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_historial_engorde", obj.getString("id_historial_engorde"));
                    values1.put("id_animal", obj.getString("id_animal"));
                    values1.put("cantidad", obj.getString("cantidad"));
                    values1.put("fecha_obtencao", obj.getString("fecha_obtencao"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_historial_leche"))};
                    db.update(ScriptDB.Historial_LecheEntry.TABLE_NAME,values1,"id_historial_leche=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void ManutencaoLotegado(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_lote_gado),0) "
                            + FROM + ScriptDB.Lote_gadoEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("id_lote_gado", String.valueOf(codigo_usuario+1));
                    values.put("id_usuario", obj.getString("id_usuario"));
                    values.put("nombre", obj.getString("nombre"));
                    values.put("descripcao", obj.getString("descripcao"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Lote_gadoEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_lote_gado"))};
                    db.delete(ScriptDB.Lote_gadoEntry.TABLE_NAME," id_lote_gado=?", args);
                    db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_lote_gado=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_historial_engorde", obj.getString("id_historial_engorde"));
                    values1.put("id_usuario", obj.getString("id_usuario"));
                    values1.put("nombre", obj.getString("nombre"));
                    values1.put("descripcao", obj.getString("descripcao"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_lote_gado"))};
                    db.update(ScriptDB.Lote_gadoEntry.TABLE_NAME,values1,"id_lote_gado=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoMaquinarias(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_maquinaria),0) "
                            + FROM + ScriptDB.MaquinariaEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_maquinaria", String.valueOf(codigo_usuario+1));
                    values.put("Id_usuario", obj.getString("id_usuario"));
                    values.put("Nombre", obj.getString("nombre"));
                    values.put("Registro", obj.getString("registro"));
                    values.put("Fecha_Adquisicion", obj.getString("fecha_adquisicion"));
                    values.put("Precio", obj.getString("precio"));
                    values.put("Tipo", obj.getString("tipo"));
                    values.put("Descripcion", obj.getString("descripcion"));
                    values.put("Modelo", obj.getString("modelo"));
                    values.put("costo_mantenimiento", obj.getString("costo_mantenimiento"));
                    values.put("vida_util_horas", obj.getString("vida_util_horas"));
                    values.put("vida_util_ano", obj.getString("vida_util_ano"));
                    values.put("potencia_maquinaria", obj.getString("potencia_maquinaria"));
                    values.put("tipo_adquisicion", obj.getString("tipo_adquisicion"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.MaquinariaEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_maquinaria"))};
                    db.delete(ScriptDB.MaquinariaEntry.TABLE_NAME," Id_maquinaria=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("Id_maquinaria", obj.getString("Id_maquinaria"));
                    values1.put("Id_usuario", obj.getString("id_usuario"));
                    values1.put("Nombre", obj.getString("nombre"));
                    values1.put("Registro", obj.getString("registro"));
                    values1.put("Fecha_Adquisicion", obj.getString("fecha_adquisicion"));
                    values1.put("Precio", obj.getString("precio"));
                    values1.put("Tipo", obj.getString("tipo"));
                    values1.put("Descripcion", obj.getString("descripcion"));
                    values1.put("Modelo", obj.getString("modelo"));
                    values1.put("costo_mantenimiento", obj.getString("costo_mantenimiento"));
                    values1.put("vida_util_horas", obj.getString("vida_util_horas"));
                    values1.put("vida_util_ano", obj.getString("vida_util_ano"));
                    values1.put("potencia_maquinaria", obj.getString("potencia_maquinaria"));
                    values1.put("tipo_adquisicion", obj.getString("tipo_adquisicion"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_maquinaria"))};
                    db.update(ScriptDB.MaquinariaEntry.TABLE_NAME,values1,"Id_maquinaria=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void ManutencaoNegociacoes(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":


                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_negociacoes),0) "
                            + FROM + ScriptDB.NegociacoesEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("id_negociacoes", String.valueOf(codigo_usuario+1));
                    values.put("usuario", obj.getString("usuario"));
                    values.put("pim", obj.getString("pim"));
                    values.put("imei", obj.getString("imei"));
                    values.put("latitude", obj.getString("latitude"));
                    values.put("longitude", obj.getString("longitude"));
                    values.put("versao", obj.getString("versao"));
                    values.put("cpf", obj.getString("cpf"));
                    values.put("nome", obj.getString("nome"));
                    values.put("tipo_local", obj.getString("tipo_local"));
                    values.put("local", obj.getString("local"));
                    values.put("produto", obj.getString("producto"));
                    values.put("taxa", obj.getString("taxa"));
                    values.put("valor_negociado", obj.getString("valor_negociado"));
                    values.put("data_pagamento", obj.getString("data_pagamento"));
                    values.put("data_cadastro", obj.getString("data_cadastro"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.NegociacoesEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_negociacoes"))};
                    db.delete(ScriptDB.NegociacoesEntry.TABLE_NAME," id_negociacoes=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_negociacoes", obj.getString("id_negociacoes"));
                    values1.put("usuario", obj.getString("usuario"));
                    values1.put("pim", obj.getString("pim"));
                    values1.put("imei", obj.getString("imei"));
                    values1.put("latitude", obj.getString("latitude"));
                    values1.put("longitude", obj.getString("longitude"));
                    values1.put("versao", obj.getString("versao"));
                    values1.put("cpf", obj.getString("cpf"));
                    values1.put("nome", obj.getString("nome"));
                    values1.put("tipo_local", obj.getString("tipo_local"));
                    values1.put("local", obj.getString("local"));
                    values1.put("produto", obj.getString("producto"));
                    values1.put("taxa", obj.getString("taxa"));
                    values1.put("valor_negociado", obj.getString("valor_negociado"));
                    values1.put("data_pagamento", obj.getString("data_pagamento"));
                    values1.put("data_cadastro", obj.getString("data_cadastro"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_negociacoes"))};
                    db.update(ScriptDB.NegociacoesEntry.TABLE_NAME,values1,"id_negociacoes=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoPraga(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":


                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_plaga),0) "
                            + FROM + ScriptDB.PlagaEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_plaga", String.valueOf(codigo_usuario+1));
                    values.put("Nombre", obj.getString("usuario"));
                    values.put("Caracteristicas", obj.getString("pim"));
                    values.put("Sintomas", obj.getString("imei"));
                    values.put("Tratamiento", obj.getString("latitude"));
                    values.put("Clase", obj.getString("longitude"));
                    values.put("Descripcion", obj.getString("versao"));
                    values.put("Prevencion", obj.getString("cpf"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.PlagaEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_plaga"))};
                    db.delete(ScriptDB.PlagaEntry.TABLE_NAME," Id_plaga=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    values1.put("Nombre", obj.getString("usuario"));
                    values1.put("Caracteristicas", obj.getString("pim"));
                    values1.put("Sintomas", obj.getString("imei"));
                    values1.put("Tratamiento", obj.getString("latitude"));
                    values1.put("Clase", obj.getString("longitude"));
                    values1.put("Descripcion", obj.getString("versao"));
                    values1.put("Prevencion", obj.getString("cpf"));
                    values1.put("estado", "Criado");

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_plaga"))};
                    db.update(ScriptDB.PlagaEntry.TABLE_NAME,values1,"Id_plaga=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void ManutencaoPontoInteresse(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_mapear),0) "
                            + FROM + ScriptDB.MapearEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate1 = df1.format(c.getTime()).replace(" ","");

                    ContentValues values = new ContentValues();
                    values.put("id_mapear", String.valueOf(codigo_usuario+1));
                    values.put("usuario", obj.getString("usuario"));
                    values.put("pim", obj.getString("pim"));
                    values.put("imei", obj.getString("imei"));
                    values.put("latitude", obj.getString("latitude"));
                    values.put("longitude", obj.getString("longitude"));
                    values.put("versao", obj.getString("versao"));
                    values.put("endereco", obj.getString("endereco"));
                    values.put("tipo", obj.getString("tipo"));
                    values.put("obs", obj.getString("obs"));
                    values.put("data_cadastro", formattedDate1);
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.MapearEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_mapear"))};
                    db.delete(ScriptDB.MapearEntry.TABLE_NAME," id_mapear=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_mapear", obj.getString("id_mapear"));
                    values1.put("usuario", obj.getString("usuario"));
                    values1.put("pim", obj.getString("pim"));
                    values1.put("imei", obj.getString("imei"));
                    values1.put("latitude", obj.getString("latitude"));
                    values1.put("longitude", obj.getString("longitude"));
                    values1.put("versao", obj.getString("versao"));
                    values1.put("endereco", obj.getString("endereco"));
                    values1.put("tipo", obj.getString("tipo"));
                    values1.put("obs", obj.getString("obs"));
                    values1.put("data_cadastro", obj.getString("data_cadastro"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_mapear"))};
                    db.update(ScriptDB.MapearEntry.TABLE_NAME,values1,"id_mapear=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void ManutencaoPremios(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_premio),0) "
                            + FROM + ScriptDB.PremiosEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

/*                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df1 = new SimpleDateFormat("MM");
                    String formattedDate1 = df1.format(obj.getString("data_atualizacao")).replace(" ","");
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
                    String formattedDate2 = df2.format(obj.getString("data_atualizacao")).replace(" ","");*/

                    String ano = obj.getString("data_atualizacao").substring(0,4);
                    String mes = obj.getString("data_atualizacao").substring(5,7);

                    Log.d("ano",ano);
                    Log.d("mes",mes);

                    ContentValues values = new ContentValues();
                    values.put("id_premio", String.valueOf(codigo_usuario+1));
                    values.put("empleado", obj.getString("empleado"));
                    values.put("premio", obj.getString("premio"));
                    values.put("ano", ano);
                    values.put("mes", mes);
                    values.put("mes_descricao", obj.getString("mes_descricao"));
                    values.put("data_atualizacao", obj.getString("data_atualizacao"));
                    values.put("id_usuario", obj.getString("id_usuario"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.PremiosEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_premio"))};
                    db.delete(ScriptDB.PremiosEntry.TABLE_NAME," id_premio=?", args);
                    db.close();
                    break;

                case "E":

                    String ano2 = obj.getString("data_atualizacao").substring(0,4);
                    String mes2 = obj.getString("data_atualizacao").substring(5,7);

                    Log.d("ano",ano2);
                    Log.d("mes",mes2);

                    ContentValues values1 = new ContentValues();
                    //values.put("id_premio", obj.getString("id_premio"));
                    values1.put("empleado", obj.getString("empleado"));
                    values1.put("premio", obj.getString("premio"));
                    values1.put("ano", ano2);
                    values1.put("mes", mes2);
                    values1.put("mes_descricao", obj.getString("mes_descricao"));
                    values1.put("data_atualizacao", obj.getString("data_atualizacao"));
                    values1.put("id_usuario", obj.getString("id_usuario"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_premio"))};
                    db.update(ScriptDB.PremiosEntry.TABLE_NAME,values1,"id_premio=?",args1);
                    db.close();

                    break;

                case "X":


                    db.delete(ScriptDB.PremiosEntry.TABLE_NAME, null ,null);
                    db.close();

                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoProductos(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_producto),0) "
                            + FROM + ScriptDB.ProductoEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_producto", String.valueOf(codigo_usuario+1));
                    values.put("Id_tipo_producto", obj.getString("Id_tipo_producto"));
                    values.put("Nombre", obj.getString("Nombre"));

                    //acambia a actual
                    values.put("Fecha_registro", obj.getString("Fecha_expiracion"));

                    values.put("Fecha_expiracion", obj.getString("Fecha_expiracion"));
                    values.put("Funcion", obj.getString("Funcion"));
                    values.put("Descripcion", obj.getString("Descipcion"));
                    values.put("Composicion", obj.getString("Composicion"));
                    values.put("Objeto", obj.getString("Objeto"));
                    values.put("Imagen", String.valueOf(1));
                    values.put("lote", obj.getString("lote"));
                    values.put("custo", obj.getString("custo"));
                    values.put("id_usuario", obj.getString("id_usuario"));
                    values.put("kilos", obj.getString("kilos"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.ProductoEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_producto"))};
                    db.delete(ScriptDB.ProductoEntry.TABLE_NAME," Id_producto=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_premio", obj.getString("id_premio"));
                    values1.put("Id_tipo_producto", obj.getString("Id_tipo_producto"));
                    values1.put("Nombre", obj.getString("Nombre"));

                    //acambia a actual
                    values1.put("Fecha_registro", obj.getString("Fecha_expiracion"));

                    values1.put("Fecha_expiracion", obj.getString("Fecha_expiracion"));
                    values1.put("Funcion", obj.getString("Funcion"));
                    values1.put("Descripcion", obj.getString("Descipcion"));
                    values1.put("Composicion", obj.getString("Composicion"));
                    values1.put("Objeto", obj.getString("Objeto"));
                    values1.put("Imagen", String.valueOf(1));
                    values1.put("lote", obj.getString("lote"));
                    values1.put("custo", obj.getString("custo"));
                    values1.put("id_usuario", obj.getString("id_usuario"));
                    values1.put("kilos", obj.getString("kilos"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_producto"))};
                    db.update(ScriptDB.ProductoEntry.TABLE_NAME,values1,"Id_producto=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }








    public void ManutencaoRegistro(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    ContentValues values = new ContentValues();
                    //values.put("Id_producto", obj.getString("Id_producto"));
                    values.put("nome", encripta(obj.getString("nome")));
                    values.put("login", encripta(obj.getString("login")));
                    values.put("senha", encripta(obj.getString("senha")));
                    values.put("email", encripta(obj.getString("email")));
                    values.put("status", encripta("ativo"));
                    values.put("perfil", encripta(String.valueOf("2")));


                    db.insert(ScriptDB.UsuariosEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }







    public void ManutencaoSolo(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_consulta_solo),0) "
                            + FROM + ScriptDB.Consulta_SoloEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("id_consulta_solo", String.valueOf(codigo_usuario+1));
                    values.put("id_usuario", obj.getString("id_usuario"));
                    values.put("id_sector", obj.getString("id_sector"));
                    values.put("fosforo_status", obj.getString("fosforo_status"));
                    values.put("fosforo_value", obj.getString("fosforo_value"));
                    values.put("potasio_status", obj.getString("potasio_status"));
                    values.put("potasio_value", obj.getString("potasio_value"));
                    values.put("calcio_status", obj.getString("calcio_status"));
                    values.put("calcio_value", obj.getString("calcio_value"));
                    values.put("magnesio_status", obj.getString("magnesio_status"));
                    values.put("magnesio_value", obj.getString("magnesio_value"));
                    values.put("aluminio_status", obj.getString("alumninio_status"));
                    values.put("aluminio_value", obj.getString("alumninio_value"));
                    values.put("material_organico_status", obj.getString("material_organico_status"));
                    values.put("material_organico_value", obj.getString("material_organico_value"));
                    values.put("hidrogeno_status", obj.getString("hidrogeno_status"));
                    values.put("hidrogeno_value", obj.getString("hidrogeno_value"));
                    values.put("potencial_hidrogenionico_status", obj.getString("potencial_hidrogenionico_status"));
                    values.put("potencial_hidrogenionico_value", obj.getString("potencial_hidrogenionico_value"));
                    values.put("data_consulta", obj.getString("data_consulta"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.Consulta_SoloEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_consulta_solo"))};
                    db.delete(ScriptDB.Consulta_SoloEntry.TABLE_NAME," id_consulta_solo=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_consulta_solo", obj.getString("id_consulta_solo"));
                    values1.put("id_usuario", obj.getString("id_usuario"));
                    values1.put("id_sector", obj.getString("id_sector"));
                    values1.put("fosforo_status", obj.getString("fosforo_status"));
                    values1.put("fosforo_value", obj.getString("fosforo_value"));
                    values1.put("potasio_status", obj.getString("potasio_status"));
                    values1.put("potasio_value", obj.getString("potasio_value"));
                    values1.put("calcio_status", obj.getString("calcio_status"));
                    values1.put("calcio_value", obj.getString("calcio_value"));
                    values1.put("magnesio_status", obj.getString("magnesio_status"));
                    values1.put("magnesio_value", obj.getString("magnesio_value"));
                    values1.put("aluminio_status", obj.getString("aluminio_status"));
                    values1.put("aluminio_value", obj.getString("aluminio_value"));
                    values1.put("material_organico_status", obj.getString("material_organico_status"));
                    values1.put("material_organico_value", obj.getString("material_organico_value"));
                    values1.put("hidrogeno_status", obj.getString("hidrogeno_status"));
                    values1.put("hidrogeno_value", obj.getString("hidrogeno_value"));
                    values1.put("potencial_hidrogenionico_status", obj.getString("potencial_hidrogenionico_status"));
                    values1.put("potencial_hidrogenionico_value", obj.getString("potencial_hidrogenionico_value"));
                    values1.put("data_consulta", obj.getString("data_consulta"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_consulta_solo"))};
                    db.update(ScriptDB.Consulta_SoloEntry.TABLE_NAME,values1,"id_consulta_solo=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoTarefas(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(Id_tarea),0) "
                            + FROM + ScriptDB.TareaEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("Id_tarea", String.valueOf(codigo_usuario+1));
                    values.put("Id_usuario", obj.getString("Id_usuario"));
                    values.put("Id_producto", obj.getString("Id_producto"));
                    values.put("Id_empleado", obj.getString("Id_empleado"));
                    values.put("Id_tipo_producto", obj.getString("Id_tipo_producto"));
                    values.put("Id_maquinaria", obj.getString("Id_maquinaria"));
                    values.put("Id_tipo_tarea", obj.getString("Id_tipo_tarea"));
                    values.put("Id_sector", obj.getString("Id_sector"));
                    values.put("Nombre", obj.getString("Nombre"));
                    values.put("Descripcion", obj.getString("Descripcion"));
                    values.put("Fecha_trabajo", obj.getString("Fecha_trabajo"));
                    values.put("horas_trabajadas", obj.getString("horas_trabajadas"));
                    values.put("hectareas_trabajadas", obj.getString("hectareas_trabajadas"));
                    values.put("cantidad_producto", obj.getString("cantidad_producto"));
                    values.put("estado", "Criado");

                    db.insert(ScriptDB.TareaEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("Id_tarea"))};
                    db.delete(ScriptDB.TareaEntry.TABLE_NAME," Id_tarea=?", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("Id_tarea", obj.getString("Id_tarea"));
                    values1.put("id_usuario", obj.getString("Id_usuario"));
                    values1.put("Id_producto", obj.getString("Id_producto"));
                    values1.put("Id_empleado", obj.getString("Id_empleado"));
                    values1.put("Id_tipo_producto", obj.getString("Id_tipo_producto"));
                    values1.put("Id_maquinaria", obj.getString("Id_maquinaria"));
                    values1.put("Id_tipo_tarea", obj.getString("Id_tipo_tarea"));
                    values1.put("Id_sector", obj.getString("Id_sector"));
                    values1.put("Nombre", obj.getString("Nombre"));
                    values1.put("Descripcion", obj.getString("Descripcion"));
                    values1.put("Fecha_trabajo", obj.getString("Fecha_trabajo"));
                    values1.put("horas_trabajadas", obj.getString("horas_trabajadas"));
                    values1.put("hectareas_trabajadas", obj.getString("hectareas_trabajadas"));
                    values1.put("cantidad_producto", obj.getString("cantidad_producto"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("Id_tarea"))};
                    db.update(ScriptDB.TareaEntry.TABLE_NAME,values1,"Id_tarea=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void ManutencaoUsuarioAtivar(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {


                case "I":

                    ContentValues values = new ContentValues();
                    //values.put("Id_tarea", obj.getString("Id_tarea"));
                    values.put("status", encripta("ativo"));

                    String[] args = new String[]{encripta(String.valueOf(obj.getInt("codigo")))};
                    db.update(ScriptDB.UsuariosEntry.TABLE_NAME,values,"codigo=?",args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("Id_tarea", obj.getString("Id_tarea"));
                    values1.put("status", encripta("inativo"));

                    String[] args1 = new String[]{encripta(String.valueOf(obj.getInt("codigo")))};
                    db.update(ScriptDB.UsuariosEntry.TABLE_NAME,values1,"codigo=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void ManutencaoVentaGado(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":

                    int id_venta_gado_novo = 0;

                    int codigo_usuario = 0;

                    String selectQuery2 = SELECT + " ifnull(max(id_venta_gado),0) "
                            + FROM + ScriptDB.VentaGadoEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery2, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values1 = new ContentValues();
                    values1.put("id_venta_gado", String.valueOf(codigo_usuario+1));
                    values1.put("fecha_venta", obj.getString("fecha_venta"));
                    values1.put("estado", "Criado");
                    db.insert(ScriptDB.VentaGadoEntry.TABLE_NAME, null, values1);


                    String selectQuery1 = SELECT + " MAX(id_venta_gado) as id_venta_gado"
                            + FROM + ScriptDB.VentaGadoEntry.TABLE_NAME
                            + WHERE + " fecha_venta = '" + String.valueOf(obj.getString("fecha_venta")) + "' ";

                    Cursor cursor1 = db.rawQuery(selectQuery1, null);


                    if (cursor1.moveToFirst()) {
                        id_venta_gado_novo = cursor1.getInt(0);
                    }

                    cursor1.close();


                    int codigo_usuario2 = 0;

                    String selectQuery3 = SELECT + " ifnull(max(id_venta_gado_detalle),0) "
                            + FROM + ScriptDB.VentaGadoDetalleEntry.TABLE_NAME;

                    Cursor cursor2 = db.rawQuery(selectQuery3, null);

                    if (cursor2.moveToFirst()) {
                        codigo_usuario2 = cursor2.getInt(0);
                    }

                    cursor.close();


                    ContentValues values2 = new ContentValues();
                    values2.put("id_venta_gado_detalle", String.valueOf(codigo_usuario2+1));
                    values2.put("id_venta_gado", String.valueOf(id_venta_gado_novo));
                    values2.put("id_animal", obj.getString("id_animal"));
                    values2.put("precio", obj.getString("precio"));
                    values2.put("nome_gado", obj.getString("nome_gado"));
                    values2.put("id_usuario", obj.getString("id_usuario"));
                    db.insert(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME, null, values2);


                    String[] args = new String[]{String.valueOf(obj.getInt("id_animal"))};
                    db.delete(ScriptDB.AnimalEntry.TABLE_NAME," id_animal=? ", args);
                    db.delete(ScriptDB.Historial_LecheEntry.TABLE_NAME," id_animal=? ", args);
                    db.delete(ScriptDB.Historial_ConsumoEntry.TABLE_NAME," id_animal=? ", args);
                    db.delete(ScriptDB.Historial_EngordeEntry.TABLE_NAME," id_animal=? ", args);


                    db.close();
                    break;

                case "D":

                    Log.d("args1",String.valueOf(obj.getInt("id_venta_gado")));
                    Log.d("args2",String.valueOf(obj.getInt("id_venta_gado")) + " " +String.valueOf(obj.getInt("id_animal")));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_venta_gado"))};
                    String[] args2 = new String[]{String.valueOf(obj.getInt("id_venta_gado")),String.valueOf(obj.getInt("id_animal"))};

                    db.delete(ScriptDB.VentaGadoEntry.TABLE_NAME," id_venta_gado=? ", args1);
                    db.delete(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME," id_venta_gado=? AND id_animal=? ", args2);
                    db.close();
                    break;

                case "X":

                    db.delete(ScriptDB.VentaGadoEntry.TABLE_NAME, null, null);
                    db.delete(ScriptDB.VentaGadoDetalleEntry.TABLE_NAME, null, null);
                    db.close();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void ManutencaoSector(JSONObject obj) {
        db = helperDB.getWritableDatabase();

        try {

            switch (obj.getString("acao")) {
                case "I":

                    int sector_ultimo = 0;

                    int codigo_usuario = 0;

                    String selectQuery = SELECT + " ifnull(max(Id_sector),0) "
                            + FROM + ScriptDB.SectorEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values1 = new ContentValues();
                    values1.put("Id_sector", String.valueOf(codigo_usuario+1));
                    values1.put("Id_usuario", obj.getString("id_usuario"));
                    values1.put("Id_cultivo", String.valueOf(0));
                    values1.put("Status", obj.getString("status"));
                    values1.put("Nombre", obj.getString("nombre"));
                    values1.put("Hectareas", obj.getString("hectareas"));
                    values1.put("estado", "Criado");
                    db.insert(ScriptDB.SectorEntry.TABLE_NAME, null, values1);


                    String selectQuery1 = SELECT + " MAX(Id_sector)  as Id_sector"
                            + FROM + ScriptDB.SectorEntry.TABLE_NAME;

                    Cursor cursor1 = db.rawQuery(selectQuery1, null);


                    if (cursor1.moveToFirst()) {
                        sector_ultimo = cursor1.getInt(0);
                    }

                    cursor1.close();


                    ContentValues values2 = new ContentValues();
                    String latitude[] = obj.getString("latitude").split(",");
                    String longitude[] = obj.getString("longitude").split(",");

                    for(int i = 0; i < latitude.length; i++){

                        int codigo_usuario2 = 0;

                        String selectQuery3 = SELECT + " ifnull(max(id_coordenada),0) "
                                + FROM + ScriptDB.CoordenadasEntry.TABLE_NAME;

                        Cursor cursor3 = db.rawQuery(selectQuery3, null);

                        if (cursor.moveToFirst()) {
                            codigo_usuario2 = cursor3.getInt(0);
                        }

                        cursor.close();

                        values2.put("id_coordenada", String.valueOf(codigo_usuario2+1));
                        values2.put("latitude",  latitude[i].trim());
                        values2.put("longitude", longitude[i].trim());
                        values2.put("id_sector", sector_ultimo);
                        values2.put("estado", "Criado");
                        db.insert(ScriptDB.CoordenadasEntry.TABLE_NAME, null, values2);
                    }

                    db.close();

                    break;

                case "D":

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_sector"))};
                    String[] args2 = new String[]{String.valueOf(obj.getInt("id_sector")),String.valueOf(obj.getInt("id_usuario"))};

                    db.delete(ScriptDB.CoordenadasEntry.TABLE_NAME," id_sector=? ", args1);
                    db.delete(ScriptDB.SectorEntry.TABLE_NAME," Id_sector=? AND Id_usuario=? ", args2);
                    db.close();
                    break;

                case "X":

                    db.delete(ScriptDB.CoordenadasEntry.TABLE_NAME, null, null);
                    db.delete(ScriptDB.SectorEntry.TABLE_NAME, null, null);
                    db.close();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void ManutencaoVisitas(JSONObject obj) {

        db = helperDB.getWritableDatabase();
        try {

            switch (obj.getString("acao")) {
                case "I":

                    int codigo_usuario = 0;

                    String selectQuery1 = SELECT + " ifnull(max(id_visitas),0) "
                            + FROM + ScriptDB.VisitasEntry.TABLE_NAME;

                    Cursor cursor = db.rawQuery(selectQuery1, null);

                    if (cursor.moveToFirst()) {
                        codigo_usuario = cursor.getInt(0);
                    }

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("id_visitas", String.valueOf(codigo_usuario+1));
                    values.put("usuario", obj.getString("usuario"));
                    values.put("latitude", obj.getString("latitude"));
                    values.put("longitude", obj.getString("longitude"));
                    values.put("pim", obj.getString("pim"));
                    values.put("imei", obj.getString("imei"));
                    values.put("versao", obj.getString("versao"));
                    values.put("cliente", obj.getString("cliente"));
                    values.put("motivo", obj.getString("motivo"));
                    values.put("data_agenda", obj.getString("data_agenda"));
                    values.put("data_visita", obj.getString("data_visita"));
                    values.put("resultado", obj.getString("resultado"));
                    values.put("deslocamento", obj.getString("deslocamento"));
                    values.put("situacao", obj.getString("situacao"));
                    values.put("obs", obj.getString("obs"));
                    values.put("cadastrante", obj.getString("cadastrante"));
                    values.put("estado", "Criado");

                    Log.d("MVisitas latitude",obj.getString("latitude"));
                    Log.d("MVisitas longitude",obj.getString("longitude"));

                    db.insert(ScriptDB.VisitasEntry.TABLE_NAME, null, values);
                    db.close();
                    break;

                case "D":

                    String[] args = new String[]{String.valueOf(obj.getInt("id_visita"))};
                    db.delete(ScriptDB.VisitasEntry.TABLE_NAME," id_visitas=? ", args);
                    db.close();
                    break;

                case "E":


                    ContentValues values1 = new ContentValues();
                    //values.put("id_visitas", obj.getString("id_visitas"));
                    values1.put("usuario", obj.getString("usuario"));
                    values1.put("latitude", obj.getString("latitude"));
                    values1.put("longitude", obj.getString("longitude"));
                    values1.put("pim", obj.getString("pim"));
                    values1.put("imei", obj.getString("imei"));
                    values1.put("versao", obj.getString("versao"));
                    values1.put("cliente", obj.getString("cliente"));
                    values1.put("motivo", obj.getString("motivo"));
                    values1.put("data_agenda", obj.getString("data_agenda"));
                    values1.put("data_visita", obj.getString("data_visita"));
                    values1.put("resultado", obj.getString("resultado"));
                    values1.put("deslocamento", obj.getString("deslocamento"));
                    values1.put("situacao", obj.getString("situacao"));
                    values1.put("obs", obj.getString("obs"));
                    values1.put("cadastrante", obj.getString("cadastrante"));

                    String[] args1 = new String[]{String.valueOf(obj.getInt("id_visita"))};
                    db.update(ScriptDB.VisitasEntry.TABLE_NAME,values1,"id_visitas=?",args1);
                    db.close();

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public ArrayList<Quantidade> getquantidade(int id_usuario) {

        db = helperDB.getWritableDatabase();

        ArrayList<Quantidade> confList = new ArrayList<Quantidade>();
        String selectQuery =
                SELECT + " 'AlertaPraga' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.Alerta_plagaEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Clientes' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.ClienteEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Colheitas' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.CosechaEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Despesas' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.DespesasEntry.TABLE_NAME + WHERE + " usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Empregados' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.EmpleadoEntry.TABLE_NAME + WHERE + " Id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Gado' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.AnimalEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Maquinarias' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.MaquinariaEntry.TABLE_NAME + WHERE + " Id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Negociacoes' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.NegociacoesEntry.TABLE_NAME + WHERE + " usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'PontoInteresse' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.MapearEntry.TABLE_NAME + WHERE + " usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Premios' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.PremiosEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Produto' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.ProductoEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Setores' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.SectorEntry.TABLE_NAME + WHERE + " Id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Solo' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.Consulta_SoloEntry.TABLE_NAME + WHERE + " Id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Tareas' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.TareaEntry.TABLE_NAME + WHERE + " Id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'VentaGado' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.VentaGadoDetalleEntry.TABLE_NAME + WHERE + " id_usuario = " + id_usuario + " "
                        + " UNION ALL "
                        + SELECT + " 'Visita' as Modulo, Count(*) as Quantidade " + FROM + ScriptDB.VisitasEntry.TABLE_NAME + WHERE + " usuario = " + id_usuario + " ";


        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Quantidade conf = new Quantidade();
                conf.setModulo(cursor.getString(0));
                conf.setQuantidade(cursor.getString(1));
                confList.add(conf);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return confList;
    }











}
