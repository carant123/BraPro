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

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.forms.VentaGadoForm;
import projeto.undercode.com.proyectobrapro.fragments.VentaGadoFragment;
import projeto.undercode.com.proyectobrapro.models.VentaGado;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Level on 05/01/2017.
 */

public class VentaGadoController extends BaseController implements SearchView.OnQueryTextListener {

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

    private VentaGadoFragment ventagadofragment;
    String V = "A";
    Bundle bundle;
    private String wsVentaGadoConsult;
    int id_usuario;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public VentaGadoFragment getVentaGadoFragment() { return this.ventagadofragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_venta_gado;
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

        bundle = getIntent().getExtras();

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), VentaGadoForm.class);

                i.putExtra("id_usuario", id_usuario);
                i.putExtra("id_lote_gado", "");

                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
                //deleteInfoVentaGado();
            }
        });

        wsVentaGadoConsult = getWsConsultaVentaGado();
        createFragments();

    }

    public void createFragments() {

        ventagadofragment = VentaGadoFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(ventagadofragment);

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
                        getVentaGado();
                        break ;
                    case (0):
                        getVentaGadoLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }
            }
        }, milisegundos);
    }

    public void getVentaGado() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsVentaGadoConsult, aux, "VentaGado");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getVentaGadoLocal() {
        //Local
        ArrayList<VentaGado> aux = localdb.ConsultaVentaGado(id_user,"Criado");
        ventagadofragment.setData(aux);
    }

    public void deleteInfoVentaGado(){
        JSONObject aux = new JSONObject();

        try {


            aux.put("acao", "X");
            localdb.ManutencaoVentaGado(aux);
            UpdateList();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB
    }


    public void deleteVentaGado(VentaGado ventagado) {

        JSONObject aux = new JSONObject();

        try {


            aux.put("acao", "D");
            aux.put("precio","");
            aux.put("id_animal", ventagado.getId_animal());
            aux.put("fecha_venta", "");
            aux.put("id_venta_gado", ventagado.getId_venta_gado());
            aux.put("nome_gado", "");
            aux.put("id_usuario", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoVentaGado(aux,"DeleteVentaGado");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoVentaGado(aux);
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

        if ( option == "VentaGado") {
            VentaGado p;
            ArrayList<VentaGado> aux = new ArrayList<VentaGado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new VentaGado(
                                jo.getInt("id_venta_gado"),
                                jo.getInt("id_animal"),
                                jo.getString("nombre"),
                                jo.getInt("precio"),
                                jo.getInt("id_venta_gado_detalle"),
                                jo.getString("fecha_venta"),
                                jo.getInt("id_usuario")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem ventas de gados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ventagadofragment.setData(aux);
        }

        if (option == "DeleteVentaGado") {
            Log.d("holi", "lote eliminada");
        }
    }


    public void UpdateList(){
        ventagadofragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getVentaGado();
                break ;
            case (0):
                getVentaGadoLocal();
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
        ventagadofragment.filtro(newText);
        return false;
    }

}

