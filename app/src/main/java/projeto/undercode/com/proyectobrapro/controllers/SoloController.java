package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import projeto.undercode.com.proyectobrapro.forms.SoloForm;
import projeto.undercode.com.proyectobrapro.fragments.SoloFragment;
import projeto.undercode.com.proyectobrapro.models.Solo;
import projeto.undercode.com.proyectobrapro.models.Solo2;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Level on 11/01/2017.
 */

public class SoloController extends BaseController {

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

    private SoloFragment soloFragment;
    @BindString(R.string.ManutencaoSolo) String wsManutencaoSolo;
    String V = "A";
    private String wsSoloConsult;
    int id_usuario;

    MenuItem item;
    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public SoloFragment getSoloFragment() { return this.soloFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_solo;
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
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SoloForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });


        wsSoloConsult = getWsConsultaSolo();
        createFragments();
    }

    public void createFragments() {

        soloFragment = SoloFragment.newInstance();
        setSupportActionBar(mToolbar);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(soloFragment);
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
                        getSolo();
                        break ;
                    case (0):
                        getSoloLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getSolo() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            ArrayRequest ar = new ArrayRequest(this, wsSoloConsult, aux, "Solo");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getSoloLocal() {
        //Local
        ArrayList<Solo2> aux = localdb.ConsultaSolo(id_user,"Criado");
        soloFragment.setData(aux);
    }


    public void deleteSolo(Solo2 solo) {

        JSONObject aux = new JSONObject();

        try {


            aux.put("acao", "D");
            aux.put("id_usuario", "");
            aux.put("id_sector", "");
            aux.put("fosforo_status", "");
            aux.put("fosforo_value", "");
            aux.put("potasio_status", "");
            aux.put("potasio_value", "");
            aux.put("calcio_status", "");
            aux.put("calcio_value", "");
            aux.put("magnesio_status", "");
            aux.put("magnesio_value", "");
            aux.put("alumninio_status", "");
            aux.put("alumninio_value", "");
            aux.put("material_organico_status", "");
            aux.put("material_organico_value", "");
            aux.put("hidrogeno_status", "");
            aux.put("hidrogeno_value", "");
            aux.put("potencial_hidrogenionico_status", "");
            aux.put("potencial_hidrogenionico_value", "");
            aux.put("id_consulta_solo2", solo.getId_consulta_solo());
            aux.put("data_consulta", "");


            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoSolo2(aux,"DeleteSolo");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoSolo(aux);
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

                        Log.d("solo", p.toString());

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem solos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            soloFragment.setData(aux);
        }

        if (option == "DeleteSolo") {
            Log.d("holi", "lote eliminada");
        }
    }


    public void UpdateList(){
        soloFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getSolo();
                break ;
            case (0):
                getSoloLocal();
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

}
