package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.TipoContrato;
import projeto.undercode.com.proyectobrapro.models.TipoDespesa;
import projeto.undercode.com.proyectobrapro.models.TipoDespesaTempo;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 27/09/2016.
 */

public class EmpregadoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;

    @BindString(R.string.ManutencaoEmpleados) String wsManutencaoEmpleados;

    @BindView(R.id.et_form_empleado_nome) EditText et_form_empleado_nome;
    @BindView(R.id.et_form_empleado_fecha_contrata) EditText et_form_empleado_fecha_contrata;
    @BindView(R.id.et_form_empleado_edad) EditText et_form_empleado_edad;
    @BindView(R.id.et_form_empleado_rol) EditText et_form_empleado_rol;
    @BindView(R.id.et_form_empleado_contacto) EditText et_form_empleado_contacto;
    @BindView(R.id.et_form_empleado_salario) EditText et_form_empleado_salario;
    @BindView(R.id.et_form_empleado_fin_de_contrato) EditText et_form_empleado_fin_de_contrato;


    @BindView(R.id.sp_form_empleado_tipo_contrato)
    Spinner sp_form_empleado_tipo_contrato;

    String Valor = "I";
    Bundle bundle;

    Intent i;

    @BindView(R.id.bt_salvar_empleado) Button bt_salvar_empleado;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener date2;

    HashMap<String,String> options;
    List<String> options_indexes;

    @OnClick(R.id.bt_salvar_empleado)
    public void submit() {
        Log.d("holi", "EmpregadoForm");

        if (et_form_empleado_nome.getText().length()==0)
        {
            et_form_empleado_nome.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_empleado_fecha_contrata.getText().length()==0)
        {
            et_form_empleado_fecha_contrata.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_empleado_edad.getText().length()==0)
        {
            et_form_empleado_edad.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_empleado_rol.getText().length()==0)
        {
            et_form_empleado_rol.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_empleado_contacto.getText().length()==0)
        {
            et_form_empleado_contacto.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_empleado_salario.getText().length()==0)
        {
            et_form_empleado_salario.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_empleado_fin_de_contrato.getText().length()==0)
        {
            et_form_empleado_fin_de_contrato.setError("O campo não pode ser deixado em branco.");

        }  else {

            if (Valor == "I"){
                GuardarEmpleado();
            } else {
                EditarEmpleado();
            }

        }


    }

    @Override
    public int getLayout() {
        return R.layout.fragment_empleados_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotodb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        bundle = getIntent().getExtras();

        datepicker();
        getDatosInicial();

        bt_salvar_empleado.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }


    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_empleado_nome.setText(bundle.getString("nombre"));
        et_form_empleado_fecha_contrata.setText(bundle.getString("fecha_contratacion"));
        et_form_empleado_edad.setText(String.valueOf(bundle.getInt("edad")));
        et_form_empleado_rol.setText(bundle.getString("rol"));
        et_form_empleado_contacto.setText(bundle.getString("contacto"));
        et_form_empleado_salario.setText(String.valueOf(bundle.getInt("salario")));
        et_form_empleado_fin_de_contrato.setText(bundle.getString("fin_de_contrato"));


        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("Id_empleado")));
        bt_salvar_empleado.setText("Editar");

    }


    public void getDatosInicial(){

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                remotodb.ConsultaTipoContrato(null,"TipoContrato");
                break;
            case (0):
                //Local
                ArrayList<TipoContrato> aux = localdb.ConsultaTipoContrato();
                updateTipoContratoArray(aux);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

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
                    updateTipoContratoArray(aux);
                } else {
                    ToastMsg("Nao tem tipo de contratos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            Log.d("holi", "empleado guardado");
            finish();

        }

    }



    public void updateTipoContratoArray(List<TipoContrato> Lp) {

        String[] optionsTipoContrato  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_contrato()));
            optionsTipoContrato[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoContrato = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoContrato);
        dataAdapterTipoContrato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_empleado_tipo_contrato.setAdapter(dataAdapterTipoContrato);

        if (bundle != null){

            int spinnerPosition = dataAdapterTipoContrato.getPosition(bundle.getString("N_tipo_contrato"));
            sp_form_empleado_tipo_contrato.setSelection(spinnerPosition);

        }

    }

    public void GuardarEmpleado() {

        JSONObject aux = new JSONObject();
        try {


            String tipo_contratoName = sp_form_empleado_tipo_contrato.getSelectedItem().toString();
            String tipo_contratoNameValue = options.get(tipo_contratoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_empleado_nome.getText().toString().replace(" ","%20"));
                    aux.put("fecha_contratacion", et_form_empleado_fecha_contrata.getText().toString().replace(" ","%20"));
                    aux.put("edad", et_form_empleado_edad.getText().toString().replace(" ","%20"));
                    aux.put("rol", et_form_empleado_rol.getText().toString().replace(" ","%20"));
                    aux.put("contacto", et_form_empleado_contacto.getText().toString().replace(" ","%20"));
                    aux.put("Id_empleado", "");
                    aux.put("Photo", "");
                    aux.put("salario", et_form_empleado_salario.getText().toString().replace(" ","%20"));
                    aux.put("fin_de_contrato", et_form_empleado_fin_de_contrato.getText().toString().replace(" ","%20"));
                    aux.put("tipo_contrato", String.valueOf(tipo_contratoNameValue));

                    remotodb.ManutencaoEmpleados(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_empleado_nome.getText().toString());
                    aux.put("fecha_contratacion", et_form_empleado_fecha_contrata.getText().toString());
                    aux.put("edad", et_form_empleado_edad.getText().toString());
                    aux.put("rol", et_form_empleado_rol.getText().toString());
                    aux.put("contacto", et_form_empleado_contacto.getText().toString());
                    aux.put("Id_empleado", "");
                    aux.put("Photo", "");
                    aux.put("salario", et_form_empleado_salario.getText().toString());
                    aux.put("fin_de_contrato", et_form_empleado_fin_de_contrato.getText().toString());
                    aux.put("tipo_contrato", String.valueOf(tipo_contratoNameValue));


                    localdb.ManutencaoEmpleados(aux);
                    ToastMsg("Feito local");
                    finish();
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }

            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void EditarEmpleado() {

        JSONObject aux2 = new JSONObject();
        try {

            String tipo_contratoName = sp_form_empleado_tipo_contrato.getSelectedItem().toString();
            String tipo_contratoNameValue = options.get(tipo_contratoName);


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_empleado_nome.getText().toString().replace(" ","%20"));
                    aux2.put("fecha_contratacion", et_form_empleado_fecha_contrata.getText().toString().replace(" ","%20"));
                    aux2.put("edad", et_form_empleado_edad.getText().toString().replace(" ","%20"));
                    aux2.put("rol", et_form_empleado_rol.getText().toString().replace(" ","%20"));
                    aux2.put("contacto", et_form_empleado_contacto.getText().toString().replace(" ","%20"));
                    aux2.put("Id_empleado", String.valueOf(bundle.getInt("Id_empleado")));
                    aux2.put("Photo", "");
                    aux2.put("salario", et_form_empleado_salario.getText().toString().replace(" ","%20"));
                    aux2.put("fin_de_contrato", et_form_empleado_fin_de_contrato.getText().toString().replace(" ","%20"));
                    aux2.put("tipo_contrato", String.valueOf(tipo_contratoNameValue));

                    remotodb.ManutencaoEmpleados(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_empleado_nome.getText().toString());
                    aux2.put("fecha_contratacion", et_form_empleado_fecha_contrata.getText().toString());
                    aux2.put("edad", et_form_empleado_edad.getText().toString());
                    aux2.put("rol", et_form_empleado_rol.getText().toString());
                    aux2.put("contacto", et_form_empleado_contacto.getText().toString());
                    aux2.put("Id_empleado", String.valueOf(bundle.getInt("Id_empleado")));
                    aux2.put("Photo", "");
                    aux2.put("salario", et_form_empleado_salario.getText().toString());
                    aux2.put("fin_de_contrato", et_form_empleado_fin_de_contrato.getText().toString());
                    aux2.put("tipo_contrato", String.valueOf(tipo_contratoNameValue));

                    localdb.ManutencaoEmpleados(aux2);
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




    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_empleado_fecha_contrata.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabel2() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_empleado_fin_de_contrato.setText(sdf.format(myCalendar2.getTime()));
    }

    public void datepicker() {

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();


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

        et_form_empleado_fecha_contrata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EmpregadoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        et_form_empleado_fin_de_contrato.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EmpregadoForm.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }


}
