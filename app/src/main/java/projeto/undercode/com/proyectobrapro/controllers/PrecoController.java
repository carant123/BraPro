package projeto.undercode.com.proyectobrapro.controllers;

import android.os.Bundle;
import android.os.Handler;
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
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.fragments.PrecoFragment;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.models.Preco;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 14/10/2016.
 */

public class PrecoController extends BaseController {


    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    private PrecoFragment precoFragment;

    private String wsPrecosConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public PrecoFragment getPrecoFragment() { return this.precoFragment; }

    @Override
    public int getLayout() {
        return  R.layout.activity_preco;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsPrecosConsult = getWsConsultaPrecos();
        createFragments();
    }

    public void createFragments() {

        precoFragment = PrecoFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(precoFragment);

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
                        getPreco();
                        break ;
                    case (0):
                        //getPragaLocal();
                        ToastMsg("precisa conexão à internet");
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getPreco() {

        ArrayRequest ar = new ArrayRequest(this, wsPrecosConsult, null, null);
        ar.makeRequest();
        // TODO: Go to getArrayResults() to get the JSON from DB

    }

    public void getPragaLocal() {
        //Local
        ArrayList<Preco> aux = localdb.ConsultaPrecos();
        precoFragment.setData(aux);
    }

    @Override
    public void getArrayResults(JSONArray response, String option){

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
}
