package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
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
import com.github.florent37.materialviewpager.MaterialViewPager;

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
import projeto.undercode.com.proyectobrapro.database.ScriptDB;
import projeto.undercode.com.proyectobrapro.forms.HistorialConsumoForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialEngordeForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialLecheForm;
import projeto.undercode.com.proyectobrapro.fragments.HistorialConsumoFragment;
import projeto.undercode.com.proyectobrapro.fragments.HistorialEngordeFragment;
import projeto.undercode.com.proyectobrapro.fragments.HistorialLecheFragment;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;
import projeto.undercode.com.proyectobrapro.models.HistorialEngorde;
import projeto.undercode.com.proyectobrapro.models.HistorialLeche;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialController extends BaseController implements SearchView.OnQueryTextListener {


    private HistorialEngordeFragment historialengordeFragment;
    private HistorialConsumoFragment historialconsumoFragment;
    private HistorialLecheFragment historiallecheFragment;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    @BindView(R.id.menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnAdd)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;

    @BindString(R.string.ManutencaoVisitas) String wsManutencaoVisitas;
    String V = "A";
    private String wsConsultaHistorialEngorde;
    private String wsConsultaHistorialLeche;
    private String wsConsultaHistorialConsumo;

    Bundle bundle;
    String tabSeleccion = "1";

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    //public static MaterialViewPager mViewPager;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public HistorialEngordeFragment getHistorialEngordeFragment() { return this.historialengordeFragment; }
    public HistorialConsumoFragment getHistorialConsumoFragment() { return this.historialconsumoFragment; }
    public HistorialLecheFragment getHistorialLecheFragment() { return this.historiallecheFragment; }

    @Override
    public int getLayout() {
        return  R.layout.activity_historial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        bundle = getIntent().getExtras();
        V = "A";

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mTabLayout = (TabLayout) findViewById(getmTabLayout());
        //mViewPager = (MaterialViewPager) findViewById(getmViewPager());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsConsultaHistorialEngorde = getWsConsultaHistorialEngorde();
        wsConsultaHistorialConsumo = getWsConsultaHistorialConsumo();
        wsConsultaHistorialLeche = getWsConsultaHistorialLeche();

        createFragments();

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_animal = bundle.getInt("id_animal");

                switch (tabSeleccion){
                    case "1":
                        Intent i1 = new Intent(getBaseContext(), HistorialEngordeForm.class);

                        i1.putExtra("acao", "I");
                        i1.putExtra("id_animal", id_animal);

                        startActivity(i1);
                        break;
                    case "2":
                        Intent i2 = new Intent(getBaseContext(), HistorialConsumoForm.class);

                        i2.putExtra("acao", "I");
                        i2.putExtra("id_animal", id_animal);

                        startActivity(i2);
                        break;
                    case "3":
                        Intent i3 = new Intent(getBaseContext(), HistorialLecheForm.class);

                        i3.putExtra("acao", "I");
                        i3.putExtra("id_animal", id_animal);

                        startActivity(i3);
                        break;
                }
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabSeleccion){
                    case "1":
                        UpdateList();
                        break;
                    case "2":
                        UpdateList2();
                        break;
                    case "3":
                        UpdateList3();
                        break;
                }
            }
        });



    }

    public void createFragments() {

        historialengordeFragment = HistorialEngordeFragment.newInstance();
        historialconsumoFragment = HistorialConsumoFragment.newInstance();
        historiallecheFragment = HistorialLecheFragment.newInstance();

        setSupportActionBar(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText("Engorde"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Consumo"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Leche"));

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(historialengordeFragment);
        viewPagerAdapter.addFrag(historialconsumoFragment);
        viewPagerAdapter.addFrag(historiallecheFragment);

        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        tabSeleccion = "1";
                        UpdateList();
                        break;
                    case 1:
                        tabSeleccion = "2";
                        UpdateList2();
                        break;
                    case 2:
                        tabSeleccion = "3";
                        UpdateList3();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

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
                        getHistorialEngorde();
                        break ;
                    case (0):
                        getHistorialEngordeLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }


    public void getHistorialEngorde() {

        int id_animal = bundle.getInt("id_animal");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);

            ArrayRequest ar = new ArrayRequest(this, wsConsultaHistorialEngorde, aux, "Engorde");
            ar.makeRequest();


            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getHistorialEngordeLocal() {

        int id_animal = bundle.getInt("id_animal");

        //Local
        //ArrayList<HistorialEngorde> aux = localdb.ConsultaHistorialEngorde(String.valueOf(id_animal),"Criado");
        ArrayList<HistorialEngorde> aux = localdb.ConsultaHistorialEngorde(String.valueOf(id_animal),"");
        historialengordeFragment.setData(aux);

    }

    public void getHistorialLeche() {

        int id_animal = bundle.getInt("id_animal");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);

            ArrayRequest ar = new ArrayRequest(this, wsConsultaHistorialLeche, aux, "Leche");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getHistorialLecheLocal() {

        int id_animal = bundle.getInt("id_animal");
        //Local
        //ArrayList<HistorialLeche> aux = localdb.ConsultaHistorialLeche(String.valueOf(id_animal),"Criado");
        ArrayList<HistorialLeche> aux = localdb.ConsultaHistorialLeche(String.valueOf(id_animal),"");
        historiallecheFragment.setData(aux);

    }


    public void getHistorialConsumo() {

        int id_animal = bundle.getInt("id_animal");

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_animal", id_animal);

            ArrayRequest ar = new ArrayRequest(this, wsConsultaHistorialConsumo, aux, "Consumo");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getHistorialConsumoLocal() {

        int id_animal = bundle.getInt("id_animal");

        //Local
        //ArrayList<HistorialConsumo> aux = localdb.ConsultaHistorialConsumo(String.valueOf(id_animal),"Criado");
        ArrayList<HistorialConsumo> aux = localdb.ConsultaHistorialConsumo(String.valueOf(id_animal),"");
        historialconsumoFragment.setData(aux);

    }


    public void deleteHistorialEngorde(HistorialEngorde historialengorde) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_animal", "");
            aux.put("peso", "");
            aux.put("fecha_medicion", "");
            aux.put("id_historial_engorde", historialengorde.getId_historial_engorde());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoHistorialEngorde(aux,"DeleteHistorialEngorde");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoHistorialEngorde(aux);
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


    public void deleteHistorialConsumo(HistorialConsumo historialconsumo) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_animal", "");
            aux.put("id_producto", "");
            aux.put("cantidad_consumida", "");
            aux.put("fecha_consumo", "");
            aux.put("id_historial_consumo", historialconsumo.getId_historial_consumo());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoHistorialConsumo(aux,"DeleteHistorialConsumo");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoHistorialConsumo(aux);
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


    public void deleteHistorialLeche(HistorialLeche historialleche) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_animal", "");
            aux.put("cantidad", "");
            aux.put("fecha_obtencao", "");
            aux.put("id_historial_leche", historialleche.getId_historial_leche());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoHistorialLeche(aux,"DeleteHistorialLeche");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoHistorialLeche(aux);
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

                        Log.d("holi engorde", aux.toString());
                    }
                } else {
                    ToastMsg("Não tem história de engorda");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            historialengordeFragment.setData(aux);
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

                        Log.d("holi leche", aux2.toString());
                    }
                } else {
                    ToastMsg("Não tem historia de leite");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("holi", "4");
            historiallecheFragment.setData(aux2);

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
                } else {
                    ToastMsg("Não tem historia de consumo");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            historialconsumoFragment.setData(aux3);
        }


        if (option == "DeleteHistorialEngorde") {
            UpdateList();
        }

        if (option == "DeleteHistorialConsumo") {
            UpdateList2();
        }

        if (option == "DeleteHistorialLeche") {
            UpdateList3();
        }


    }


    public void UpdateList(){
        historialengordeFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getHistorialEngorde();
                break ;
            case (0):
                getHistorialEngordeLocal();
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    public void UpdateList2(){

        historialconsumoFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getHistorialConsumo();
                break ;
            case (0):
                getHistorialConsumoLocal();
                break;
            default:
                Log.d("conn", ""+conn); break;
        }
    }

    public void UpdateList3(){

        historiallecheFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getHistorialLeche();
                break ;
            case (0):
                getHistorialLecheLocal();
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();


        if ( V == "B"){

            switch (tabSeleccion){
                case "1":
                    UpdateList();
                    break;
                case "2":
                    UpdateList2();
                    break;
                case "3":
                    UpdateList3();
                    break;
            }
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

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
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

        switch (tabSeleccion){
            case "1":
                historialengordeFragment.filtro(newText);
                break;
            case "2":
                historialconsumoFragment.filtro(newText);
                break;
            case "3":
                historiallecheFragment.filtro(newText);
                break;
        }

        return false;
    }



}
