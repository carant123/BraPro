package projeto.undercode.com.proyectobrapro.controllers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.fragments.CotacoesFragment;
import projeto.undercode.com.proyectobrapro.models.Cotacoe;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 23/09/2016.
 */

public class CotacoesController extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    public int conn ;

    private CotacoesFragment cotacoesFragment;
    private String wsCotacoesConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public CotacoesFragment getCotacoesFragment() { return this.cotacoesFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_cotacoes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsCotacoesConsult = getWsConsultaCotacoes();
        createFragments();
    }

    public void createFragments() {

        cotacoesFragment = CotacoesFragment.newInstance();

        getCotacoes();
        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(cotacoesFragment);

        mViewPager.setAdapter(viewPagerAdapter);

    }

    public void getCotacoes() {



        switch (conn) {
            case (1):
            case (2):

                ArrayRequest ar = new ArrayRequest(this, wsCotacoesConsult, null, null);
                ar.makeRequest();
                // TODO: Go to getArrayResults() to get the JSON from DB

                break ;
            case (0):


                ToastMsg("precisa conexão à internet");

                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option){

        Cotacoe p;
        ArrayList<Cotacoe> aux = new ArrayList<Cotacoe>();
        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);


                    p = new Cotacoe(
                            jo.getInt("Id_cosecha"),
                            jo.getString("Nombre"),
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
}
