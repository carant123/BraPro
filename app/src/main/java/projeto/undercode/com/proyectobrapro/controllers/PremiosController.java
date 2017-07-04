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
import projeto.undercode.com.proyectobrapro.forms.PremioForm;
import projeto.undercode.com.proyectobrapro.fragments.PremiosFragment;
import projeto.undercode.com.proyectobrapro.models.Premio;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 17/10/2016.
 */

public class PremiosController extends BaseController implements SearchView.OnQueryTextListener {

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

    private PremiosFragment premiosFragment;
    @BindString(R.string.ManutencaoPremios) String wsManutencaoPremios;
    String V = "A";
    private String wsPremiosConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public PremiosFragment getPremiosFragment() { return this.premiosFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_premio;
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
                Intent i = new Intent(getBaseContext(), PremioForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });


        wsPremiosConsult = getWsConsultaPremios();
        createFragments();
    }

    public void createFragments() {

        premiosFragment = PremiosFragment.newInstance();


        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(premiosFragment);

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
                        getPremios();
                        break ;
                    case (0):
                        getPremiosLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getPremios() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaPremios(aux,"Premios");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getPremiosLocal(){
        //Local
        ArrayList<Premio> aux = localdb.ConsultaPremios(id_user,"Criado");
        premiosFragment.setData(aux);
    }


    public void deletePremios(Premio premio) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("empleado", "");
            aux.put("premio", "");
            aux.put("mes_descricao", "");
            aux.put("data_atualizacao", "");
            aux.put("id_usuario", "");
            aux.put("id_premio", premio.getId_premio());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoPremios(aux,"DeletePremios");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoPremios(aux);
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
                } else {
                    ToastMsg("Não tem premios");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            premiosFragment.setData(aux);
        }

        if ( option == "DeletePremios"){
            UpdateList();
        }
    }

    public void UpdateList(){
        premiosFragment.clearData();
        switch (conn) {
            case (1):
            case (2):
                getPremios();
                break ;
            case (0):
                getPremiosLocal();
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
        premiosFragment.filtro(newText);
        return false;
    }
}