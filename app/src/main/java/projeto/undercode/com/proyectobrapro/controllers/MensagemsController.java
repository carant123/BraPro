package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ViewPagerAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.forms.MensagemForm;
import projeto.undercode.com.proyectobrapro.fragments.MensagemsFragment;
import projeto.undercode.com.proyectobrapro.models.Mensagem;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 21/10/2016.
 */

public class MensagemsController extends BaseController {


    SharedPreferences prefs;
    String nome;
    int id_user;

    private MensagemsFragment mensagemsFragment;

    private String wsMensagemConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public MensagemsFragment getMensagemsFragment() { return this.mensagemsFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_mensagem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        wsMensagemConsult = getWsConsultaMensagens();
        createFragments();
    }

    public void createFragments() {

        mensagemsFragment = MensagemsFragment.newInstance();

        getMensagems();
        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(mensagemsFragment);

        mViewPager.setAdapter(viewPagerAdapter);

    }

    public void getMensagems() {

        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsMensagemConsult, aux, null);
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option){

        Mensagem p;
        ArrayList<Mensagem> aux = new ArrayList<Mensagem>();
        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);


                    p = new Mensagem(
                            jo.getInt("id_mensagens"),
                            jo.getInt("usuario"),
                            jo.getString("assunto"),
                            jo.getString("mensagem"),
                            jo.getString("data_mensagem")
                    );




                    aux.add(p);
                }
            } else {
                ToastMsg("Não tem mensagems");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mensagemsFragment.setData(aux);

    }

    public void UpdateList(){
        mensagemsFragment.clearData();
        getMensagems();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_botones, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                Intent i = new Intent(this, MensagemForm.class);
                startActivity(i);
                break;

            case R.id.action_refresh:
                UpdateList();
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
