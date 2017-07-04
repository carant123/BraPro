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
import projeto.undercode.com.proyectobrapro.forms.LoteGadoForm;
import projeto.undercode.com.proyectobrapro.forms.MaquinariaForm;
import projeto.undercode.com.proyectobrapro.fragments.MaquinariasFragment;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 23/09/2016.
 */

public class MaquinariasController extends BaseController implements SearchView.OnQueryTextListener {

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

    private MaquinariasFragment maquinariasFragment;
    @BindString(R.string.ManutencaoMaquinarias) String wsManutencaoMaquinarias;
    String V = "A";
    private String wsMaquinariasConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public MaquinariasFragment getMaquinariasFragment() { return this.maquinariasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_maquinaria;
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
                Intent i = new Intent(getBaseContext(), MaquinariaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsMaquinariasConsult = getWsConsultaMaquinarias();
        createFragments();
    }

    public void createFragments() {

        maquinariasFragment = MaquinariasFragment.newInstance();


        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(maquinariasFragment);

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
                        getMaquinarias();
                        break ;
                    case (0):
                        getMaquinariasLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getMaquinarias() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaMaquinarias(aux,"Maquinaria");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getMaquinariasLocal() {

        //Local
        ArrayList<Maquinaria> aux = localdb.ConsultaMaquinarias(String.valueOf(id_user),"Criado");
        maquinariasFragment.setData(aux);

    }


    public void deleteMaquinarias(Maquinaria maquinaria) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_usuario", "");
            aux.put("nombre", "");
            aux.put("registro", "");
            aux.put("fecha_adquisicion", "");
            aux.put("precio", "");
            aux.put("tipo", "");
            aux.put("descripcion", "");
            aux.put("modelo", "");
            aux.put("Id_maquinaria", maquinaria.getId_maquinaria());
            aux.put("costo_mantenimiento", "");
            aux.put("vida_util_horas", "");
            aux.put("vida_util_ano", "");
            aux.put("potencia_maquinaria", "");
            aux.put("tipo_adquisicion", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoMaquinarias(aux,"DeleteMaquinaria");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoMaquinarias(aux);
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
            } else {
                ToastMsg("Não tem maquinarias");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            maquinariasFragment.setData(aux);
        }

        if (option == "DeleteMaquinaria") {
            Log.d("holi", "maquinaria eliminada");
            UpdateList();
        }


    }


    public void UpdateList(){
        maquinariasFragment.clearData();
        switch (conn) {
            case (1):
            case (2):
                getMaquinarias();
                break ;
            case (0):
                getMaquinariasLocal();
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
        maquinariasFragment.filtro(newText);
        return false;
    }

}
