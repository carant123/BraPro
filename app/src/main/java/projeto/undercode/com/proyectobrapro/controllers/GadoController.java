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
import projeto.undercode.com.proyectobrapro.fragments.GadoFragment;
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.models.LoteGado;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Level on 03/01/2017.
 */

public class GadoController extends BaseController implements SearchView.OnQueryTextListener {

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

    private GadoFragment lotegadoFragment;
    @BindString(R.string.ManutencaoLotegado) String wsManutencaoLotegado;
    String V = "A";
    private String wsLotegadoConsult;
    int id_usuario;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public GadoFragment getGadoFragment() { return this.lotegadoFragment; }



    @Override
    public int getLayout() {
        return  R.layout.activity_lote_gado;
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

/*        final GlobalVariables globalVariable = (GlobalVariables) getApplicationContext();
        id_usuario = globalVariable.getId_usuario();*/

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LoteGadoForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsLotegadoConsult = getWsConsultaLotegado();
        createFragments();
    }

    public void createFragments() {

        lotegadoFragment = GadoFragment.newInstance();


        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(lotegadoFragment);

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
                        getLoteGado();
                        break ;
                    case (0):
                        getLoteGadoLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getLoteGado() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            ArrayRequest ar = new ArrayRequest(this, wsLotegadoConsult, aux, "LoteGado");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getLoteGadoLocal() {

        //Local
        //ArrayList<LoteGado> aux = localdb.ConsultaLotegado(String.valueOf(id_user),"Criado");
        ArrayList<LoteGado> aux = localdb.ConsultaLotegado(String.valueOf(id_user),"");
        lotegadoFragment.setData(aux);

    }

    public void deleteLoteGado(LoteGado lotegado) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("nombre", "");
            aux.put("descripcao", "");
            aux.put("id_lote_gado", lotegado.getId_lote_gado());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoLotegado(aux,"DeleteLoteGado");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoLotegado(aux);
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
                    }
                } else {
                    ToastMsg("Não tem lotes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            lotegadoFragment.setData(aux);
        }

        if (option == "DeleteLoteGado") {
            Log.d("holi", "lote eliminada");
            UpdateList();
        }
    }


    public void UpdateList(){
        lotegadoFragment.clearData();
        switch (conn) {
            case (1):
            case (2):
                getLoteGado();
                break ;
            case (0):
                getLoteGadoLocal();
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
        lotegadoFragment.filtro(newText);
        return false;
    }
}
