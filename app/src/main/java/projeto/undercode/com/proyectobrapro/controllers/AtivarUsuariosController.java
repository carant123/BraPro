package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

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
import projeto.undercode.com.proyectobrapro.forms.ClienteForm;
import projeto.undercode.com.proyectobrapro.fragments.AtivarUsuariosFragment;
import projeto.undercode.com.proyectobrapro.fragments.ClientesFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Usuario;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 03/03/2017.
 */

public class AtivarUsuariosController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;

    private AtivarUsuariosFragment ativarUsuariosFragment;

    @BindString(R.string.ManutencaoUsuarioAtivar) String wsManutencaoUsuarioAtivar;
    @BindString(R.string.ConsultaUsuarioAtivar) String wsConsultaUsuarioAtivar;
    String V = "A";

    private String wsAtivarUsaurioConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public AtivarUsuariosFragment getAtivarUsuariosFragment() { return this.ativarUsuariosFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_cliente;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        //wsAtivarUsaurioConsult = getWsConsultaClientes();
        createFragments();
    }

    public void createFragments() {

        ativarUsuariosFragment = AtivarUsuariosFragment.newInstance();

        //getUsuarios();

        getUsuariosLocal();

        setSupportActionBar(mToolbar);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(ativarUsuariosFragment);
        mViewPager.setAdapter(viewPagerAdapter);

    }

    public void getUsuarios() {

        ArrayRequest ar = new ArrayRequest(this, wsConsultaUsuarioAtivar, null, "Usuarios");
        ar.makeRequest();

    }

    public void getUsuariosLocal() {

        ArrayList<Usuario> aux = localdb.ConsultaUsuarioAtivar();
        ativarUsuariosFragment.setData(aux);

    }

    public void ativarUsuario(Usuario usuario) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "A");
            aux.put("codigo", String.valueOf(usuario.getCodigo()));

            StringsRequest ar = new StringsRequest(this, wsManutencaoUsuarioAtivar, aux, "Ativar");
            ar.makeRequest();




        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB

    }

    public void desativarUsuario(Usuario usuario) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "I");
            aux.put("codigo", String.valueOf(usuario.getCodigo()));

            StringsRequest ar = new StringsRequest(this, wsManutencaoUsuarioAtivar, aux, "Desativar");
            ar.makeRequest();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB

    }

    public void ativarUsuarioLocal(Usuario usuario) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "A");
            aux.put("codigo", String.valueOf(usuario.getCodigo()));

            localdb.ManutencaoUsuarioAtivar(aux);
            ToastMsg("Feito local");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB

    }

    public void desativarUsuarioLocal(Usuario usuario) {


        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "I");
            aux.put("codigo", String.valueOf(usuario.getCodigo()));

/*            //Remoto
            StringsRequest ar = new StringsRequest(this, wsManutencaoUsuarioAtivar, aux, "Desativar");
            ar.makeRequest();*/

            //Local
            localdb.ManutencaoUsuarioAtivar(aux);
            ToastMsg("Feito local");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: Go to getArrayResults() to get the JSON from DB

    }



    @Override
    public void getArrayResults(JSONArray response, String option){

        if (option == "Usuarios")
        {

            Usuario p;
            ArrayList<Usuario> aux = new ArrayList<Usuario>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Usuario(
                                jo.getString("codigo"),
                                jo.getString("nome"),
                                jo.getString("login"),
                                jo.getString("senha"),
                                jo.getString("email"),
                                jo.getString("status"),
                                jo.getString("perfil")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Não tem usuario inativo");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ativarUsuariosFragment.setData(aux);

        }

        if (option == "Ativar" || option == "Desativar") {
            UpdateList();
            Log.d("holi", "cliente eliminado");
        }

    }

    public void UpdateList(){
        ativarUsuariosFragment.clearData();
        //getUsuarios();
        getUsuariosLocal();
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

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.setFilter(filteredModelList);
        ativarUsuariosFragment.filtro(newText);
        return false;
    }



}
