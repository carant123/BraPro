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
import projeto.undercode.com.proyectobrapro.forms.DespesaForm;
import projeto.undercode.com.proyectobrapro.fragments.DespesaFragment;
import projeto.undercode.com.proyectobrapro.models.Despesa;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 17/10/2016.
 */

public class DespesasController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public static int MILISEGUNDOS_ESPERA = 1000;
    public int conn ;

    @BindView(R.id.menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnAdd)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;

    private DespesaFragment despesaFragment;
    @BindString(R.string.ManutencaoDespesas) String wsManutencaoDespesas;
    String V = "A";
    private String wsDespesasConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public DespesaFragment getDespesaFragment() { return this.despesaFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_despesa;
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
                Intent i = new Intent(getBaseContext(), DespesaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsDespesasConsult = getWsConsultaDespesas();
        createFragments();
    }

    public void createFragments() {

        despesaFragment = DespesaFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(despesaFragment);

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
                        getDespesa();
                        break ;
                    case (0):
                        getDespesaLocal();
                        ; break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getDespesa() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("usuario", String.valueOf(id_user));
            ArrayRequest ar = new ArrayRequest(this, wsDespesasConsult, aux, "Despesa");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getDespesaLocal() {

        //Local
        ArrayList<Despesa> aux = localdb.ConsultaDespesas(String.valueOf(id_user),"Criado");
        despesaFragment.setData(aux);

    }

    public void deleteDespesa(Despesa despesa) {

        JSONObject aux = new JSONObject();

        try {


            aux.put("acao", "D");
            aux.put("usuario", String.valueOf(id_user));
            aux.put("pim", "");
            aux.put("imei", "");
            aux.put("latitude", "");
            aux.put("longitude", "");
            aux.put("versao", "");
            aux.put("valor", "");
            aux.put("data_despesa", "");
            aux.put("id_despesa", despesa.getId_despesa());
            aux.put("tipo_despesa_tempo", "");
            aux.put("tipo_despesa", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoDespesas(aux,"DeleteDespesa");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoDespesas(aux);
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
                } else {
                    ToastMsg("Não tem despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            despesaFragment.setData(aux);
        }

        if (option == "DeleteDespesa") {

            UpdateList();

        }
    }



    public void UpdateList(){
        despesaFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getDespesa();
                break ;
            case (0):
                getDespesaLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
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
        despesaFragment.filtro(newText);
        return false;
    }
}
