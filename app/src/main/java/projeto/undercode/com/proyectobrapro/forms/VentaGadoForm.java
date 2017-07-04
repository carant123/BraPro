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

import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Level on 05/01/2017.
 */

public class VentaGadoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindView(R.id.et_form_venta_gado_fecha) EditText et_form_venta_gado_fecha;
    @BindView(R.id.et_form_venta_gado_precio) EditText et_form_venta_gado_precio;

    @BindView(R.id.sp_form_venta_gado_animal)
    Spinner sp_form_venta_gado_animal;

    @BindView(R.id.bt_salvar_venta_gado)
    Button bt_salvar_venta_gado;

    String Valor = "I";
    Bundle bundle;
    int id_usuario;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    List<String> options_indexes;

    @OnClick(R.id.bt_salvar_venta_gado)
    public void submit() {
        Log.d("holi", "ColheitaForm");

        if (et_form_venta_gado_fecha.getText().length()==0)
        {
            et_form_venta_gado_fecha.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_venta_gado_precio.getText().length()==0)
        {
            et_form_venta_gado_precio.setError("O campo não pode ser deixado em branco.");

        }  else {
            if (Valor == "I"){
                getHistorialConsumo();
            } else {
                editHistorialConsumo();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_venta_gado_form;
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

        datepicker();

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                getDatosInicial();
                break;

            case (0):
                //Local
                getDatosInicialLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

        bt_salvar_venta_gado.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }

        Log.d("holi", "Datos de Valor: " + String.valueOf(Valor));

    }


    public void getDatos(){
/*        Valor = bundle.getString("acao");
        et_cosechanome.setText(bundle.getString("cosecha"));
        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("Id_cosecha")));
        bt_salvarcosecha.setText("Editar");*/

    }



    public void getHistorialConsumo() {

        JSONObject aux = new JSONObject();
        try {

            String gadoName = sp_form_venta_gado_animal.getSelectedItem().toString();
            String gadoValue = options.get(gadoName);

            int id_animal = bundle.getInt("id_animal");


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("precio", et_form_venta_gado_precio.getText().toString().replace(" ","%20"));
                    aux.put("id_animal", String.valueOf(gadoValue));
                    aux.put("fecha_venta", et_form_venta_gado_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_venta_gado", "");
                    aux.put("nome_gado", sp_form_venta_gado_animal.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("id_usuario", String.valueOf(id_user));

                    remotedb.ManutencaoVentaGado(aux,"InsertVentaGado");
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("precio", et_form_venta_gado_precio.getText().toString());
                    aux.put("id_animal", String.valueOf(gadoValue));
                    aux.put("fecha_venta", et_form_venta_gado_fecha.getText().toString());
                    aux.put("id_venta_gado", "");
                    aux.put("nome_gado", sp_form_venta_gado_animal.getSelectedItem().toString());
                    aux.put("id_usuario", String.valueOf(id_user));

                    localdb.ManutencaoVentaGado(aux);
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

    public void editHistorialConsumo() {

        JSONObject aux2 = new JSONObject();

        try {

            int id_animal = bundle.getInt("id_animal");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("precio", et_form_venta_gado_precio.getText().toString().replace(" ","%20"));
                    aux2.put("id_animal", id_animal);
                    aux2.put("fecha_venta", et_form_venta_gado_fecha.getText().toString().replace(" ","%20"));
                    aux2.put("id_venta_gado", "");
                    aux2.put("nome_gado", sp_form_venta_gado_animal.getSelectedItem().toString().replace(" ","%20"));
                    aux2.put("id_usuario", String.valueOf(id_user));


                    remotedb.ManutencaoVentaGado(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("precio", et_form_venta_gado_precio.getText().toString());
                    aux2.put("id_animal", id_animal);
                    aux2.put("fecha_venta", et_form_venta_gado_fecha.getText().toString());
                    aux2.put("id_venta_gado", "");
                    aux2.put("nome_gado", sp_form_venta_gado_animal.getSelectedItem().toString());
                    aux2.put("id_usuario", String.valueOf(id_user));

                    localdb.ManutencaoVentaGado(aux2);
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

    public void getDatosInicial(){


        //Remoto
        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("id_lote_gado", "");

            ArrayRequest ar = new ArrayRequest(this, getWsConsultaGado(), aux, "Gado");
            ar.makeRequest();

            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getDatosInicialLocal(){
        //Local
        ArrayList<Gado> aux = localdb.ConsultaGado(String.valueOf(id_user),"","Cargado");
        updateAnimalArray(aux);
    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Gado") {

            Gado p;
            ArrayList<Gado> aux = new ArrayList<Gado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Gado(
                                jo.getInt("id_animal"),
                                jo.getInt("id_lote_gado"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getInt("peso_inicial"),
                                jo.getInt("cod_adquisicao"),
                                jo.getString("tipo_adquisicao"),
                                jo.getInt("precio"),
                                jo.getString("fecha"),
                                jo.getString("id_animal_parto")

                        );

                        aux.add(p);
                        Log.d("dato", aux.toString());
                    }

                    updateAnimalArray(aux);

                } else {
                    ToastMsg("Nao tem gados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        if (option == "InsertVentaGado") {

            Log.d("holi", "venta gado enviada");
            finish();

        }

    }



    public void updateAnimalArray(List<Gado> Lp) {

        String[] optionsProducto  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_animal()));
            optionsProducto[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsProducto);
        dataAdapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_venta_gado_animal.setAdapter(dataAdapterProducto);

        if (bundle != null){

            int spinnerPosition = dataAdapterProducto.getPosition(bundle.getString("N_Animal"));
            sp_form_venta_gado_animal.setSelection(spinnerPosition);

        }


    }

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_venta_gado_fecha.setText(sdf.format(myCalendar.getTime()));
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

        et_form_venta_gado_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VentaGadoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}