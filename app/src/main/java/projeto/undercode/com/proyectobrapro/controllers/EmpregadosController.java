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
import projeto.undercode.com.proyectobrapro.forms.EmpregadoForm;
import projeto.undercode.com.proyectobrapro.fragments.EmpregadosFragment;
import projeto.undercode.com.proyectobrapro.models.Empregado;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;



/**
 * Created by Level on 23/09/2016.
 */

public class EmpregadosController extends BaseController implements SearchView.OnQueryTextListener {

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


    private EmpregadosFragment empleadosFragment;
    @BindString(R.string.ManutencaoEmpleados) String wsManutencaoEmpleados;
    String V = "A";
    private String wsEmpleadosConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public EmpregadosFragment getEmpleadosFragment() { return this.empleadosFragment; }



    @Override
    public int getLayout() {
        return  R.layout.activity_empleado;
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
                Intent i = new Intent(getBaseContext(), EmpregadoForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsEmpleadosConsult = getWsConsultaEmpleados();
        createFragments();
    }

    public void createFragments() {

        empleadosFragment = EmpregadosFragment.newInstance();

        setSupportActionBar(mToolbar);


        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(empleadosFragment);

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
                        getEmpleados();
                        break ;
                    case (0):
                        getEmpleadosLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }


    public void getEmpleados() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            ArrayRequest ar = new ArrayRequest(this, wsEmpleadosConsult, aux, "Empregado");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getEmpleadosLocal() {

        //Local
        ArrayList<Empregado> aux = localdb.ConsultaEmpleados(String.valueOf(id_user),"Criado");
        empleadosFragment.setData(aux);

    }


    public void deleteEmpleado(Empregado empregado) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_usuario", "");
            aux.put("nombre", "");
            aux.put("fecha_contratacion", "");
            aux.put("edad", "");
            aux.put("rol", "");
            aux.put("contacto", "");
            aux.put("Id_empleado",  empregado.getId_empleado());
            aux.put("Photo", "");
            aux.put("salario", "");
            aux.put("fin_de_contrato", "");
            aux.put("tipo_contrato", "");
            aux.put("N_tipo_contrato", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoEmpleados(aux,"DeleteEmpleado");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoEmpleados(aux);
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


        if (option == "Empregado")
        {
        Empregado p;
        ArrayList<Empregado> aux = new ArrayList<Empregado>();
        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);


                    p = new Empregado(
                            jo.getInt("Id_empleado"),
                            jo.getInt("Id_usuario"),
                            jo.getString("Nombre"),
                            jo.getString("Fecha_contratacion"),
                            jo.getInt("Edad"),
                            jo.getString("Rol"),
                            jo.getString("contacto"),
                            jo.getInt("salario"),
                            jo.getString("fin_de_contrato"),
                            jo.getInt("tipo_contrato"),
                            jo.getString("N_tipo_contrato")
                    );

                    aux.add(p);
                }
            } else {
                ToastMsg("Não tem empregados");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            empleadosFragment.setData(aux);

        }

        if (option == "DeleteEmpleado") {
            UpdateList();
        }

    }

    public void UpdateList(){
        empleadosFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getEmpleados();
                break ;
            case (0):
                getEmpleadosLocal();
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
        empleadosFragment.filtro(newText);
        return false;
    }



}

