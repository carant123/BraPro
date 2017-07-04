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
import projeto.undercode.com.proyectobrapro.forms.AlertaPragaForm;
import projeto.undercode.com.proyectobrapro.fragments.AlertaPragasFragment;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 30/09/2016.
 */

public class AlertaPragasController extends BaseController implements SearchView.OnQueryTextListener {

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

    private AlertaPragasFragment alertaPlagasFragment;
    @BindString(R.string.ManutencaoAlertaPlagas) String wsManutencaoAlertaPlagas;
    String V;
    private String wsAlertaPlagaConsult;

    /* Deben definirse en el onCreate, porque van después de que se infle la vista*/
    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public AlertaPragasFragment getAlertaPlagasFragment() { return this.alertaPlagasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_alertaplaga;
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

        V = "A";
        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AlertaPragaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsAlertaPlagaConsult = getWsConsultaAlertaPlagas();
        createFragments();
    }

    public void createFragments() {

        alertaPlagasFragment = AlertaPragasFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(alertaPlagasFragment);

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
                        getAlertaPlaga();
                        break ;
                    case (0):
                        getAlertaPlagaLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }


            }
        }, milisegundos);
    }

    public void getAlertaPlaga() {


        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaAlertaPraga(aux,"AlertaPraga");
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getAlertaPlagaLocal() {

        ArrayList<AlertaPraga> aux = localdb.ConsultaAlertaPlaga(id_user,"Criado");
        alertaPlagasFragment.setData(aux);

    }

    public void deleteAlertaPlaga(AlertaPraga alertaplaga) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("Id_sector", "");
            aux.put("Id_plaga", "");
            aux.put("Nombre", "");
            aux.put("Fecha_registro", "");
            aux.put("Descripcion", "");
            aux.put("Status", "");
            aux.put("Id_alerta_plaga", alertaplaga.getId_alerta_plaga() );
            aux.put("id_usuario", String.valueOf(id_user));

            localdb.ManutencaoAlertaPlaga(aux);
            UpdateList();

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoAlertaPragas(aux, "DeleteAlertaPlaga");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoAlertaPlaga(aux);
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
            } else {
                ToastMsg("Não tem alertas de pragas");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            alertaPlagasFragment.setData(aux);

        }

        if (option == "DeleteAlertaPlaga") {

            UpdateList();

        }


    }

    public void UpdateList(){
        alertaPlagasFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getAlertaPlaga();
                break ;
            case (0):
                getAlertaPlagaLocal();
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
        alertaPlagasFragment.filtro(newText);
        return false;
    }

}
