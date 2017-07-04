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

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.forms.NegociacaoForm;
import projeto.undercode.com.proyectobrapro.fragments.NegociacoesFragment;
import projeto.undercode.com.proyectobrapro.models.Negociacoe;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 10/10/2016.
 */

public class NegociacoesController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    @BindView(R.id.menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnAdd)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;

    private NegociacoesFragment negociacoesFragment;
    @BindString(R.string.ManutencaoNegociacoes) String wsManutencaoNegociacoes;
    String V = "A";
    private String wsConsultaNegociacoes;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public NegociacoesFragment getNegociacoesFragment() { return this.negociacoesFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_negociacoes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), NegociacaoForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsConsultaNegociacoes = getWsConsultaNegociacoes();
        createFragments();
    }

    public void createFragments() {

        negociacoesFragment = NegociacoesFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(negociacoesFragment);

        mViewPager.setAdapter(viewPagerAdapter);

        cargaInicial(MILISEGUNDOS_ESPERA);

    }

    public void cargaInicial(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos

                switch (conn) {
                    case (1):
                    case (2):
                        getNegociacioes();
                        break ;
                    case (0):
                        getNegociacioesLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }


    public void getNegociacioes() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsConsultaNegociacoes, aux, "Negociacao");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getNegociacioesLocal() {

        //Local
        ArrayList<Negociacoe> aux = localdb.ConsultaNegociacoes(String.valueOf(id_user),"Criado");
        negociacoesFragment.setData(aux);

    }


    public void deleteNegociacoe(Negociacoe negociacoe) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("usuario", "");
            aux.put("pim", "");
            aux.put("imei", "");
            aux.put("latitude", "");
            aux.put("longitude", "");
            aux.put("versao", "");
            aux.put("cpf", "");
            aux.put("nome", "");
            aux.put("tipo_local", "");
            aux.put("local", "");
            aux.put("producto", "");
            aux.put("taxa", "");
            aux.put("valor_negociado", "");
            aux.put("data_pagamento", "");
            aux.put("data_cadastro", "");
            aux.put("id_negociacoes", negociacoe.getId_negociacoes());


            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoNegociacoes(aux,"DeleteNegociacoe");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoNegociacoes(aux);
                    UpdateList();
                    break;
                default:
                    Log.d("conn", ""+conn); break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB

    }

    @Override
    public void getArrayResults(JSONArray response, String option){

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

                    Log.d("Valor",jo.toString());
                    aux.add(p);
                }
            } else {
                ToastMsg("Não tem negociacoes");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
            Log.d("Datosaux",aux.toString());
            negociacoesFragment.setData(aux);

        }

        if (option == "DeleteNegociacoe") {
            Log.d("holi", "negociacao eliminada");
            UpdateList();
        }

    }


    public void UpdateList(){
        negociacoesFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getNegociacioes();
                break ;
            case (0):
                getNegociacioesLocal();
                break;
            default:
                Log.d("conn", ""+conn); break;
        }
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

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.setFilter(filteredModelList);
        negociacoesFragment.filtro(newText);
        return false;
    }


}

