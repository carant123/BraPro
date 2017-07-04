package projeto.undercode.com.proyectobrapro.controllers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.fragments.Balance2Fragment;
import projeto.undercode.com.proyectobrapro.models.Balance2;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 19/12/2016.
 */

public class BalanceController  extends BaseController {


    SharedPreferences prefs;
    String nome;
    int id_user;
    public int conn ;

    private Balance2Fragment balance2Fragment;
    private String wsBalanceConsult;

    public static ViewPager mViewPager;

    public int getmToolbar() {
        return R.id.toolbar;
    }
    public int getmViewPager() {
        return R.id.viewpager;
    }

    public Balance2Fragment getBalance2Fragment() {
        return this.balance2Fragment;
    }



    @Override
    public int getLayout() {
        return  R.layout.activity_balance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        //conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsBalanceConsult = getWsConsultaBalance();
        createFragments();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fb_activity_balance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        UpdateList2();
            }
        });


    }

    public void createFragments() {

        balance2Fragment = Balance2Fragment.newInstance();

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(balance2Fragment);

        mViewPager.setAdapter(viewPagerAdapter);

    }


    public void getBalance2(String data) {


        switch (conn) {
            case (1):
            case (2):

                JSONObject aux = new JSONObject();

                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("data_balance", data);
                    aux.put("tipo", "R");

                    ArrayRequest ar = new ArrayRequest(this, wsBalanceConsult, aux, "Balance2");
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

        if (option == "Balance2") {

            Balance2 p;
            ArrayList<Balance2> aux2 = new ArrayList<Balance2>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Balance2(
                                jo.getString("tipo_costo_fijo"),
                                jo.getString("valorR$"),
                                jo.getString("tipo_balance"),
                                jo.getString("id_usuario"),
                                jo.getString("data_balance")
                        );

                        aux2.add(p);
                    }
                } else {
                    ToastMsg("Adicionar um novo balance");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            balance2Fragment.setData(aux2);

        }

    }


    public void UpdateList2(){

        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        if ( balance2Fragment.EnviarData() != "") {
            balance2Fragment.clearData();
            getBalance2(balance2Fragment.EnviarData());
        }
    }

}
