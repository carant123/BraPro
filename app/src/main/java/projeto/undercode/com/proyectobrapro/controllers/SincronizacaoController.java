package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.forms.ColheitaForm;
import projeto.undercode.com.proyectobrapro.fragments.ColheitasFragment;
import projeto.undercode.com.proyectobrapro.fragments.SincronizacaoFragment;
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
import projeto.undercode.com.proyectobrapro.models.Negociacoe;
import projeto.undercode.com.proyectobrapro.models.PontoInteresse;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.models.Premio;
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.models.Quantidade;
import projeto.undercode.com.proyectobrapro.models.Safra;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.models.Solo2;
import projeto.undercode.com.proyectobrapro.models.Tarefa;
import projeto.undercode.com.proyectobrapro.models.Usuario;
import projeto.undercode.com.proyectobrapro.models.VentaGado;
import projeto.undercode.com.proyectobrapro.models.Visita;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 15/03/2017.
 */

public class SincronizacaoController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome, login;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public static int MILISEGUNDOS_ESPERA = 1000;

    ArrayList<Colheita> colheitalocal;
    ArrayList<String> idcolheitalocal;
    ArrayList<String> idcolheitaremoto;

    ArrayList<Cliente> clientelocal;
    ArrayList<Maquinaria> maquinarialocal;
    ArrayList<Empregado> empregadolocal;
    ArrayList<Produto> produtolocal;
    ArrayList<Despesa> despesalocal;
    ArrayList<PontoInteresse> pontolocal ;
    ArrayList<Premio> premiolocal ;
    ArrayList<Negociacoe> negociacoeslocal ;
    ArrayList<Visita> visitalocal;
    ArrayList<Tarefa> tarefalocal;
    ArrayList<Solo2> sololocal;
    ArrayList<AlertaPraga> alertapraga;
    ArrayList<Safra> safralocal;
    ArrayList<VentaGado> ventagadolocal;

    ArrayList<Coordenadas> coordendassector;
    ArrayList<String> idcoordendassectorlocal;
    ArrayList<String> idcoordendassectorremoto;


    ArrayList<LoteGado> totallotegadolocal;

    ArrayList<String> idlotelocal;
    ArrayList<String> idloteremoto;
    int contadorlote=0;
    int contadorcolheita=0;
    int contadorsetor=0;


    ArrayList<Gado> totalgadolocal = new ArrayList<Gado>();
    ArrayList<String> idgadolocal;
    ArrayList<String> idgadoremoto;
    int contadorgado=0;


    ArrayList<HistorialConsumo> totalhconsumolocal;
    ArrayList<HistorialLeche> totalhlechelocal;
    ArrayList<HistorialEngorde> totalhengordelocal;

    @BindString(R.string.ConsultaUsers)
    String wsConsultaUsers;

    @BindView(R.id.menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;
    @BindView(R.id.fab_btnAgregar) FloatingActionButton fab_btnAgregar;

    private SincronizacaoFragment sincronizacaoFragment;
    String V = "A";

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public SincronizacaoFragment getSincronizacaoFragment() { return this.sincronizacaoFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_sincro;
    }


    public void getStringresults(String response2, String option){

        if(option == null){
            option = "";
        }

        if(option == ""){
            ToastMsg(response2.toString());
        }

        if(option.equals("Colheita")){
            String colheitacode = response2.substring(response2.indexOf("(") + 1,response2.indexOf(")"));
            idcolheitaremoto.add(colheitacode);

            contadorcolheita++;


            if ( contadorcolheita == colheitalocal.size()){


                EnviarSetor();
                //FuncaoDelayGado(MILISEGUNDOS_ESPERA);

            }

        }

        if(option.equals("Setor")){

            String setorcode = response2.substring(response2.indexOf("(") + 1,response2.indexOf(")"));
            idcoordendassectorremoto.add(setorcode);

            contadorsetor++;

            if ( contadorsetor == coordendassector.size()){

                EnviarSafra();
                //FuncaoDelayGado(MILISEGUNDOS_ESPERA);

            }

        }


        if(option.equals("Lote")){

            String lotecode = response2.substring(response2.indexOf("(") + 1,response2.indexOf(")"));
            idloteremoto.add(lotecode);

            contadorlote++;

            if ( contadorlote == totallotegadolocal.size()){


                gado();
                //FuncaoDelayGado(MILISEGUNDOS_ESPERA);

            }

        }

        if(option.equals("Gado")){
            String gadocode = response2.substring(response2.indexOf("(") + 1,response2.indexOf(")"));
            idgadoremoto.add(gadocode);

            contadorgado++;

            if ( contadorgado == totalgadolocal.size()){

                historial();
                //FuncaoDelayHistorial(MILISEGUNDOS_ESPERA);
            }

        }

    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if(option == null){
            option = "";
        }

        if ( option.equals("ConsultaUsers") ) {

            Log.d("length", String.valueOf(response.length()));

            if (response.length() > 0) {

                Sincronizacao();

            } else {

                ToastMsg("No existe usuario");
            }

            ToastMsg("Feito envio");
            UpdateList();
        }

        if ( option.equals("ConsultaAgregar") ) {

            Log.d("length", String.valueOf(response.length()));

            if (response.length() > 0) {

                AgregarColeita();

            } else {

                ToastMsg("No existe usuario");
            }

            ToastMsg("Feito envio");
            UpdateList();
        }

        if(option.equals("")){
            Log.d("result",response.toString());
        }



        if (option == "Colheita")
        {
            Colheita p;
            ArrayList<Colheita> aux = new ArrayList<Colheita>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Colheita(
                                jo.getInt("Id_cosecha"),
                                jo.getString("Nombre"),
                                jo.getInt("id_usuario")
                        );

                        Log.d("id_usuario",String.valueOf(jo.getInt("id_usuario")));
                        aux.add(p);
                    }

                    localdb.CargaColhieta(aux);
                    AgregarCliente();

                } else {
                    //ToastMsg("Não tem colheitas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "Cliente")
        {

            Cliente p;
            ArrayList<Cliente> aux = new ArrayList<Cliente>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Cliente(
                                jo.getInt("id_cliente"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getString("organizacion"),
                                jo.getString("numero"),
                                jo.getString("direccion"),
                                jo.getString("area"),
                                jo.getString("cpf"),
                                jo.getString("data_insercao")
                        );


                        aux.add(p);
                    }

                    localdb.CargaCliente(aux);
                    AgregarMaquinaria();
                } else {
                    //ToastMsg("Não tem clientes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        if (option == "Maquinaria")
        {

            Maquinaria p;
            ArrayList<Maquinaria> aux = new ArrayList<Maquinaria>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Maquinaria(
                                jo.getInt("Id_maquinaria"),
                                jo.getInt("Id_usuario"),
                                jo.getString("Nombre"),
                                jo.getString("Registro"),
                                jo.getString("Fecha_Adquisicion"),
                                jo.getInt("Precio"),
                                jo.getString("Tipo"),
                                jo.getString("Descripcion"),
                                jo.getString("Modelo"),
                                jo.getInt("costo_mantenimiento"),
                                jo.getInt("vida_util_horas"),
                                jo.getInt("vida_util_ano"),
                                jo.getInt("potencia_maquinaria"),
                                jo.getString("tipo_adquisicion")
                        );

                        aux.add(p);
                    }

                    localdb.CargaMaquinaria(aux);
                    AgregarEmpregado();

                } else {
                    //ToastMsg("Não tem maquinarias");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "Empregado")
        {
            Empregado p;
            ArrayList<Empregado> aux = new ArrayList<Empregado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Empregado(
                                jo.getInt("Id_empleado"),
                                jo.getInt("Id_usuario"),
                                jo.getString("Nombre"),
                                jo.getString("Fecha_contratacion"),
                                jo.getInt("Edad"),
                                jo.getString("Rol"),
                                jo.getString("contacto"),
                                jo.getInt("salario"),
                                jo.getString("fin_de_contrato"),
                                jo.getInt("tipo_contrato"),
                                jo.getString("N_tipo_contrato")
                        );

                        aux.add(p);
                    }

                    localdb.CargaEmpregado(aux);
                    AgregarProduto();

                } else {
                    //ToastMsg("Não tem empregados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "Produto")
        {

            Produto p;
            ArrayList<Produto> aux = new ArrayList<Produto>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Produto(
                                jo.getInt("Id_producto"),
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre"),
                                jo.getString("N_TipoProducto"),
                                jo.getString("Fecha_registro"),
                                jo.getString("Fecha_expiracion"),
                                jo.getString("Funcion"),
                                jo.getString("Descipcion"),
                                jo.getString("Composicion"),
                                jo.getString("Objeto"),
                                jo.getString("Imagen"),
                                jo.getString("lote"),
                                jo.getString("custo"),
                                jo.getInt("id_usuario"),
                                jo.getString("kilos")

                        );

                        aux.add(p);
                    }

                    localdb.CargaProduto(aux);
                    AgregarDespesa();

                } else {
                    //ToastMsg("Não tem produtos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if ( option == "Despesa") {
            Despesa p;
            ArrayList<Despesa> aux = new ArrayList<Despesa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Despesa(
                                jo.getInt("id_despesa"),
                                jo.getInt("usuario"),
                                jo.getString("pim"),
                                jo.getString("imei"),
                                jo.getString("latitude"),
                                jo.getString("longitude"),
                                jo.getString("versao"),
                                jo.getString("valor"),
                                jo.getString("data_despesa"),
                                jo.getInt("tipo_despesa_tempo"),
                                jo.getString("N_tipo_despesa_tempo"),
                                jo.getInt("tipo_despesa"),
                                jo.getString("N_tipo_despesa")
                        );

                        aux.add(p);
                    }

                    localdb.CargaDespesa(aux);
                    AgregarPonto();

                } else {
                    //ToastMsg("Não tem despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "Ponto") {

            PontoInteresse p;
            ArrayList<PontoInteresse> aux = new ArrayList<PontoInteresse>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new PontoInteresse(
                                jo.getInt("id_mapear"),
                                jo.getInt("usuario"),
                                jo.getString("pim"),
                                jo.getString("imei"),
                                jo.getString("latitude"),
                                jo.getString("longitude"),
                                jo.getString("versao"),
                                jo.getString("endereco"),
                                jo.getString("tipo"),
                                jo.getString("obs"),
                                jo.getString("data_cadastro")
                        );

                        aux.add(p);
                    }

                    localdb.CargaPontoInteresse(aux);
                    AgregarPremio();

                } else {
                    //ToastMsg("Não tem pontos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if ( option == "Premios")
        {
            Premio p;
            ArrayList<Premio> aux = new ArrayList<Premio>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Premio(

                                jo.getInt("id_premio"),
                                jo.getInt("empleado"),
                                jo.getString("N_Empleado"),
                                jo.getInt("premio"),
                                jo.getInt("ano"),
                                jo.getInt("mes"),
                                jo.getString("mes_descricao"),
                                jo.getString("data_atualizacao"),
                                jo.getInt("id_usuario")

                        );
                        aux.add(p);

                    }

                    localdb.CargaPremios(aux);
                    AgregarSector();
                } else {
                    //ToastMsg("Não tem premios");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if (option.equals("Coordenadas")) {

            Coordenadas p;
            ArrayList<Coordenadas> auxCoordendas = new ArrayList<Coordenadas>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        Setor s = new Setor(
                                jo.getString("Id_sector"),
                                jo.getString("Id_usuario"),
                                jo.getString("Id_cultivo"),
                                jo.getString("Status"),
                                jo.getString("Nombre"),
                                jo.getString("Hectareas")
                        );

                        p = new Coordenadas(
                                jo.getInt("id_coordenada"),
                                jo.getString("latitude"),
                                jo.getString("longitude"),
                                s
                        );
                        Log.d("latilong" + i, p.getLatitude());
                        Log.d("latilong" + i, p.getLongitude());
                        auxCoordendas.add(p);
                    }

                    localdb.CargaSectoresCoordendas(auxCoordendas);
                    AgregarNegociacoes();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if (option == "Negociacao")
        {
            Negociacoe p;
            ArrayList<Negociacoe> aux = new ArrayList<Negociacoe>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        Log.d("GetArray", "array results");

                        p = new Negociacoe(
                                jo.getInt("id_negociacoes"),
                                jo.getInt("usuario"),
                                jo.getString("pim"),
                                jo.getString("imei"),
                                jo.getString("latitude"),
                                jo.getString("longitude"),
                                jo.getString("versao"),
                                jo.getString("cpf"),
                                jo.getString("nome"),
                                jo.getString("tipo_local"),
                                jo.getString("local"),
                                jo.getInt("produto"),
                                jo.getInt("taxa"),
                                jo.getInt("valor_negociado"),
                                jo.getString("data_pagamento"),
                                jo.getString("data_cadastro"),
                                jo.getString("N_producto")
                        );

                        aux.add(p);
                    }

                    localdb.CargaNegociacoes(aux);
                    AgregarVisita();

                } else {
                    //ToastMsg("Não tem negociacoes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



            if (option == "Visita") {

                Visita p;

                ArrayList<Visita> aux = new ArrayList<Visita>();

                JSONObject jo = null;

                try {
                    if (response.length() > 0) {
                        for (int i=0; i<response.length(); i++) {
                            jo = response.getJSONObject(i);


                            p = new Visita(
                                    jo.getInt("id_visita"),
                                    jo.getInt("usuario"),
                                    jo.getString("latitude"),
                                    jo.getString("longitude"),
                                    jo.getString("pim"),
                                    jo.getString("imei"),
                                    jo.getString("versao"),
                                    jo.getInt("cliente"),
                                    jo.getString("N_Cliente"),
                                    jo.getString("motivo"),
                                    jo.getString("data_agenda"),
                                    jo.getString("data_visita"),
                                    jo.getString("resultado"),
                                    jo.getInt("deslocamento"),
                                    jo.getString("situacao"),
                                    jo.getString("obs"),
                                    jo.getInt("cadastrante")
                            );

                            aux.add(p);

                        }

                        localdb.CargaVisita(aux);
                        AgregarTareas();

                    } else {
                        //ToastMsg("Não tem visitas");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        if (option == "Tareas") {

            Tarefa p;
            ArrayList<Tarefa> aux = new ArrayList<Tarefa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Tarefa(
                                jo.getInt("Id_tarea"),
                                jo.getInt("Id_usuario"),
                                jo.getInt("Id_producto"),
                                jo.getString("Nombre_Producto"),
                                jo.getInt("Id_empleado"),
                                jo.getString("Nombre_Empleado"),
                                jo.getString("Contacto_Empleado"),
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre_Tipo_Producto"),
                                jo.getInt("Id_maquinaria"),
                                jo.getString("Nombre_Maquinaria"),
                                jo.getInt("Id_tipo_tarea"),
                                jo.getString("Nombre_Tipo_Tarea"),
                                jo.getInt("Id_sector"),
                                jo.getString("Nombre_Sector"),
                                jo.getString("Nombre_Tarea"),
                                jo.getString("Descripcion"),
                                jo.getString("Fecha_trabajo"),
                                jo.getInt("horas_trabajadas"),
                                jo.getInt("hectareas_trabajadas"),
                                jo.getInt("cantidad_producto")
                        );

                        aux.add(p);
                    }

                    localdb.CargaTarefa(aux);
                    AgregarSolo();
                } else {
                    //ToastMsg("Não tem tarefas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if ( option == "Solo") {
            Solo2 p;
            ArrayList<Solo2> aux = new ArrayList<Solo2>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Solo2(
                                jo.getInt("id_consulta_solo2"),
                                jo.getInt("id_sector"),
                                jo.getString("N_Sector"),
                                jo.getString("fosforo_status"),
                                jo.getDouble("fosforo_value"),
                                jo.getString("potasio_status"),
                                jo.getDouble("potasio_value"),
                                jo.getString("calcio_status"),
                                jo.getDouble("calcio_value"),
                                jo.getString("magnesio_status"),
                                jo.getDouble("magnesio_value"),
                                jo.getString("alumninio_status"),
                                jo.getDouble("alumninio_value"),
                                jo.getString("material_organico_status"),
                                jo.getDouble("material_organico_value"),
                                jo.getString("hidrogeno_status"),
                                jo.getDouble("hidrogeno_value"),
                                jo.getString("potencial_hidrogenionico_status"),
                                jo.getDouble("potencial_hidrogenionico_value"),
                                jo.getString("data_consulta"),
                                jo.getInt("id_usuario")
                        );

                        aux.add(p);
                    }

                    localdb.CargaSolo(aux);
                    AgregarAlertaPlaga();

                } else {
                    //ToastMsg("Não tem solos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



        if (option == "AlertaPraga")
        {

            AlertaPraga p;
            ArrayList<AlertaPraga> aux = new ArrayList<AlertaPraga>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new AlertaPraga(
                                jo.getInt("Id_alerta_plaga"),
                                jo.getInt("Id_sector"),
                                jo.getString("N_Sector"),
                                jo.getInt("Id_plaga"),
                                jo.getString("N_Plaga"),
                                jo.getString("N_AlertaPlaga"),
                                jo.getString("Fecha_registro"),
                                jo.getString("Descripcion"),
                                jo.getString("Status"),
                                jo.getInt("id_usuario")
                        );

                        aux.add(p);
                    }

                    localdb.CargaAlertaPraga(aux);
                    AgregarPraga();

                } else {
                    //ToastMsg("Não tem alertas de pragas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if (option == "Praga")
        {
            Praga p;
            ArrayList<Praga> aux = new ArrayList<Praga>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Praga(
                                jo.getInt("Id_plaga"),
                                jo.getString("Nombre"),
                                jo.getString("Caracteristicas"),
                                jo.getString("Sintomas"),
                                jo.getString("Tratamiento"),
                                jo.getString("Clase"),
                                jo.getString("Descripcion"),
                                jo.getString("Prevencion")
                        );
                        aux.add(p);

                    }

                    localdb.CargaPlaga(aux);
                    AgregarLoteGado();

                } else {
                    //ToastMsg("Não tem pragas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if ( option == "LoteGado") {
            LoteGado p;
            ArrayList<LoteGado> aux = new ArrayList<LoteGado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new LoteGado(
                                jo.getInt("id_lote_gado"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getString("descripcao"),
                                jo.getString("cantidad")
                        );

                        aux.add(p);

                        AgregarGado(jo.getInt("id_lote_gado"));

                    }

                    localdb.CargaLote(aux);

                } else {
                    //ToastMsg("Não tem lotes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        if ( option == "Gado") {
            Gado p;
            ArrayList<Gado> aux = new ArrayList<Gado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Gado(
                                jo.getInt("id_animal"),
                                jo.getInt("id_lote_gado"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getInt("peso_inicial"),
                                jo.getInt("cod_adquisicao"),
                                jo.getString("tipo_adquisicao"),
                                jo.getInt("precio"),
                                jo.getString("fecha"),
                                jo.getString("id_parto")
                        );

                        aux.add(p);
                        AgregarHistorialConsumo(jo.getInt("id_animal"));
                        AgregarHistorialLeche(jo.getInt("id_animal"));
                        AgregarHistorialEngorde(jo.getInt("id_animal"));
                    }

                    localdb.CargaGado(aux);

                } else {
                    //ToastMsg("Não tem gados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




        if (option == "Engorde")
        {
            HistorialEngorde p;
            ArrayList<HistorialEngorde> aux = new ArrayList<HistorialEngorde>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new HistorialEngorde(
                                jo.getInt("id_historial_engorde"),
                                jo.getInt("id_animal"),
                                jo.getInt("peso"),
                                jo.getString("fecha_medicion"),
                                jo.getString("nombre")
                        );

                        aux.add(p);

                    }

                    localdb.CargaHistorialEngorde(aux);

                } else {
                    //ToastMsg("Não tem história de engorda");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        if (option == "Leche")
        {

            HistorialLeche p;
            ArrayList<HistorialLeche> aux2 = new ArrayList<HistorialLeche>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new HistorialLeche(
                                jo.getInt("id_historial_leche"),
                                jo.getInt("id_animal"),
                                jo.getInt("cantidad"),
                                jo.getString("fecha_obtencao"),
                                jo.getString("nombre")
                        );

                        aux2.add(p);

                    }

                    localdb.CargaHistorialLeche(aux2);

                } else {
                    //ToastMsg("Não tem historia de leite");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        if (option == "Consumo")
        {
            HistorialConsumo p;
            ArrayList<HistorialConsumo> aux3 = new ArrayList<HistorialConsumo>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new HistorialConsumo(
                                jo.getInt("id_historial_consumo"),
                                jo.getInt("id_animal"),
                                jo.getInt("id_producto"),
                                jo.getInt("cantidad_consumida"),
                                jo.getString("fecha_consumo"),
                                jo.getString("nombre"),
                                jo.getString("N_producto")
                        );

                        aux3.add(p);
                    }

                    localdb.CargaHistorialConsumo(aux3);

                } else {
                    //ToastMsg("Não tem historia de consumo");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        login = prefs.getString("login", "No login defined");


        colheitalocal = localdb.ConsultaColheita(String.valueOf(id_user),"Criado");
        clientelocal = localdb.ConsultaClientes(String.valueOf(id_user),"Criado");
        maquinarialocal = localdb.ConsultaMaquinarias(String.valueOf(id_user),"Criado");
        empregadolocal = localdb.ConsultaEmpleados(String.valueOf(id_user),"Criado");

        produtolocal = localdb.ConsultaProducto(id_user,"Criado");
        despesalocal = localdb.ConsultaDespesas(String.valueOf(id_user),"Criado");
        pontolocal = localdb.ConsultaPontosInteresse(id_user,"Criado");
        visitalocal = localdb.ConsultaVisitas(id_user,"Criado");
        tarefalocal = localdb.ConsultaTarefas(id_user,"Criado");
        sololocal = localdb.ConsultaSolo(id_user,"Criado");
        alertapraga = localdb.ConsultaAlertaPlaga(id_user,"Criado");


        coordendassector = localdb.ConsultaSectores_Coordenadas(id_user,"Criado");

       totallotegadolocal = localdb.ConsultaLotegado(String.valueOf(id_user),"Criado");

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnAgregar.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_btnAgregar.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificacaoUsuario2();
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UpdateList();
                verificacaoUsuario();
            }
        });

        createFragments();
    }

    public void Sincronizacao(){

        contadorcolheita=0;
        contadorsetor=0;

        colheitalocal = localdb.ConsultaColheita(String.valueOf(id_user),"Criado");
        idcolheitalocal = new ArrayList<String>();
        idcolheitaremoto = new ArrayList<String>();

        clientelocal = localdb.ConsultaClientes(String.valueOf(id_user),"Criado");
        maquinarialocal = localdb.ConsultaMaquinarias(String.valueOf(id_user),"Criado");
        empregadolocal = localdb.ConsultaEmpleados(String.valueOf(id_user),"Criado");

        produtolocal = localdb.ConsultaProducto(id_user,"Criado");
        despesalocal = localdb.ConsultaDespesas(String.valueOf(id_user),"Criado");
        pontolocal = localdb.ConsultaPontosInteresse(id_user,"Criado");
        premiolocal = localdb.ConsultaPremios(id_user,"Criado");
        visitalocal = localdb.ConsultaVisitas(id_user,"Criado");
        safralocal = localdb.ConsultaCultivos(id_user,"Criado");


        coordendassector = localdb.ConsultaSectores_Coordenadas(id_user,"Criado");
        idcoordendassectorlocal = new ArrayList<String>();
        idcoordendassectorremoto = new ArrayList<String>();;


        negociacoeslocal = localdb.ConsultaNegociacoes(String.valueOf(id_user),"Criado");
        sololocal = localdb.ConsultaSolo(id_user,"Criado");
        alertapraga = localdb.ConsultaAlertaPlaga(id_user,"Criado");
        ventagadolocal = localdb.ConsultaVentaGado(id_user,"Criado");

        totallotegadolocal = localdb.ConsultaLotegado(String.valueOf(id_user),"Criado");
        idlotelocal = new ArrayList<>();
        idloteremoto = new ArrayList<>();
        contadorlote = 0;

        totalgadolocal = new ArrayList<Gado>();
        idgadolocal = new ArrayList<String>();
        idgadoremoto = new ArrayList<String>();
        contadorgado = 0;


        totalhconsumolocal = new ArrayList<HistorialConsumo>();
        totalhlechelocal = new ArrayList<HistorialLeche>();
        totalhengordelocal = new ArrayList<HistorialEngorde>();

        JSONObject aux = new JSONObject();

        try {


            EnviarColheitas();


            aux = new JSONObject();

            for (int i = 0; i < clientelocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("id_usuario", String.valueOf(id_user));
                aux.put("nombre", clientelocal.get(i).getNombre().replace(" ","%20"));
                aux.put("organizacion", clientelocal.get(i).getOrganizacion().replace(" ","%20"));
                aux.put("numero", clientelocal.get(i).getNumero().replace(" ","%20"));
                aux.put("direccion", clientelocal.get(i).getDireccion().replace(" ","%20"));
                aux.put("area", clientelocal.get(i).getArea().replace(" ","%20"));
                aux.put("Id_cliente", "" );
                aux.put("cpf", clientelocal.get(i).getCpf().replace(" ","%20") );

                remotedb.ManutencaoClientes(aux,null);
            }



            localdb.DeleteInfoCliente(id_user,"Criado");

            aux = new JSONObject();

            for (int i = 0; i < maquinarialocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("id_usuario", String.valueOf(id_user));
                aux.put("nombre", maquinarialocal.get(i).getNombre().replace(" ","%20"));
                aux.put("registro", maquinarialocal.get(i).getRegistro().replace(" ","%20"));
                aux.put("fecha_adquisicion", maquinarialocal.get(i).getFecha_Adquisicion().replace(" ","%20"));
                aux.put("precio", String.valueOf(maquinarialocal.get(i).getPrecio()).replace(" ","%20"));
                aux.put("tipo", maquinarialocal.get(i).getTipo().replace(" ","%20"));
                aux.put("descripcion", maquinarialocal.get(i).getDescripcion().replace(" ","%20"));
                aux.put("modelo", maquinarialocal.get(i).getModelo().replace(" ","%20"));
                aux.put("Id_maquinaria", "");
                aux.put("costo_mantenimiento", String.valueOf(maquinarialocal.get(i).getCosto_mantenimiento()).replace(" ","%20"));
                aux.put("vida_util_horas", String.valueOf(maquinarialocal.get(i).getVida_util_horas()).replace(" ","%20"));
                aux.put("vida_util_ano", String.valueOf(maquinarialocal.get(i).getVida_util_ano()).replace(" ","%20"));
                aux.put("potencia_maquinaria", String.valueOf(maquinarialocal.get(i).getPotencia_maquinaria()).replace(" ","%20"));
                aux.put("tipo_adquisicion", maquinarialocal.get(i).getTipo_adquisicion().replace(" ","%20"));

                remotedb.ManutencaoMaquinarias(aux,null);

            }

            localdb.DeleteInfoMaquinaria(id_user,"Criado");

            aux = new JSONObject();


            for (int i = 0; i < empregadolocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("id_usuario", String.valueOf(id_user));
                aux.put("nombre", empregadolocal.get(i).getNombre().replace(" ","%20"));
                aux.put("fecha_contratacion", empregadolocal.get(i).getFecha_contratacion().replace(" ","%20"));
                aux.put("edad", String.valueOf(empregadolocal.get(i).getEdad()).replace(" ","%20"));
                aux.put("rol", empregadolocal.get(i).getRol().replace(" ","%20"));
                aux.put("contacto", empregadolocal.get(i).getContacto().replace(" ","%20"));
                aux.put("Id_empleado", "");
                aux.put("Photo", "");
                aux.put("salario", String.valueOf(empregadolocal.get(i).getSalario()).replace(" ","%20"));
                aux.put("fin_de_contrato", empregadolocal.get(i).getFin_de_contrato().replace(" ","%20"));
                aux.put("tipo_contrato", String.valueOf(empregadolocal.get(i).getTipo_contrato()).replace(" ","%20"));

                remotedb.ManutencaoEmpleados(aux,null);

            }

            localdb.DeleteInfoEmpregado(id_user,"Criado");

            aux = new JSONObject();


            for (int i = 0; i < produtolocal.size(); i++) {


                aux.put("acao", "I");
                aux.put("Id_tipo_producto", String.valueOf(produtolocal.get(i).getId_tipo_producto()).replace(" ","%20"));
                aux.put("Nombre", produtolocal.get(i).getNombre().replace(" ","%20"));
                aux.put("Fecha_expiracion", produtolocal.get(i).getFecha_expiracion().replace(" ","%20"));
                aux.put("Funcion", produtolocal.get(i).getFuncion().replace(" ","%20"));
                aux.put("Descipcion", produtolocal.get(i).getDescipcion().replace(" ","%20"));
                aux.put("Composicion", produtolocal.get(i).getComposicion().replace(" ","%20"));
                aux.put("Objeto", produtolocal.get(i).getObjeto().replace(" ","%20"));
                aux.put("Id_producto", "");
                aux.put("id_usuario", String.valueOf(id_user));
                aux.put("lote", produtolocal.get(i).getLote().replace(" ","%20"));
                aux.put("custo", produtolocal.get(i).getCusto().replace(" ","%20"));
                aux.put("kilos", produtolocal.get(i).getKilos().replace(" ","%20"));

                remotedb.ManutencaoProductos(aux,null);

            }

            localdb.DeleteInfoProduto(id_user,"Criado");

            aux = new JSONObject();

            for (int i = 0; i < despesalocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("usuario", String.valueOf(id_user));
                aux.put("pim", String.valueOf(despesalocal.get(i).getPim()));
                aux.put("imei", String.valueOf(despesalocal.get(i).getImei()));
                aux.put("latitude", despesalocal.get(i).getLatitude());
                aux.put("longitude", despesalocal.get(i).getLongitude());
                aux.put("versao", "versao");
                aux.put("valor", despesalocal.get(i).getValor().replace(" ","%20"));
                aux.put("data_despesa", despesalocal.get(i).getData_despesa().replace(" ","%20"));
                aux.put("id_despesa", "");
                aux.put("tipo_despesa_tempo", String.valueOf(despesalocal.get(i).getTipo_despesa_tempo()));
                aux.put("tipo_despesa", String.valueOf(despesalocal.get(i).getTipo_despesa()));

                remotedb.ManutencaoDespesas(aux,null);

            }

            localdb.DeleteInfoDespesa(id_user,"Criado");

            aux = new JSONObject();

            for (int i = 0; i < pontolocal.size(); i++) {

                aux.put("acao","I");
                aux.put("usuario",String.valueOf(id_user));
                aux.put("pim",String.valueOf(pontolocal.get(i).getPim()));
                aux.put("imei",String.valueOf(pontolocal.get(i).getImei()));
                aux.put("latitude",pontolocal.get(i).getLatitude().replace(" ","%20"));
                aux.put("longitude",pontolocal.get(i).getLongitude().replace(" ","%20"));
                aux.put("versao","2");
                aux.put("endereco",pontolocal.get(i).getEndereco().replace(" ","%20"));
                aux.put("tipo",pontolocal.get(i).getTipo().replace(" ","%20"));
                aux.put("obs",pontolocal.get(i).getObs().replace(" ","%20"));
                aux.put("data_cadastro","");
                aux.put("id_mapear","");

                remotedb.ManutencaoPontoInteresse(aux,null);

            }

            localdb.DeleteInfoPontoInteresse(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < premiolocal.size(); i++) {


                aux.put("acao", "I");
                aux.put("empleado", String.valueOf(premiolocal.get(i).getEmpleado()));
                aux.put("premio", premiolocal.get(i).getPremio());
                aux.put("mes_descricao", premiolocal.get(i).getMes_descricao());
                aux.put("data_atualizacao",  premiolocal.get(i).getData_atualizacao());
                aux.put("id_usuario",  String.valueOf(premiolocal.get(i).getId_usuario()));
                aux.put("id_premio",  "");

                remotedb.ManutencaoPremios(aux,null);

            }

            localdb.DeleteInfoPremio(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < negociacoeslocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("usuario", String.valueOf(negociacoeslocal.get(i).getUsuario()));
                aux.put("pim", negociacoeslocal.get(i).getPim().replace(" ","%20"));
                aux.put("imei", negociacoeslocal.get(i).getImei().replace(" ","%20"));
                aux.put("latitude", negociacoeslocal.get(i).getLatitude().replace(" ","%20"));
                aux.put("longitude", negociacoeslocal.get(i).getLongitude().replace(" ","%20"));
                aux.put("versao", negociacoeslocal.get(i).getVersao().replace(" ","%20"));
                aux.put("cpf", negociacoeslocal.get(i).getCpf().replace(" ","%20"));
                aux.put("nome", negociacoeslocal.get(i).getNome().replace(" ","%20"));
                aux.put("tipo_local", negociacoeslocal.get(i).getTipo_local().replace(" ","%20"));
                aux.put("local", negociacoeslocal.get(i).getLocal().replace(" ","%20"));
                aux.put("producto", String.valueOf(negociacoeslocal.get(i).getProduto()).replace(" ","%20"));
                aux.put("taxa", negociacoeslocal.get(i).getTaxa());
                aux.put("valor_negociado",negociacoeslocal.get(i).getValor_negociado());
                aux.put("data_pagamento", negociacoeslocal.get(i).getData_pagamento().replace(" ","%20"));
                aux.put("data_cadastro", negociacoeslocal.get(i).getData_cadastro().replace(" ","%20"));
                aux.put("id_negociacoes", "");

                remotedb.ManutencaoNegociacoes(aux,null);
            }


            localdb.DeleteInfoNegociacoes(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < visitalocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("usuario", String.valueOf(visitalocal.get(i).getUsuario()));
                aux.put("latitude", visitalocal.get(i).getLatitude().replace(" ","%20"));
                aux.put("longitude", visitalocal.get(i).getLongitude().replace(" ","%20"));
                aux.put("pim", String.valueOf(visitalocal.get(i).getPim()).replace(" ","%20"));
                aux.put("imei", String.valueOf(visitalocal.get(i).getImei()).replace(" ","%20"));
                aux.put("versao", visitalocal.get(i).getVersao().replace(" ","%20"));
                aux.put("cliente", visitalocal.get(i).getCliente());
                aux.put("motivo", visitalocal.get(i).getMotivo().replace(" ","%20"));
                aux.put("data_agenda", visitalocal.get(i).getData_agenda().replace(" ","%20"));
                aux.put("data_visita", visitalocal.get(i).getData_visita().replace(" ","%20"));
                aux.put("resultado", visitalocal.get(i).getResultado().replace(" ","%20"));
                aux.put("deslocamento", visitalocal.get(i).getDeslocamento());
                aux.put("situacao", visitalocal.get(i).getSituacao().replace(" ","%20"));
                aux.put("obs", visitalocal.get(i).getObs().replace(" ","%20"));
                aux.put("cadastrante", String.valueOf(visitalocal.get(i).getCadastrante()).replace(" ","%20"));
                aux.put("id_visita", "");


                remotedb.ManutencaoVisitas(aux,null);

            }

            localdb.DeleteInfoVisita(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < tarefalocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("Id_usuario", String.valueOf(tarefalocal.get(i).getId_usuario()));
                aux.put("Id_producto", String.valueOf(tarefalocal.get(i).getId_producto()));
                aux.put("Id_empleado", String.valueOf(tarefalocal.get(i).getId_empleado()));
                aux.put("Id_tipo_producto",  String.valueOf(tarefalocal.get(i).getId_tipo_producto()));
                aux.put("Id_maquinaria",  String.valueOf(tarefalocal.get(i).getId_maquinaria()));
                aux.put("Id_tipo_tarea",  String.valueOf(tarefalocal.get(i).getId_tipo_tarea()));
                aux.put("Id_sector",  String.valueOf(tarefalocal.get(i).getId_sector()));
                aux.put("Nombre", tarefalocal.get(i).getNombre_Tarea().replace(" ","%20"));
                aux.put("Descripcion", tarefalocal.get(i).getDescripcion().replace(" ","%20"));
                aux.put("Fecha_trabajo", tarefalocal.get(i).getFecha_trabajo().replace(" ","%20"));
                aux.put("Id_tarea", "");
                aux.put("horas_trabajadas", tarefalocal.get(i).getHoras_trabajadas());
                aux.put("hectareas_trabajadas",tarefalocal.get(i).getHectareas_trabajadas());
                aux.put("cantidad_producto", tarefalocal.get(i).getCantidad_producto());

                remotedb.ManutencaoTareas(aux,null);

            }

            localdb.DeleteInfoTarefa(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < sololocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("id_usuario", String.valueOf(sololocal.get(i).getId_usuario()));
                aux.put("id_sector", String.valueOf(sololocal.get(i).getId_sector()));
                aux.put("fosforo_status", sololocal.get(i).getFosforo_status().replace(" ","%20"));
                aux.put("fosforo_value", sololocal.get(i).getFosforo_value());
                aux.put("potasio_status", sololocal.get(i).getPotasio_status().replace(" ","%20"));
                aux.put("potasio_value", sololocal.get(i).getPotasio_value());
                aux.put("calcio_status",  sololocal.get(i).getCalcio_status().replace(" ","%20"));
                aux.put("calcio_value", sololocal.get(i).getCalcio_value());
                aux.put("magnesio_status", sololocal.get(i).getMagnesio_status().replace(" ","%20"));
                aux.put("magnesio_value", sololocal.get(i).getMagnesio_value());
                aux.put("alumninio_status", sololocal.get(i).getAlumninio_status().replace(" ","%20"));
                aux.put("alumninio_value", sololocal.get(i).getAlumninio_value());
                aux.put("material_organico_status", sololocal.get(i).getMaterial_organico_status().replace(" ","%20"));
                aux.put("material_organico_value", sololocal.get(i).getMaterial_organico_value());
                aux.put("hidrogeno_status", sololocal.get(i).getHidrogeno_status().replace(" ","%20"));
                aux.put("hidrogeno_value", sololocal.get(i).getHidrogeno_value());
                aux.put("potencial_hidrogenionico_status", sololocal.get(i).getPotencial_hidrogenionico_status().replace(" ","%20"));
                aux.put("potencial_hidrogenionico_value", sololocal.get(i).getPotencial_hidrogenionico_value());
                aux.put("id_consulta_solo2", "");
                aux.put("data_consulta", sololocal.get(i).getData_consulta().replace(" ","%20"));


                remotedb.ManutencaoSolo2(aux,null);

            }

            localdb.DeleteInfoSolo(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < alertapraga.size(); i++) {

                aux.put("acao", "I");
                aux.put("Id_sector", String.valueOf(alertapraga.get(i).getId_sector()));
                aux.put("Id_plaga", String.valueOf(alertapraga.get(i).getId_plaga()));
                aux.put("Nombre", alertapraga.get(i).getN_AlertaPlaga().replace(" ","%20"));
                aux.put("Fecha_registro", alertapraga.get(i).getFecha_registro().replace(" ","%20"));
                aux.put("Descripcion", alertapraga.get(i).getDescripcion().replace(" ","%20"));
                aux.put("Status", alertapraga.get(i).getStatus().replace(" ","%20"));
                aux.put("Id_alerta_plaga", "" );
                aux.put("id_usuario", String.valueOf(alertapraga.get(i).getId_usuario()));

                remotedb.ManutencaoAlertaPragas(aux,null);

            }

            localdb.DeleteInfoAlertaPraga(id_user,"Criado");


            aux = new JSONObject();

            for (int i = 0; i < ventagadolocal.size(); i++) {

                aux.put("acao", "I");
                aux.put("precio", ventagadolocal.get(i).getPrecio());
                aux.put("id_animal", String.valueOf(ventagadolocal.get(i).getId_animal()));
                aux.put("fecha_venta", ventagadolocal.get(i).getFecha_venta().replace(" ","%20"));
                aux.put("id_venta_gado", "");
                aux.put("nome_gado", ventagadolocal.get(i).getNombre().replace(" ","%20"));
                aux.put("id_usuario", String.valueOf(ventagadolocal.get(i).getId_usuario()));

                remotedb.ManutencaoVentaGado(aux,null);

            }

            localdb.DeleteInfoVentaGado(id_user,"X");


            aux = new JSONObject();

            if ( totallotegadolocal.size() == 0) {
                Log.d("gado", "gado");
                gado();

            } else {

                for (int i = 0; i < totallotegadolocal.size(); i++) {

                    idlotelocal.add(String.valueOf(totallotegadolocal.get(i).getId_lote_gado()));

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", totallotegadolocal.get(i).getNombre().replace(" ", "%20"));
                    aux.put("descripcao", totallotegadolocal.get(i).getDescripcao().replace(" ", "%20"));
                    aux.put("id_lote_gado", "");

                    remotedb.ManutencaoLotegado(aux, "Lote");

                }

            }

            //localdb.DeleteInfoLoteGadoHistorial(id_user,"Criado");




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void gado() {



        ArrayList<Gado> gadolocal = localdb.ConsultaGado(String.valueOf(id_user),"","Criado");

        for (int i = 0; i < gadolocal.size(); i++) {

            totalgadolocal.add(gadolocal.get(i));

        }


        if (totalgadolocal.size() == 0) {

            historial();

        } else {

        for (int i = 0; i < totalgadolocal.size(); i++) {


            boolean found = false;


                    try {

                        idgadolocal.add(String.valueOf(totalgadolocal.get(i).getId_animal()));

                        JSONObject aux2 = new JSONObject();

                        aux2.put("acao", "I");

                        for (int j = 0; j < idlotelocal.size(); j++) {

                            if (idlotelocal.get(j).equals(String.valueOf(totalgadolocal.get(i).getId_lote_gado()))) {

                                aux2.put("id_lote_gado", idloteremoto.get(j));
                                found = true;
                                break;

                            }

                        }

                        if(!found){
                            aux2.put("id_lote_gado", String.valueOf(totalgadolocal.get(i).getId_lote_gado()));
                        }

                        aux2.put("id_usuario", String.valueOf(id_user));
                        aux2.put("nombre", totalgadolocal.get(i).getNombre().replace(" ", "%20"));
                        aux2.put("peso_inicial", String.valueOf(totalgadolocal.get(i).getPeso_inicial()).replace(" ", "%20"));
                        aux2.put("cod_adquisicao", "");
                        aux2.put("tipo_adquisicao", totalgadolocal.get(i).getTipo_adquisicao().replace(" ", "%20"));
                        aux2.put("id_animal", "");
                        aux2.put("precio", String.valueOf(totalgadolocal.get(i).getPrecio()).replace(" ", "%20"));
                        aux2.put("fecha", totalgadolocal.get(i).getFecha().replace(" ", "%20"));
                        aux2.put("id_animal_parto", totalgadolocal.get(i).getId_parto().replace(" ", "%20"));

                        remotedb.ManutencaoGado(aux2, "Gado");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




        }



    }

    }


    public void historial(){


            ArrayList<HistorialLeche> hlechelocal = localdb.ConsultaHistorialLeche("","Criado" );

            for (int i = 0; i < hlechelocal.size(); i++){

                totalhlechelocal.add(hlechelocal.get(i));

            }





            ArrayList<HistorialConsumo> hconsumolocal = localdb.ConsultaHistorialConsumo("","Criado" );


            for (int i = 0; i < hconsumolocal.size(); i++){

                totalhconsumolocal.add(hconsumolocal.get(i));

            }



            ArrayList<HistorialEngorde> hengordelocal = localdb.ConsultaHistorialEngorde("","Criado" );

            for (int i = 0; i < hengordelocal.size(); i++){

                totalhengordelocal.add(hengordelocal.get(i));

            }




        for (int i = 0; i < totalhlechelocal.size(); i++){

            boolean found = false;

                    try {

                        JSONObject aux = new JSONObject();

                        aux.put("acao", "I");
                        for (int j = 0; j < idgadolocal.size(); j++) {
                            if (idgadolocal.get(j).equals(String.valueOf(totalhlechelocal.get(i).getId_animal()))) {

                                aux.put("id_animal", idgadoremoto.get(j));
                                found = true;
                                break;
                            }
                        }

                        if(!found){
                            aux.put("id_animal", String.valueOf(totalhlechelocal.get(i).getId_animal()).replace(" ","%20"));
                        }

                        aux.put("cantidad", String.valueOf(totalhlechelocal.get(i).getCantidad()).replace(" ","%20"));
                        aux.put("fecha_obtencao", totalhlechelocal.get(i).getFecha_obtencao().replace(" ","%20"));
                        aux.put("id_historial_leche", "");

                        remotedb.ManutencaoHistorialLeche(aux,"HistorialLeche");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





        }




        for (int i = 0; i < totalhconsumolocal.size(); i++){

            boolean found = false;

            for (int j = 0; j < idgadolocal.size(); j++) {


                try {

                    JSONObject aux = new JSONObject();

                    aux.put("acao", "I");

                    for (int t = 0; t < idgadolocal.size(); t++) {

                        if (idgadolocal.get(t).equals(String.valueOf(totalhconsumolocal.get(i).getId_animal()))) {

                            aux.put("id_animal", idgadoremoto.get(j));
                        }
                    }
                    if (!found) {
                        aux.put("id_animal", String.valueOf(totalhconsumolocal.get(i).getId_animal()));
                    }

                    aux.put("id_producto", String.valueOf(totalhconsumolocal.get(i).getId_producto()));
                    aux.put("cantidad_consumida", String.valueOf(totalhconsumolocal.get(i).getCantidad_consumida()).replace(" ", "%20"));
                    aux.put("fecha_consumo", totalhconsumolocal.get(i).getFecha_consumo().replace(" ", "%20"));
                    aux.put("id_historial_consumo", "");

                    remotedb.ManutencaoHistorialConsumo(aux, "HistorialCosumo");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }



        for (int i = 0; i < totalhengordelocal.size(); i++){

            boolean found = false;

                    try {

                        JSONObject aux = new JSONObject();

                        aux.put("acao", "I");
                        for (int j = 0; j < idgadolocal.size(); j++){
                            if (idgadolocal.get(j).equals(String.valueOf(totalhengordelocal.get(i).getId_animal()))) {
                                aux.put("id_animal", idgadoremoto.get(j));
                                found = true;
                                break;
                            }
                        }

                        if(!found){
                            aux.put("id_animal", String.valueOf(totalhengordelocal.get(i).getId_animal()).replace(" ", "%20"));
                        }
                        aux.put("peso", String.valueOf(totalhengordelocal.get(i).getPeso()).replace(" ","%20"));
                        aux.put("fecha_medicion", totalhengordelocal.get(i).getFecha_medicion().replace(" ","%20"));
                        aux.put("id_historial_engorde", "");

                        remotedb.ManutencaoHistorialEngorde(aux,"HistorialEngorde");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




            localdb.DeleteInfoLoteGadoHistorial(id_user,"Criado");

        }


    }

    public void EnviarColheitas(){

        JSONObject aux;
        aux = new JSONObject();

        try {

            for (int i = 0; i < colheitalocal.size(); i++) {

                idcolheitalocal.add(String.valueOf(colheitalocal.get(i).getId_cosecha()));

                aux.put("acao", "I");
                aux.put("cosecha", colheitalocal.get(i).getNombre().replace(" ","%20"));
                aux.put("Id_cosecha", "" );
                aux.put("id_usuario",  String.valueOf(id_user) );

                remotedb.ManutencaoColheita(aux,"Colheita");
            }

            localdb.DeleteInfoColeita(id_user,"Criado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void EnviarSetor(){

        JSONObject aux;

        aux = new JSONObject();

        try {

        String nomesctor = "";
        ArrayList<String> latitude_strings;
        ArrayList<String> longitud_strings;

            if( coordendassector.size() == 0) {

                EnviarSafra();

            } else {


                for (int i = 0; i < coordendassector.size(); i++) {

                    idcoordendassectorlocal.add(String.valueOf(coordendassector.get(i).getSetor().getId_sector()));

                    if (!(coordendassector.get(i).getSetor().getNombre().equals(nomesctor))) {

                        nomesctor = coordendassector.get(i).getSetor().getNombre();
                        latitude_strings = new ArrayList<String>();
                        longitud_strings = new ArrayList<String>();

                        for (int j = 0; j < coordendassector.size(); j++) {

                            if (coordendassector.get(j).getSetor().getNombre().equals(nomesctor)) {

                                latitude_strings.add(coordendassector.get(j).getLatitude());
                                longitud_strings.add(coordendassector.get(j).getLongitude());

                            }
                        }

                        aux.put("acao", "I");
                        aux.put("id_usuario", String.valueOf(id_user));
                        aux.put("status", "activo");
                        aux.put("nombre", nomesctor.replace(" ", "%20"));
                        aux.put("hectareas", coordendassector.get(i).getSetor().getHectares());
                        aux.put("latitude", latitude_strings.toString().replace(" ", "").replace("[", "").replace("]", ""));
                        aux.put("longitude", longitud_strings.toString().replace(" ", "").replace("[", "").replace("]", ""));
                        aux.put("id_sector", "");

                        remotedb.ManutencaoSector(aux, "Setor");

                    }

                }


            }

        localdb.DeleteInfoSectorCoordendas(id_user,"Criado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void EnviarSafra(){

        JSONObject aux;

        aux = new JSONObject();

        try {

        for (int i = 0; i < safralocal.size(); i++) {

            String valuesector = safralocal.get(i).getId_sector().replace(" ","%20");
            String valuecolheita = safralocal.get(i).getId_cosecha().replace(" ","%20");

            for (int j = 0; j < idcolheitalocal.size(); j++){

                if(safralocal.get(i).getId_cosecha().replace(" ","%20").equals(idcolheitalocal.get(j).toString())){

                    valuecolheita = idcolheitaremoto.get(j).toString().replace(" ","%20");

                }

            }

            for (int k = 0; k < idcoordendassectorlocal.size(); k++){

                if(safralocal.get(i).getId_sector().replace(" ","%20").equals(idcoordendassectorlocal.get(k).toString())){

                    valuesector = idcoordendassectorremoto.get(k).toString().replace(" ","%20");

                }

            }


            aux.put("acao", "I");
            aux.put("sector", valuesector);
            aux.put("cosecha", valuecolheita);
            aux.put("inicio", safralocal.get(i).getInicio().replace(" ","%20"));
            aux.put("fim", safralocal.get(i).getFim().replace(" ","%20"));

            Log.d("sector", String.valueOf(safralocal.get(i).getId_sector()));
            Log.d("cosecha", String.valueOf(safralocal.get(i).getId_cosecha()));
            Log.d("inicio", String.valueOf(safralocal.get(i).getInicio()));
            Log.d("fim", String.valueOf(safralocal.get(i).getFim()));


            remotedb.ManutencaoCultivos(aux,null);

        }

        localdb.DeleteInfoCultivo(id_user,"X");

    } catch (JSONException e) {
        e.printStackTrace();
    }


    }



    public void AgregarColeita(){

        localdb.DeleteInfoColeita(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaColheita(aux,"Colheita");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarCliente(){

        localdb.DeleteInfoCliente(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaClientes(aux,"Cliente");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarMaquinaria(){

        localdb.DeleteInfoMaquinaria(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaMaquinarias(aux,"Maquinaria");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarEmpregado() {

        localdb.DeleteInfoEmpregado(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaEmpleados(aux,"Empregado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarProduto() {

        localdb.DeleteInfoProduto(id_user,"X");

        //Producto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaProducto(aux,"Produto");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarDespesa() {

        localdb.DeleteInfoDespesa(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("usuario", String.valueOf(id_user));
            remotedb.ConsultaDespesas(aux,"Despesa");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarPonto() {

        localdb.DeleteInfoPontoInteresse(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaPontosInteresse(aux,"Ponto");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarPremio() {

        localdb.DeleteInfoPremio(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaPremios(aux,"Premios");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void AgregarSector() {

        localdb.DeleteInfoSectorCoordendas(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaCoordenadas(aux,"Coordenadas");


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void AgregarVisita() {

        localdb.DeleteInfoVisita(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaVisitas(aux,"Visita");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void AgregarNegociacoes() {

        localdb.DeleteInfoNegociacoes(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaNegociacoes(aux,"Negociacao");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarTareas() {

        localdb.DeleteInfoTarefa(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaTareas(aux,"Tareas");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarSolo() {

        localdb.DeleteInfoSolo(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaSolo(aux,"Solo");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarAlertaPlaga() {

        localdb.DeleteInfoAlertaPraga(id_user,"X");

        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaAlertaPraga(aux,"AlertaPraga");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarLoteGado() {

        localdb.DeleteInfoLote(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaLotegado(aux, "LoteGado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarPraga() {

        localdb.DeleteInfoPraga(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();
        try {
            aux.put("plaga", "");

            remotedb.ConsultaPlagas(aux, "Praga");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarGado(int lote) {

        localdb.DeleteInfoGado(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("id_lote_gado",  String.valueOf(lote));

            remotedb.ConsultaGado(aux, "Gado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarHistorialEngorde(int id_animal) {

        localdb.DeleteInfoHistorialEngorde(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);
            remotedb.ConsultaHistorialEngorde(aux, "Engorde");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarHistorialLeche(int id_animal) {

        localdb.DeleteInfoHistorialLeite(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);
            remotedb.ConsultaHistorialLeche(aux, "Leche");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void AgregarHistorialConsumo(int id_animal) {

        localdb.DeleteInfoHistorialConsumo(id_user,"X");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);
            remotedb.ConsultaHistorialConsumo(aux, "Consumo");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void createFragments() {

        sincronizacaoFragment = SincronizacaoFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(sincronizacaoFragment);

        mViewPager.setAdapter(viewPagerAdapter);

        cargaInicial(MILISEGUNDOS_ESPERA);

    }

    public void cargaInicial(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                Log.d("espera","getCosechasLocal");
                //getCosechasLocal();
                getQuantidade();
            }
        }, milisegundos);
    }


    public void FuncaoDelayGado(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                gado();
            }
        }, milisegundos);
    }

    public void FuncaoDelayHistorial(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                Log.d("espera","getCosechasLocal");
                //getCosechasLocal();hi
                historial();
            }
        }, milisegundos);
    }



    public void getQuantidade() {

        //Local
        ArrayList<Quantidade> aux2 = localdb.getquantidade(id_user);
        sincronizacaoFragment.setData(aux2);

    }

    public void verificacaoUsuario2() {

        JSONObject aux = new JSONObject();

        try {

            aux.put("login", String.valueOf(login));

            ArrayRequest ar = new ArrayRequest(this, wsConsultaUsers, aux, "ConsultaAgregar");
            ar.makeRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void verificacaoUsuario() {

        JSONObject aux = new JSONObject();

        try {

            aux.put("login", String.valueOf(login));

            ArrayRequest ar = new ArrayRequest(this, wsConsultaUsers, aux, "ConsultaUsers");
            ar.makeRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void UpdateList(){
        sincronizacaoFragment.clearData();
        getQuantidade();
    }

    @Override
    public void onResume() {
        super.onResume();

        if ( V == "B"){
            UpdateList();
        }

        V = "B";

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

/*        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_botones, menu);*/

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        //adapter.setFilter(mCountryModel);
                        //cosechasFragment.filtro("");
                        return true; // Return true to collapse action view
                    }
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.setFilter(filteredModelList);
        sincronizacaoFragment.filtro(newText);
        return false;
    }


    private String encripta(String valor){
        String valor2 = valor;
        if(valor2==null){
            valor2="";
        }
        if(valor2.equals("")){
            return valor2;
        }
        String retorno="";
        try{
            while(true){
                String letra = valor2.substring(0,1);
                valor2 = valor2.substring(1);
                int letra2 = letra.hashCode();
                letra2 += 166;
                String letra3 = Integer.toString(letra2);
                while(true){
                    if(letra3.length()==3){
                        break;
                    }
                    letra3 = "0" + letra3;
                }
                retorno += letra3;
                if (valor2==""){
                    break;
                }
            }
        }catch(Exception ex){}
        return retorno;
    }

    private String decripta(String texto)
    {
        String retorno = "";
        String stexto = texto;
        if (stexto == "")
        {
            return stexto;
        }
        try{
            while (true)
            {
                String letra = stexto.substring(0, 3);
                int snumero = Integer.parseInt(letra);
                snumero -= 166;
                retorno += (char)snumero;
                stexto = stexto.substring(3);
                if (stexto == "")
                {
                    break;
                }
            }
        }catch(Exception ex){}
        return retorno;
    }


}
