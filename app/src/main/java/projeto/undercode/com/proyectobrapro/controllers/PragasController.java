package projeto.undercode.com.proyectobrapro.controllers;

import android.content.SharedPreferences;
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
import projeto.undercode.com.proyectobrapro.fragments.PragasFragment;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 23/09/2016.
 */

public class PragasController extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    private PragasFragment plagasFragment;

    private String wsPlagasConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public PragasFragment getPlagasFragment() { return this.plagasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_plaga;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsPlagasConsult = getWsConsultaPlagas();
        createFragments();


    }

    public void createFragments() {

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        plagasFragment = PragasFragment.newInstance();

        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(plagasFragment);

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
                        getPraga();
                        break;
                    case (2):
                        getPraga();
                        break ;
                    case (0):
                        getPragaLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }


    public void getPraga() {

        //Remoto
        JSONObject aux = new JSONObject();
        try {
            aux.put("plaga", "");

            ArrayRequest ar = new ArrayRequest(this, wsPlagasConsult, aux, null);
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getPragaLocal() {
        //Local
        ArrayList<Praga> aux = localdb.ConsultaPlagas(String.valueOf(id_user));
        plagasFragment.setData(aux);
    }



        @Override
    public void getArrayResults(JSONArray response, String option){


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

            plagasFragment.setData(aux);

        }


    }


