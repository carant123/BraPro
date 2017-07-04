package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.forms.VisitaForm;
import projeto.undercode.com.proyectobrapro.fragments.TaxasFragment;
import projeto.undercode.com.proyectobrapro.fragments.VisitasFragment;
import projeto.undercode.com.proyectobrapro.models.Tax;
import projeto.undercode.com.proyectobrapro.models.Visita;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 13/10/2016.
 */

public class TaxasController extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    public int conn ;

    private TaxasFragment taxasFragment;

    private String wsConsultaTaxas;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public TaxasFragment getTaxasFragment() { return this.taxasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_tax;
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

        wsConsultaTaxas = getWsConsultaTaxas();
        createFragments();
    }

    public void createFragments() {

        taxasFragment = TaxasFragment.newInstance();

        getTaxas();
        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(taxasFragment);
        mViewPager.setAdapter(viewPagerAdapter);

    }

    public void getTaxas() {



        switch (conn) {
            case (1):
            case (2):

                ArrayRequest ar = new ArrayRequest(this, wsConsultaTaxas, null, null);
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


}
