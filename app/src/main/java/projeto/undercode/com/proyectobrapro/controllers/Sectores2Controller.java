package projeto.undercode.com.proyectobrapro.controllers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.fragments.MapaSectoresFragment;
import projeto.undercode.com.proyectobrapro.fragments.SetoresFragment;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 22/09/2016.
 */
public class Sectores2Controller extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;

    private SetoresFragment sectoresFragment;
    private MapaSectoresFragment mapaSectoresFragment;

    private String wsConsultaSectores;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmTabLayout() { return R.id.tab_layout; }
    public int getmViewPager() { return R.id.viewpager; }

    public SetoresFragment getSectoresFragment() { return this.sectoresFragment; }
    public MapaSectoresFragment getMapaSectoresFragment() { return this.mapaSectoresFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_manage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);


        mToolbar = (Toolbar) findViewById(getmToolbar());
        mTabLayout = (TabLayout) findViewById(getmTabLayout());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsConsultaSectores = getWsConsultaSectores();
        createFragments();
    }

    public void createFragments() {

        sectoresFragment = SetoresFragment.newInstance();
        mapaSectoresFragment = MapaSectoresFragment.newInstance();

        getSectores();
        setSupportActionBar(mToolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText("Sectores"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Mapa"));

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(sectoresFragment);
        viewPagerAdapter.addFrag(mapaSectoresFragment);


        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    public void getSectores() {


        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            ArrayRequest ar = new ArrayRequest(this, wsConsultaSectores, aux, "Sectores");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getArrayResults(JSONArray response, String option){


            Setor p;
            ArrayList<Setor> aux = new ArrayList<Setor>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Setor(
                                jo.getString("id_sector"),
                                jo.getString("id_usuario"),
                                jo.getString("Id_cultivo"),
                                jo.getString("status"),
                                jo.getString("Nombre"),
                                jo.getString("Hectareas")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem sectores");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            sectoresFragment.setData(aux);
        }


}
