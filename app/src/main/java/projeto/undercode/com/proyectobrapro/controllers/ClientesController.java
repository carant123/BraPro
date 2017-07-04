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

//import com.github.clans.fab.FloatingActionButton;
//import com.github.clans.fab.FloatingActionMenu;

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
import projeto.undercode.com.proyectobrapro.forms.ClienteForm;
import projeto.undercode.com.proyectobrapro.fragments.ClientesFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 23/09/2016.
 */

public class ClientesController extends BaseController implements SearchView.OnQueryTextListener {

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

    private ClientesFragment clientesFragment;

    @BindString(R.string.ManutencaoClientes) String wsManutencaoClientes;
    String V = "A";

    private String wsClientesConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public ClientesFragment getClientesFragment() { return this.clientesFragment; }


    @Override
        public int getLayout() {
            return  R.layout.activity_cliente;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ClienteForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsClientesConsult = getWsConsultaClientes();
        createFragments();
    }

    public void createFragments() {

        clientesFragment = ClientesFragment.newInstance();

        setSupportActionBar(mToolbar);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(clientesFragment);
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
                        getClientes();
                        break ;
                    case (0):
                        getClientesLocal();
                        ; break;
                    default:
                        Log.d("conn", ""+conn); break;
                }


            }
        }, milisegundos);
    }

    public void getClientes() {

        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsClientesConsult, aux, "Cliente");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getClientesLocal() {

        ArrayList<Cliente> aux = localdb.ConsultaClientes(String.valueOf(id_user),"Criado");
        clientesFragment.setData(aux);

    }

    public void deleteCliente(Cliente cliente) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("id_usuario", "");
            aux.put("nombre", "");
            aux.put("organizacion", "");
            aux.put("numero", "");
            aux.put("direccion", "");
            aux.put("area", "");
            aux.put("Id_cliente", cliente.getId_cliente() );
            aux.put("cpf", "" );

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoColheita(aux,"DeleteCliente");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoClientes(aux);
                    UpdateList();
                    ; break;
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

        if (option == "Cliente")
        {

        Cliente p;
        ArrayList<Cliente> aux = new ArrayList<Cliente>();
        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);


                    p = new Cliente(
                            jo.getInt("id_cliente"),
                            jo.getInt("id_usuario"),
                            jo.getString("nombre"),
                            jo.getString("organizacion"),
                            jo.getString("numero"),
                            jo.getString("direccion"),
                            jo.getString("area"),
                            jo.getString("cpf"),
                            jo.getString("data_insercao")
                    );

                    aux.add(p);
                }
            } else {
                ToastMsg("Não tem clientes");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            clientesFragment.setData(aux);

        }

        if (option == "DeleteCliente") {

            UpdateList();

        }

    }

    public void UpdateList(){
        clientesFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getClientes();
                break ;
            case (0):
                getClientesLocal();
                ; break;
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
        clientesFragment.filtro(newText);
        return false;
    }



}
