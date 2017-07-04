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
import projeto.undercode.com.proyectobrapro.models.Empregado;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 21/12/2016.
 */

public class PremioForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoPremios) String wsManutencaoPremios;

    @BindView(R.id.sp_form_premio_empleado)
    Spinner sp_form_premio_empleado;

    @BindView(R.id.et_form_premio_data_actualizacao) EditText et_form_premio_data_actualizacao;
    @BindView(R.id.et_form_premio_descripcao) EditText et_form_premio_descripcao;
    @BindView(R.id.et_form_premio_premio) EditText et_form_premio_premio;


    String Valor = "I";
    Bundle bundle;

    @BindView(R.id.bt_salvar_premio)
    Button bt_salvar_premio;

    @OnClick(R.id.bt_salvar_premio)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_premio_data_actualizacao.getText().length()==0)
        {
            et_form_premio_data_actualizacao.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_premio_descripcao.getText().length()==0)
        {
            et_form_premio_descripcao.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_premio_premio.getText().length()==0)
        {
            et_form_premio_premio.setError("O campo não pode ser deixado em branco.");

        }  else {

            if (Valor == "I"){
                GuardarPremio();
            } else {
                EditPremio();
            }

        }

    }


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    List<String> options_indexes;


    @Override
    public int getLayout() {
        return R.layout.fragment_premios_form;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Empregado") {
            Empregado p;
            ArrayList<Empregado> aux = new ArrayList<Empregado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Empregado(
                                jo.getInt("Id_empleado"),
                                jo.getInt("Id_usuario"),
                                jo.getString("Nombre"),
                                jo.getString("Fecha_contratacion"),
                                jo.getInt("Edad"),
                                jo.getString("Rol"),
                                jo.getString("contacto"),
                                jo.getInt("salario"),
                                jo.getString("fin_de_contrato"),
                                jo.getInt("tipo_contrato"),
                                jo.getString("N_tipo_contrato")
                        );

                        aux.add(p);
                    }
                    updateEmpleadoArray(aux);
                } else {
                    ToastMsg("Nao tem empregados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

        switch (conn) {
            case (1):
                //Remoto
                getDatosInicial();
                break;
            case (2):
                //Remoto
                getDatosInicial();
                break ;
            case (0):
                //Local
                getDatosInicialLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }


        bt_salvar_premio.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }

    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_premio_premio.setText(String.valueOf(bundle.getInt("premio")));
        et_form_premio_data_actualizacao.setText(bundle.getString("data_atualizacao"));
        et_form_premio_descripcao.setText(String.valueOf(bundle.getString("mes_descricao")));

        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("id_premio")));
        bt_salvar_premio.setText("Editar");

    }


    public void GuardarPremio() {


        JSONObject aux = new JSONObject();
        try {

            String empleadoName = sp_form_premio_empleado.getSelectedItem().toString();
            String empleadoValue = options.get(empleadoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("empleado", String.valueOf(empleadoValue));
                    aux.put("premio", et_form_premio_premio.getText().toString().replace(" ","%20"));
                    aux.put("mes_descricao", et_form_premio_descripcao.getText().toString().replace(" ","%20"));
                    aux.put("data_atualizacao",  et_form_premio_data_actualizacao.getText().toString().replace(" ","%20"));
                    aux.put("id_usuario",  String.valueOf(id_user));
                    aux.put("id_premio",  "");

                    remotedb.ManutencaoPremios(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("empleado", String.valueOf(empleadoValue));
                    aux.put("premio", et_form_premio_premio.getText().toString());
                    aux.put("mes_descricao", et_form_premio_descripcao.getText().toString());
                    aux.put("data_atualizacao",  et_form_premio_data_actualizacao.getText().toString());
                    aux.put("id_usuario",  String.valueOf(id_user));
                    aux.put("id_premio",  "");

                    localdb.ManutencaoPremios(aux);
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


    public void EditPremio() {


        JSONObject aux = new JSONObject();
        try {

            String empleadoName = sp_form_premio_empleado.getSelectedItem().toString();
            String empleadoValue = options.get(empleadoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "E");
                    aux.put("empleado", String.valueOf(empleadoValue));
                    aux.put("premio", et_form_premio_premio.getText().toString().replace(" ","%20"));
                    aux.put("mes_descricao", et_form_premio_descripcao.getText().toString().replace(" ","%20"));
                    aux.put("data_atualizacao",  et_form_premio_data_actualizacao.getText().toString().replace(" ","%20"));
                    aux.put("id_usuario",   String.valueOf(bundle.getInt("id_usuario")));
                    aux.put("id_premio",  String.valueOf(bundle.getInt("id_premio")));


                    remotedb.ManutencaoPremios(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "E");
                    aux.put("empleado", String.valueOf(empleadoValue));
                    aux.put("premio", et_form_premio_premio.getText().toString());
                    aux.put("mes_descricao", et_form_premio_descripcao.getText().toString());
                    aux.put("data_atualizacao",  et_form_premio_data_actualizacao.getText().toString());
                    aux.put("id_usuario",   String.valueOf(bundle.getInt("id_usuario")));
                    aux.put("id_premio",  String.valueOf(bundle.getInt("id_premio")));

                    localdb.ManutencaoPremios(aux);
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



    public void updateEmpleadoArray(List<Empregado> Lp) {

        String[] optionsEmpleado  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_empleado()));
            optionsEmpleado[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterEmpleado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsEmpleado);
        dataAdapterEmpleado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_premio_empleado.setAdapter(dataAdapterEmpleado);

        if (bundle != null){

            int spinnerPosition = dataAdapterEmpleado.getPosition(bundle.getString("N_Empleado"));
            sp_form_premio_empleado.setSelection(spinnerPosition);

        }

    }


    public void getDatosInicial(){

        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            remotedb.ConsultaEmpleados(aux,"Empregado");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getDatosInicialLocal(){
        //Local
        ArrayList<Empregado> aux = localdb.ConsultaEmpleados(String.valueOf(id_user),"Cargado");
        updateEmpleadoArray(aux);
    }


    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_premio_data_actualizacao.setText(sdf.format(myCalendar.getTime()));

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

        et_form_premio_data_actualizacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PremioForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

}
