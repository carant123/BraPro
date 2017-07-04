package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

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
import projeto.undercode.com.proyectobrapro.forms.TarefaForm;
import projeto.undercode.com.proyectobrapro.fragments.TarefasFragment;
import projeto.undercode.com.proyectobrapro.models.Tarefa;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 07/12/2016.
 */

public class TarefasController extends BaseController implements SearchView.OnQueryTextListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;
    public static int MILISEGUNDOS_ESPERA = 1000;

    @BindView(R.id.menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnAdd)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_btnEnviarMensaje)
    FloatingActionButton fab_btnEnviarMensaje;
    @BindView(R.id.fab_btnRefresh) FloatingActionButton fab_refresh;

    String phoneNo;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    ArrayList<Tarefa> ArrayMensaje = new ArrayList<Tarefa>();

    private TarefasFragment tarefasFragment;
    @BindString(R.string.ManutencaoTareas) String wsManutencaoTareas;
    String V = "A";

    private String wsConsultaTareas;

    private Toolbar mToolbar;
    public static ViewPager mViewPager;

    public int getmToolbar() { return R.id.toolbar; }
    public int getmViewPager() { return R.id.viewpager; }

    public TarefasFragment gettarefasFragment() { return this.tarefasFragment; }


    @Override
    public int getLayout() {
        return  R.layout.activity_tarea;
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

        mToolbar = (Toolbar) findViewById(getmToolbar());
        mViewPager = (ViewPager) findViewById(getmViewPager());

        fab_add.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_add.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_refresh.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_refresh.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnEnviarMensaje.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));
        fab_btnEnviarMensaje.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarefasFragment.enviarMensaje();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TarefaForm.class);
                startActivity(i);
            }
        });

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        wsConsultaTareas = getWsConsultaTareas();
        createFragments();
    }

    public void createFragments() {

        tarefasFragment = TarefasFragment.newInstance();
        
        setSupportActionBar(mToolbar);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFrag(tarefasFragment);
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
                        getTareas();
                        break ;
                    case (0):
                        getTareasLocal();
                        break;
                    default:
                        Log.d("conn", ""+conn); break;
                }
            }
        }, milisegundos);
    }

    public void getTareas() {

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaTareas(aux,"Tareas");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getTareasLocal() {
        //Local
        ArrayList<Tarefa> aux = localdb.ConsultaTarefas(id_user,"Criado");
        tarefasFragment.setData(aux);
    }

    public void deleteTarea(Tarefa tarefa) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "D");
            aux.put("Id_usuario", "");
            aux.put("Id_producto", "");
            aux.put("Id_empleado", "");
            aux.put("Id_tipo_producto",  "");
            aux.put("Id_maquinaria",  "");
            aux.put("Id_tipo_tarea",  "");
            aux.put("Id_sector",  "");
            aux.put("Nombre", "");
            aux.put("Descripcion", "");
            aux.put("Fecha_trabajo", "");
            aux.put("Id_tarea", tarefa.getId_tarea());
            aux.put("horas_trabajadas", "");
            aux.put("hectareas_trabajadas", "");
            aux.put("cantidad_producto", "");


            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoTareas(aux,"DeleteTarea");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoTarefas(aux);
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

        if (option == "Tareas") {

            Tarefa p;
            ArrayList<Tarefa> aux = new ArrayList<Tarefa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Tarefa(
                                jo.getInt("Id_tarea"),
                                jo.getInt("Id_usuario"),
                                jo.getInt("Id_producto"),
                                jo.getString("Nombre_Producto"),
                                jo.getInt("Id_empleado"),
                                jo.getString("Nombre_Empleado"),
                                jo.getString("Contacto_Empleado"),
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre_Tipo_Producto"),
                                jo.getInt("Id_maquinaria"),
                                jo.getString("Nombre_Maquinaria"),
                                jo.getInt("Id_tipo_tarea"),
                                jo.getString("Nombre_Tipo_Tarea"),
                                jo.getInt("Id_sector"),
                                jo.getString("Nombre_Sector"),
                                jo.getString("Nombre_Tarea"),
                                jo.getString("Descripcion"),
                                jo.getString("Fecha_trabajo"),
                                jo.getInt("horas_trabajadas"),
                                jo.getInt("hectareas_trabajadas"),
                                jo.getInt("cantidad_producto")
                        );

                        Log.d("Valor", jo.toString());
                        aux.add(p);
                        ArrayMensaje.add(p);
                    }
                } else {
                    ToastMsg("Não tem tarefas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Datosaux", aux.toString());
            tarefasFragment.setData(aux);

        }

        if (option == "DeleteTarea") {
            Log.d("holi", "tarea eliminada");
            UpdateList();
        }

    }

    public void UpdateList(){
        tarefasFragment.clearData();

        switch (conn) {
            case (1):
            case (2):
                getTareas();
                break ;
            case (0):
                getTareasLocal();
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


    public void EnviarMensaje() {


        Log.d("ArrayMensaje: ", ArrayMensaje.toString());
        for (int i=0; i<ArrayMensaje.size(); i++ ) {

            Tarefa v = ArrayMensaje.get(i);

            String NumeroTelefno = v.getContacto_Empleado();
            String SMS = "Empregado: " + v.getNombre_Empleado() + "\n"+
                    "Trabajo: " + v.getNombre_Tarea() + "\n"+
                    "Tipo trabajo: " + v.getNombre_Tipo_Tarea() + "\n"+
                    "Descripcion: " + v.getDescripcion() + "\n"+
                    "Fecha: " + v.getFecha_trabajo() + "\n"+
                    "Maquinaria: " + v.getNombre_Maquinaria() + "\n"+
                    "Setor: " + v.getNombre_Sector() + "\n"+
                    "Produto: " + v.getNombre_Producto() + "\n"+
                    "Tipo producto: " + v.getNombre_Tipo_Producto();

            String SMS3 = "Empregado: " + v.getNombre_Empleado() + "\n"+
                    "Trabajo: " + v.getNombre_Tarea() + "\n"+
                    "Tipo trabajo: " + v.getNombre_Tipo_Tarea() + "\n"+
                    "Descripcion: " + v.getDescripcion() + "\n"+
                    "Setor: " + v.getNombre_Sector() + "\n"+
                    "Produto: " + v.getNombre_Producto() + "\n"+
                    "Tipo producto: " + v.getNombre_Tipo_Producto();

            Log.d("NumeroTelefno: ", v.getContacto_Empleado());
            Log.d("SMS: ", SMS);

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NumeroTelefno, null, SMS3, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

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
        tarefasFragment.filtro(newText);
        return false;
    }

}
