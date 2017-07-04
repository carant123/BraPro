package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.AdaptadorMenu;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.Menu;
import projeto.undercode.com.proyectobrapro.models.MenuModel;
import projeto.undercode.com.proyectobrapro.models.TipoContrato;
import projeto.undercode.com.proyectobrapro.models.TipoDespesa;
import projeto.undercode.com.proyectobrapro.models.TipoDespesaTempo;
import projeto.undercode.com.proyectobrapro.models.TipoProducto;
import projeto.undercode.com.proyectobrapro.models.TipoTarefa;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;





import android.preference.PreferenceManager;

/**
 * Created by Level on 13/07/2016.
 */
public class MenuController extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    ArrayList<MenuModel> menulocal;
    public int conn ;

    @Override
    public int getLayout() {
        return R.layout.menu_layout;
    }


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<MenuModel> items = new ArrayList<MenuModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotodb = new RemoteDB(this);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        //final GlobalVariables globalVariable = (GlobalVariables) getApplicationContext();
        //globalVariable.setId_usuario(id_user);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_menu);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        conn = RemoteDB.getConnectivityStatus(getApplicationContext());
        Log.d(" conn ", conn+"");

        switch (conn) {
            case (1):
            case (2):
                PrepareListitems();
                break ;
            case (0):
                PrepareListitemsLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

    }


    public void PrepareListitemsLocal(){

        menulocal = localdb.ConsultaMenu(id_user);

        funtionalidade(menulocal);

    }

    public void funtionalidade (final ArrayList<MenuModel> menu){

        mAdapter = new AdaptadorMenu(menu);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MenuModel modelomenu = (MenuModel) menu.get(position);

                try {
                    Class ourClass =
                            Class.forName("projeto.undercode.com.proyectobrapro.controllers." + modelomenu.getNombre() + "Controller");
                    Intent ourIntent = new Intent(MenuController.this, ourClass);
                    startActivity(ourIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

        if( option == "Menu") {

            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        items.add(new MenuModel(jo.getInt("id_menu"), jo.getString("nombre"),
                                jo.getString("imagen")));

                    }
                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            localdb.MenulistAdd(items);
            funtionalidade(items);

        }


        if (option == "TipoContrato") {

            TipoContrato p;
            List<TipoContrato> aux = new ArrayList<TipoContrato>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new TipoContrato(
                                jo.getInt("Id_tipo_contrato"),
                                jo.getString("Nombre")

                        );

                        aux.add(p);
                    }

                    localdb.TipoContratolistAdd(aux);

                } else {
                    ToastMsg("Nao tem tipo de contratos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "TipoDespesa") {

            TipoDespesa p;
            List<TipoDespesa> aux = new ArrayList<TipoDespesa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new TipoDespesa(
                                jo.getInt("Id_tipo_despesa"),
                                jo.getString("Nombre")
                        );

                        aux.add(p);
                    }

                    localdb.TipoDespesalistAdd(aux);

                } else {
                    ToastMsg("No tem tipo de despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (option == "TipoDespesaTempo") {

            TipoDespesaTempo p;
            List<TipoDespesaTempo> aux = new ArrayList<TipoDespesaTempo>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new TipoDespesaTempo(
                                jo.getInt("Id_tipo_despesa_tempo"),
                                jo.getString("Nombre")
                        );
                        aux.add(p);
                    }
                    localdb.TipoDespesaTempolistAdd(aux);
                } else {
                    ToastMsg("No tem tipo de tempo de despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "TipoProducto") {

            TipoProducto p;
            List<TipoProducto> aux = new ArrayList<TipoProducto>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new TipoProducto(
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre")
                        );

                        aux.add(p);

                    }

                    localdb.TipoProductolistAdd(aux);

                } else {
                    ToastMsg("Nao tem tipo de produtos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "Tipo_tarea") {

            TipoTarefa p;
            List<TipoTarefa> aux = new ArrayList<TipoTarefa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new TipoTarefa(
                                jo.getInt("Id_tipo_tarea"),
                                jo.getString("Nombre")
                        );


                        aux.add(p);
                    }

                    localdb.TipoTarealistAdd(aux);
                } else {
                    ToastMsg("Nao tem tipo de tarefas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    private void PrepareListitems() {

        remotodb.ConsultaTipoContrato(null,"TipoContrato");
        remotodb.ConsultaTipoDespesa(null,"TipoDespesa");
        remotodb.ConsultaTipoDespesaTempo(null,"TipoDespesaTempo");
        remotodb.ConsultaTipoProducto(null,"TipoProducto");
        remotodb.ConsultaTipoTarea(null,"Tipo_tarea");


        JSONObject aux = new JSONObject();
        try {
            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, getWsConsultaMenu(), aux, "Menu");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public interface ClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MenuController.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MenuController.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



    @Override
    public void onBackPressed() {



        new SweetAlertDialog(this)
                .setTitleText("Fechando")
                .setContentText("Tem certeza que deseja deslogar se?")
                .setCancelText("Nao")
                .setConfirmText("Sim")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        finish();
                    }
                })
                .show();


    }


}
