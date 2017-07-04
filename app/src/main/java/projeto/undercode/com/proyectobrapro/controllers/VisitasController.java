package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
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
import projeto.undercode.com.proyectobrapro.forms.VisitaForm;
import projeto.undercode.com.proyectobrapro.fragments.VisitasFragment;
import projeto.undercode.com.proyectobrapro.fragments.VisitasTotalFragment;
import projeto.undercode.com.proyectobrapro.models.Visita;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 04/10/2016.
 */

public class VisitasController extends BaseController implements SearchView.OnQueryTextListener {

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
    //@BindView(R.id.fab_btnRua) FloatingActionButton fab_rua;

    public ArrayList<String> datalist = new ArrayList<String>();

    private VisitasFragment visitasFragment;
    private VisitasTotalFragment visitastotalFragment;
    @BindString(R.string.ManutencaoVisitas) String wsManutencaoVisitas;
    String V = "A";
    private String wsConsultaVisitas;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public VisitasFragment getVisitasFragment() { return this.visitasFragment; }
    public VisitasTotalFragment getVisitasTotalFragment() { return this.visitastotalFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_visita;
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
        mTabLayout = (TabLayout) findViewById(getmTabLayout());
        mViewPager = (ViewPager) findViewById(getmViewPager());


        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

/*
        fab_rua.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_rua.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));
*/

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), VisitaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

/*        fab_rua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> datalist  = new ArrayList<String>();
                ArrayList<Visita> datoVisita = visitasFragment.getData2();
                String[] stockArr = new String[datoVisita.size()];

                for (int j=0; j < datoVisita.size(); j++ ) {
                    datalist.add(String.valueOf(datoVisita.get(j).getId_visita()));
                }

                String asString = Arrays.toString(datalist.toArray(stockArr));

                Intent i2 = new Intent(getBaseContext(), Maparuta.class);
                i2.putExtra("datos",asString);
                startActivity(i2);
            }
        });*/


        wsConsultaVisitas = getWsConsultaVisitas();
        createFragments();
    }

    public void createFragments() {

        visitastotalFragment = VisitasTotalFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(visitastotalFragment);
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
                        getVisitas();
                        break ;
                    case (0):
                        getVisitasLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getVisitas() {


        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsConsultaVisitas, aux, "Visita");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getVisitasLocal() {
        //Local
        ArrayList<Visita> aux = localdb.ConsultaVisitas(id_user,"Criado");
        visitastotalFragment.setData(aux);
    }

    public void deleteVisita(Visita visita) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("usuario", "");
            aux.put("latitude", "");
            aux.put("longitude", "");
            aux.put("pim", "");
            aux.put("imei", "");
            aux.put("versao", "");
            aux.put("cliente", "");
            aux.put("motivo", "");
            aux.put("data_agenda", "");
            aux.put("data_visita", "");
            aux.put("resultado", "");
            aux.put("deslocamento", "");
            aux.put("situacao", "");
            aux.put("obs", "");
            aux.put("cadastrante", "");
            aux.put("id_visita", visita.getId_visita());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoVisitas(aux,"DeleteVisita");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoVisitas(aux);
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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate2 = df2.format(c.getTime()).replace(" ","");
        if (option == "Visita") {

        Visita p;
            Visita p2;

        ArrayList<Visita> aux = new ArrayList<Visita>();
            ArrayList<Visita> aux2 = new ArrayList<Visita>();

        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);

                    //String data = jo.getString("data_visita").replace(" ","");

/*                    if ( formattedDate2.equals(data) ) {

                        p2 = new Visita(
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

                        aux2.add(p2);
                    }*/



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
                    datalist.add(String.valueOf(p.getId_visita()));
                }
            } else {
                ToastMsg("Não tem visitas");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Datosaux",aux.toString());

            //visitasFragment.setData(aux2);
            visitastotalFragment.setData(aux);

        }

        if (option == "DeleteVisita") {
            Log.d("holi", "visita eliminada");
            UpdateList();
        }

    }


    public void UpdateList(){
        //visitasFragment.clearData();
        visitastotalFragment.clearData();
        switch (conn) {
            case (1):
            case (2):
                getVisitas();
                break ;
            case (0):
                getVisitasLocal();
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
        inflater.inflate(R.menu.menu_searchview2, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_hoje:
                visitastotalFragment.filtro("","hoje");
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.setFilter(filteredModelList);
        visitastotalFragment.filtro(newText,"total");
        return false;
    }

}

