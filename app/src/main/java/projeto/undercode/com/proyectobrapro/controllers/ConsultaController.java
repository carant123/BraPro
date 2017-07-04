package projeto.undercode.com.proyectobrapro.controllers;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.fragments.CotacoesFragment;
import projeto.undercode.com.proyectobrapro.fragments.HistorialConsumoFragment;
import projeto.undercode.com.proyectobrapro.fragments.HistorialEngordeFragment;
import projeto.undercode.com.proyectobrapro.fragments.HistorialLecheFragment;
import projeto.undercode.com.proyectobrapro.fragments.PragasFragment;
import projeto.undercode.com.proyectobrapro.fragments.PrecoFragment;
import projeto.undercode.com.proyectobrapro.fragments.PremiosFragment;
import projeto.undercode.com.proyectobrapro.fragments.TaxasFragment;
import projeto.undercode.com.proyectobrapro.models.Cotacoe;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.models.Preco;
import projeto.undercode.com.proyectobrapro.models.Tax;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 22/02/2017.
 */

public class ConsultaController extends BaseController implements SearchView.OnQueryTextListener {

    private CotacoesFragment cotacoesFragment;
    private PrecoFragment precoFragment;
    private PragasFragment pragasFragment;
    private TaxasFragment taxasFragment;
    LocalDB localdb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    String V = "A";
    Bundle bundle;
    String tabSeleccion = "1";

    private String wsCotacoesConsult;
    private String wsPrecosConsult;
    private String wsConsultaTaxas;
    private String wsPlagasConsult;


    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    //public static MaterialViewPager mViewPager;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public CotacoesFragment getCotacoesFragment() { return this.cotacoesFragment; }
    public PrecoFragment getPrecoFragment() { return this.precoFragment; }
    public PragasFragment getPragasFragment() { return this.pragasFragment; }
    public TaxasFragment getTaxasFragment() { return this.taxasFragment; }

    @Override
    public int getLayout() {
        return R.layout.activity_consulta;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        bundle = getIntent().getExtras();
        V = "A";

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mTabLayout = (TabLayout) findViewById(getmTabLayout());
        //mViewPager = (MaterialViewPager) findViewById(getmViewPager());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsCotacoesConsult = getWsConsultaCotacoes();
        wsPrecosConsult = getWsConsultaPrecos();
        wsConsultaTaxas = getWsConsultaTaxas();
        wsPlagasConsult = getWsConsultaPlagas();


        createFragments();
    }

    public void createFragments() {


        cotacoesFragment = CotacoesFragment.newInstance();
        precoFragment = PrecoFragment.newInstance();
        pragasFragment = PragasFragment.newInstance();
        taxasFragment = TaxasFragment.newInstance();

        //getCotacoes();

        setSupportActionBar(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText("Cotacoes"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Preco"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Taxas"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Pragas"));


        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(cotacoesFragment);
        viewPagerAdapter.addFrag(precoFragment);
        viewPagerAdapter.addFrag(taxasFragment);
        viewPagerAdapter.addFrag(pragasFragment);

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
                    case 3:
                        tabSeleccion = "4";
                        UpdateList4();
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
                getCotacoes();
            }
        }, milisegundos);
    }


    public void getCotacoes() {


        switch (conn) {
            case (1):
            case (2):

                ArrayRequest ar = new ArrayRequest(this, wsCotacoesConsult, null, "Cotacoe");
                ar.makeRequest();

                break ;
            case (0):
                //getPragaLocal();
                ToastMsg("precisa conexão à internet");
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    public void getPreco() {



        switch (conn) {
            case (1):
            case (2):

                ArrayRequest ar = new ArrayRequest(this, wsPrecosConsult, null, "Preco");
                ar.makeRequest();
                // TODO: Go to getArrayResults() to get the JSON from DB

                break ;
            case (0):
                //getPragaLocal();
                ToastMsg("precisa conexão à internet");
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    public void getTaxas() {



        switch (conn) {
            case (1):
            case (2):

                ArrayRequest ar = new ArrayRequest(this, wsConsultaTaxas, null, "Taxa");
                ar.makeRequest();
                // TODO: Go to getArrayResults() to get the JSON from DB

                break ;
            case (0):
                //getPragaLocal();
                ToastMsg("precisa conexão à internet");
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    public void getPragas() {


        switch (conn) {
            case (1):
            case (2):

                JSONObject aux = new JSONObject();
                try {
                    aux.put("plaga", "");

                    ArrayRequest ar = new ArrayRequest(this, wsPlagasConsult, aux, "Praga");
                    ar.makeRequest();
                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break ;
            case (0):
                //getPragaLocal();
                ToastMsg("precisa conexão à internet");
                break;
            default:
                Log.d("conn", ""+conn); break;
        }


    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Cotacoe") {
            Cotacoe p;
            ArrayList<Cotacoe> aux = new ArrayList<Cotacoe>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Cotacoe(
                                jo.getInt("id_cotacoes"),
                                jo.getString("cosecha"),
                                jo.getString("periodo"),
                                jo.getString("cotacao"),
                                jo.getString("diferenca"),
                                jo.getString("fechamento"),
                                jo.getString("data_atualizacao")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem cotacoes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            cotacoesFragment.setData(aux);

        }

        if(option == "Preco"){
            Preco p;
            ArrayList<Preco> aux = new ArrayList<Preco>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Preco(
                                jo.getInt("id_precos"),
                                jo.getString("produto"),
                                jo.getString("ano"),
                                jo.getString("mes"),
                                jo.getString("dia"),
                                jo.getString("estado"),
                                jo.getInt("regiao"),
                                jo.getInt("preco_dolar"),
                                jo.getInt("preco_real"),
                                jo.getInt("taxa"),
                                jo.getString("mes_descricao"),
                                jo.getString("data_atualizacao")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem precos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            precoFragment.setData(aux);

        }

        if (option == "Taxa"){
            Tax p;
            ArrayList<Tax> aux = new ArrayList<Tax>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Tax(
                                jo.getInt("id_taxa"),
                                jo.getInt("ano"),
                                jo.getInt("mes"),
                                jo.getInt("dia"),
                                jo.getString("mes_descricao"),
                                jo.getInt("taxa"),
                                jo.getString("data_atualizacao")
                        );

                        Log.d("Valor",jo.toString());
                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem taxas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Datosaux",aux.toString());
            taxasFragment.setData(aux);

        }


        if (option == "Praga"){
            Praga p;
            ArrayList<Praga> aux = new ArrayList<Praga>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Praga(
                                jo.getInt("Id_plaga"),
                                jo.getString("Nombre"),
                                jo.getString("Caracteristicas"),
                                jo.getString("Sintomas"),
                                jo.getString("Tratamiento"),
                                jo.getString("Clase"),
                                jo.getString("Descripcion"),
                                jo.getString("Prevencion")
                        );
                        aux.add(p);

                    }
                } else {
                    ToastMsg("Não tem pragas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            pragasFragment.setData(aux);
        }




    }

    public void UpdateList(){
        cotacoesFragment.clearData();
        getCotacoes();
    }

    public void UpdateList2(){
        precoFragment.clearData();
        getPreco();
    }

    public void UpdateList3(){
        taxasFragment.clearData();
        getTaxas();
    }

    public void UpdateList4(){
        pragasFragment.clearData();
        getPragas();
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
                case "4":
                    UpdateList4();
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
                cotacoesFragment.filtro(newText);
                break;
            case "2":
                precoFragment.filtro(newText);
                break;
            case "3":
                taxasFragment.filtro(newText);
                break;
            case "4":
                pragasFragment.filtro(newText);
                break;

        }

        return false;
    }


}
