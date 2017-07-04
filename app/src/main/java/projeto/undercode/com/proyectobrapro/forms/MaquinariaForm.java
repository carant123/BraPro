package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 28/09/2016.
 */

public class MaquinariaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoMaquinarias) String wsManutencaoMaquinarias;

    @BindView(R.id.et_form_maquinaria_nome) EditText et_form_maquinaria_nome;
    @BindView(R.id.et_form_maquinaria_registro) EditText et_form_maquinaria_registro;
    @BindView(R.id.et_form_maquinaria_fecha_adqui) EditText et_form_maquinaria_fecha_adqui;
    @BindView(R.id.et_form_maquinaria_precio) EditText et_form_maquinaria_precio;
    @BindView(R.id.et_form_maquinaria_tipo) EditText et_form_maquinaria_tipo;
    @BindView(R.id.et_form_maquinaria_descripcion) EditText et_form_maquinaria_descripcion;
    @BindView(R.id.et_form_maquinaria_modelo) EditText et_form_maquinaria_modelo;
    @BindView(R.id.et_form_maquinaria_costo_mantenimiento) EditText et_form_maquinaria_costo_mantenimiento;
    @BindView(R.id.et_form_maquinaria_vida_util_horas) EditText et_form_maquinaria_vida_util_horas;
    @BindView(R.id.et_form_maquinaria_vida_util_ano) EditText et_form_maquinaria_vida_util_ano;
    @BindView(R.id.et_form_maquinaria_potencia_maquinaria) EditText et_form_maquinaria_potencia_maquinaria;

    @BindView(R.id.sp_form_maquinaria_tipo_adquisicion)
    Spinner sp_form_maquinaria_tipo_adquisicion;

    String Valor = "I";
    Bundle bundle;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    String optionstipo[] = {"Aluguel","Compra"};

    @BindView(R.id.bt_salvar_maquinaria) Button bt_salvar_maquinaria;
    @OnClick(R.id.bt_salvar_maquinaria)
    public void submit() {
        Log.d("holi", "MaquinariaForm");

        if (et_form_maquinaria_nome.getText().length()==0)
        {
            et_form_maquinaria_nome.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_registro.getText().length()==0)
        {
            et_form_maquinaria_registro.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_fecha_adqui.getText().length()==0)
        {
            et_form_maquinaria_fecha_adqui.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_precio.getText().length()==0)
        {
            et_form_maquinaria_precio.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_tipo.getText().length()==0)
        {
            et_form_maquinaria_tipo.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_descripcion.getText().length()==0)
        {
            et_form_maquinaria_descripcion.setError("O campo não pode ser deixado em branco.");

        }else if(et_form_maquinaria_modelo.getText().length()==0)
        {
            et_form_maquinaria_modelo.setError("O campo não pode ser deixado em branco.");

        }else if(et_form_maquinaria_costo_mantenimiento.getText().length()==0)
        {
            et_form_maquinaria_costo_mantenimiento.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_vida_util_horas.getText().length()==0)
        {
            et_form_maquinaria_vida_util_horas.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_vida_util_ano.getText().length()==0)
        {
            et_form_maquinaria_vida_util_ano.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_maquinaria_potencia_maquinaria.getText().length()==0)
        {
            et_form_maquinaria_potencia_maquinaria.setError("O campo não pode ser deixado em branco.");

        } else{

            if (Valor == "I"){
                GuardarMaquinaria();
            } else {
                editMaquinarias();
            }

        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_maquinarias_form;
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

        bundle = getIntent().getExtras();

        datepicker();
        getDatosInicial();

        bt_salvar_maquinaria.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }


    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_maquinaria_nome.setText(bundle.getString("Nombre"));
        et_form_maquinaria_registro.setText(bundle.getString("Registro"));
        et_form_maquinaria_fecha_adqui.setText(bundle.getString("Fecha_Adquisicion"));
        et_form_maquinaria_precio.setText(String.valueOf(bundle.getInt("Precio")));
        et_form_maquinaria_tipo.setText(bundle.getString("Tipo"));
        et_form_maquinaria_descripcion.setText(bundle.getString("Descripcion"));
        et_form_maquinaria_modelo.setText(bundle.getString("Modelo"));
        et_form_maquinaria_costo_mantenimiento.setText(String.valueOf(bundle.getInt("costo_mantenimiento")));
        et_form_maquinaria_vida_util_horas.setText(String.valueOf(bundle.getInt("vida_util_horas")));
        et_form_maquinaria_vida_util_ano.setText(String.valueOf(bundle.getInt("vida_util_ano")));
        et_form_maquinaria_potencia_maquinaria.setText(String.valueOf(bundle.getInt("potencia_maquinaria")));

        String valuetipoadquisicao = bundle.getString("tipo_adquisicion");
        int index_tipoadquisicao = -1;
        for (int i=0;i<optionstipo.length;i++) {
            if (optionstipo[i].equals(valuetipoadquisicao)) {
                index_tipoadquisicao = i;
                break;
            }
        }
        sp_form_maquinaria_tipo_adquisicion.setSelection(index_tipoadquisicao);

        bt_salvar_maquinaria.setText("Editar");

    }

    public void getDatosInicial(){

        ArrayAdapter<String> dataAdaptertipoadquisicion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionstipo);
        dataAdaptertipoadquisicion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_maquinaria_tipo_adquisicion.setAdapter(dataAdaptertipoadquisicion);

    }

    public void GuardarMaquinaria() {

        JSONObject aux = new JSONObject();
        try {


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_maquinaria_nome.getText().toString().replace(" ","%20"));
                    aux.put("registro", et_form_maquinaria_registro.getText().toString().replace(" ","%20"));
                    aux.put("fecha_adquisicion", et_form_maquinaria_fecha_adqui.getText().toString().replace(" ","%20"));
                    aux.put("precio", et_form_maquinaria_precio.getText().toString().replace(" ","%20"));
                    aux.put("tipo", et_form_maquinaria_tipo.getText().toString().replace(" ","%20"));
                    aux.put("descripcion", et_form_maquinaria_descripcion.getText().toString().replace(" ","%20"));
                    aux.put("modelo", et_form_maquinaria_modelo.getText().toString().replace(" ","%20"));
                    aux.put("Id_maquinaria", "");
                    aux.put("costo_mantenimiento", et_form_maquinaria_costo_mantenimiento.getText().toString().replace(" ","%20"));
                    aux.put("vida_util_horas", et_form_maquinaria_vida_util_horas.getText().toString().replace(" ","%20"));
                    aux.put("vida_util_ano", et_form_maquinaria_vida_util_ano.getText().toString().replace(" ","%20"));
                    aux.put("potencia_maquinaria", et_form_maquinaria_potencia_maquinaria.getText().toString().replace(" ","%20"));
                    aux.put("tipo_adquisicion", sp_form_maquinaria_tipo_adquisicion.getSelectedItem().toString().replace(" ","%20"));

                    remotedb.ManutencaoMaquinarias(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_maquinaria_nome.getText().toString());
                    aux.put("registro", et_form_maquinaria_registro.getText().toString());
                    aux.put("fecha_adquisicion", et_form_maquinaria_fecha_adqui.getText().toString());
                    aux.put("precio", et_form_maquinaria_precio.getText().toString());
                    aux.put("tipo", et_form_maquinaria_tipo.getText().toString());
                    aux.put("descripcion", et_form_maquinaria_descripcion.getText().toString());
                    aux.put("modelo", et_form_maquinaria_modelo.getText().toString());
                    aux.put("Id_maquinaria", "");
                    aux.put("costo_mantenimiento", et_form_maquinaria_costo_mantenimiento.getText().toString());
                    aux.put("vida_util_horas", et_form_maquinaria_vida_util_horas.getText().toString());
                    aux.put("vida_util_ano", et_form_maquinaria_vida_util_ano.getText().toString());
                    aux.put("potencia_maquinaria", et_form_maquinaria_potencia_maquinaria.getText().toString());
                    aux.put("tipo_adquisicion", sp_form_maquinaria_tipo_adquisicion.getSelectedItem().toString());

                    localdb.ManutencaoMaquinarias(aux);
                    ToastMsg("Feito local");
                    finish();
                    break;

                default:
                    Log.d("conn", ""+conn);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void editMaquinarias() {

        JSONObject aux2 = new JSONObject();

        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getString("Id_cosecha")));

        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_maquinaria_nome.getText().toString().replace(" ","%20"));
                    aux2.put("registro", et_form_maquinaria_registro.getText().toString().replace(" ","%20"));
                    aux2.put("fecha_adquisicion", et_form_maquinaria_fecha_adqui.getText().toString().replace(" ","%20"));
                    aux2.put("precio", et_form_maquinaria_precio.getText().toString().replace(" ","%20"));
                    aux2.put("tipo", et_form_maquinaria_tipo.getText().toString().replace(" ","%20"));
                    aux2.put("descripcion", et_form_maquinaria_descripcion.getText().toString().replace(" ","%20"));
                    aux2.put("modelo", et_form_maquinaria_modelo.getText().toString().replace(" ","%20"));
                    aux2.put("Id_maquinaria", String.valueOf(bundle.getInt("Id_maquinaria")));
                    aux2.put("costo_mantenimiento", et_form_maquinaria_costo_mantenimiento.getText().toString().replace(" ","%20"));
                    aux2.put("vida_util_horas", et_form_maquinaria_vida_util_horas.getText().toString().replace(" ","%20"));
                    aux2.put("vida_util_ano", et_form_maquinaria_vida_util_ano.getText().toString().replace(" ","%20"));
                    aux2.put("potencia_maquinaria", et_form_maquinaria_potencia_maquinaria.getText().toString().replace(" ","%20"));
                    aux2.put("tipo_adquisicion", sp_form_maquinaria_tipo_adquisicion.getSelectedItem().toString().replace(" ","%20"));

                    remotedb.ManutencaoMaquinarias(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_maquinaria_nome.getText().toString());
                    aux2.put("registro", et_form_maquinaria_registro.getText().toString());
                    aux2.put("fecha_adquisicion", et_form_maquinaria_fecha_adqui.getText().toString());
                    aux2.put("precio", et_form_maquinaria_precio.getText().toString());
                    aux2.put("tipo", et_form_maquinaria_tipo.getText().toString());
                    aux2.put("descripcion", et_form_maquinaria_descripcion.getText().toString());
                    aux2.put("modelo", et_form_maquinaria_modelo.getText().toString());
                    aux2.put("Id_maquinaria", String.valueOf(bundle.getInt("Id_maquinaria")));
                    aux2.put("costo_mantenimiento", et_form_maquinaria_costo_mantenimiento.getText().toString());
                    aux2.put("vida_util_horas", et_form_maquinaria_vida_util_horas.getText().toString());
                    aux2.put("vida_util_ano", et_form_maquinaria_vida_util_ano.getText().toString());
                    aux2.put("potencia_maquinaria", et_form_maquinaria_potencia_maquinaria.getText().toString());
                    aux2.put("tipo_adquisicion", sp_form_maquinaria_tipo_adquisicion.getSelectedItem().toString());

                    localdb.ManutencaoMaquinarias(aux2);
                    ToastMsg("Feito local");
                    finish();
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {
        Log.d("holi", "maquinaria salvada");
        finish();

    }

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_maquinaria_fecha_adqui.setText(sdf.format(myCalendar.getTime()));
    }

    public void datepicker() {

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        et_form_maquinaria_fecha_adqui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MaquinariaForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}
