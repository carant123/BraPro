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
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.james.mime4j.codec.DecoderUtil;
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
import projeto.undercode.com.proyectobrapro.forms.ColheitaForm;
import projeto.undercode.com.proyectobrapro.fragments.ColheitasFragment;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 22/09/2016.
 */
public class ColheitasController extends BaseController implements SearchView.OnQueryTextListener {

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

    private ColheitasFragment cosechasFragment;
    @BindString(R.string.ManutencaoCosechas) String wsManutencaoCosecha;
    String V = "A";
    private String wsCosechasConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public ColheitasFragment getCosechasFragment() { return this.cosechasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_cosecha;
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
                Intent i = new Intent(getBaseContext(), ColheitaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsCosechasConsult = getWsConsultaCosechas();
        createFragments();
    }

    public void createFragments() {

        cosechasFragment = ColheitasFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(cosechasFragment);

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
                        getColheitas();
                        break ;
                    case (0):
                        getColheitasLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

                }
            }, milisegundos);
        }


    public void getColheitas() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaColheita(aux,"Colheita");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getColheitasLocal() {

        //Local
        ArrayList<Colheita> aux2 = localdb.ConsultaColheita(String.valueOf(id_user),"Criado");
        cosechasFragment.setData(aux2);

    }


    public void deleteCosechas(Colheita colheita) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("cosecha", "");
            aux.put("Id_cosecha", colheita.getId_cosecha());
            aux.put("id_usuario",  "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoColheita(aux,"DeleteCosecha");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoColheita(aux);
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

        if (option == "Colheita")
        {
            Colheita p;
            ArrayList<Colheita> aux = new ArrayList<Colheita>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Colheita(
                                jo.getInt("Id_cosecha"),
                                jo.getString("Nombre"),
                                jo.getInt("id_usuario")
                                );
                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem colheitas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            cosechasFragment.setData(aux);
        }

        if (option == "DeleteCosecha") {
            UpdateList();
        }


    }

    public void UpdateList(){
        cosechasFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getColheitas();
                break ;
            case (0):
                getColheitasLocal();
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

/*        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_botones, menu);*/

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                //adapter.setFilter(mCountryModel);
                //cosechasFragment.filtro("");
                return true; // Return true to collapse action view
            }
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                return true; // Return true to expand action view
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
        //adapter.setFilter(filteredModelList);
        cosechasFragment.filtro(newText);
        return false;
    }


}
