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
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.forms.ProdutoForm;
import projeto.undercode.com.proyectobrapro.fragments.ProdutosFragment;
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 17/10/2016.
 */

public class ProdutosController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    @BindView(R.id.menu) FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnAdd) FloatingActionButton fab_add;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;

    private ProdutosFragment productosFragment;
    @BindString(R.string.ManutencaoProductos) String wsManutencaoProductos;
    String V = "A";
    private String wsProductosConsult;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public ProdutosFragment getProductosFragment() { return this.productosFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_producto;
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

        V = "A";

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());


        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ProdutoForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsProductosConsult = getWsConsultaProducto();
        createFragments();
    }

    public void createFragments() {

        productosFragment = ProdutosFragment.newInstance();


        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(productosFragment);

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
                        getProducto();
                        break ;
                    case (0):
                        getProductoLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }

            }
        }, milisegundos);
    }

    public void getProducto() {

        //Producto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, wsProductosConsult, aux, "Produto");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getProductoLocal() {
        //Local
        ArrayList<Produto> aux = localdb.ConsultaProducto(id_user,"Criado");
        productosFragment.setData(aux);
    }

    public void deleteProducto(Produto produto) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("Id_tipo_producto", "");
            aux.put("Nombre", "");
            aux.put("Fecha_expiracion", "");
            aux.put("Funcion", "");
            aux.put("Descipcion", "");
            aux.put("Composicion", "");
            aux.put("Objeto", "");
            aux.put("Id_producto", produto.getId_producto());
            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("lote", "");
            aux.put("custo", "");
            aux.put("kilos", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoProductos(aux,"DeleteProducto");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoProductos(aux);
                    UpdateList();
                    break;
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

        if (option == "Produto")
        {

        Produto p;
        ArrayList<Produto> aux = new ArrayList<Produto>();
        JSONObject jo = null;

        try {
            if (response.length() > 0) {
                for (int i=0; i<response.length(); i++) {
                    jo = response.getJSONObject(i);


                    p = new Produto(
                            jo.getInt("Id_producto"),
                            jo.getInt("Id_tipo_producto"),
                            jo.getString("Nombre"),
                            jo.getString("N_TipoProducto"),
                            jo.getString("Fecha_registro"),
                            jo.getString("Fecha_expiracion"),
                            jo.getString("Funcion"),
                            jo.getString("Descipcion"),
                            jo.getString("Composicion"),
                            jo.getString("Objeto"),
                            jo.getString("Imagen"),
                            jo.getString("lote"),
                            jo.getString("custo"),
                            jo.getInt("id_usuario"),
                            jo.getString("kilos")

                    );

                    aux.add(p);
                }
            } else {
                ToastMsg("Não tem produtos");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            productosFragment.setData(aux);

        }

        if (option == "DeleteProducto") {
            Log.d("holi", "producto eliminada");
            UpdateList();
        }


    }


    public void UpdateList(){
        productosFragment.clearData();
        switch (conn) {
            case (1):
            case (2):
                getProducto();
                break ;
            case (0):
                getProductoLocal();
                break;
            default:
                Log.d("conn", ""+conn); break;
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
        productosFragment.filtro(newText);
        return false;
    }
}
